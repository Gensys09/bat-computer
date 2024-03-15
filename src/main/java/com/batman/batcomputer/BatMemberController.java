package com.batman.batcomputer;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class BatMemberController {
    private final BatMemberRepository repository;

    private final BatMemberModelAssembler assembler;

    BatMemberController(BatMemberRepository repository, BatMemberModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root   
    // tag::get-aggregate-root[]
    @GetMapping("/bat-members")
    CollectionModel<EntityModel<BatMember>> all() {
        List<EntityModel<BatMember>> batMembers = repository.findAll().stream()
                .map(assembler::toModel)
                        .collect(Collectors.toList());

        //methodOn is used to create a link to the method all() for bat-members endpoint
        return CollectionModel.of(batMembers, linkTo(methodOn(BatMemberController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/bat-members")
    BatMember newBatMember(@RequestBody BatMember newBatMember) {
        return repository.save(newBatMember);
    }

    // Single item
    @GetMapping("/bat-members/{id}")
    //EntityModel is a generic container part of Spring HATEOAS
    //used to wrap a single BatMember and add links to it
    EntityModel<BatMember> one(@PathVariable Long id) {
        BatMember batMember = repository.findById(id)
                .orElseThrow(() -> new BatMemberNotFoundException(id));

        return assembler.toModel(batMember);
    }

    @PutMapping("/bat-members/{id}")
    BatMember replaceBatMember(@RequestBody BatMember newBatMember, @PathVariable Long id) {
        return  repository.findById(id)
                //if the BatMember is found, update the name and role
                .map(batMember -> {
                    batMember.setName(newBatMember.getName());
                    batMember.setRole(newBatMember.getRole());
                    return repository.save(batMember);
                })
                //if the BatMember is not found, create a new BatMember
                .orElseGet(() -> {
                    newBatMember.setId(id);
                    return repository.save(newBatMember);
                });
    }

    @DeleteMapping("/bat-members/{id}")
    void deleteBatMember(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
