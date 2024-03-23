package com.batman.batcomputer;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
        //collect all BatMembers and add a link to the method all() for bat-members endpoint

        //methodOn is used to create a link to the method all() for bat-members endpoint
        return CollectionModel.of(batMembers, linkTo(methodOn(BatMemberController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/bat-members")
    //ResponseEntity is a generic container part of Spring HATEOAS
    ResponseEntity<?> newBatMember(@RequestBody BatMember newBatMember) {
        EntityModel<BatMember> entityModel = assembler.toModel(repository.save(newBatMember));

        return ResponseEntity
                //necessary to provide a URI representing the location of the resource
                //when returning a 201 Created status
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()
                ).body(entityModel);

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
    ResponseEntity<?> replaceBatMember(@RequestBody BatMember newBatMember, @PathVariable Long id) {
                BatMember updatedBatMember = repository.findById(id)
                        .map(batMember -> {
                    batMember.setName(newBatMember.getName());
                    batMember.setRole(newBatMember.getRole());
                    return repository.save(batMember);
                        })
                        .orElseGet(() -> {
                            newBatMember.setId(id);
                            return repository.save(newBatMember);
                        });

        EntityModel <BatMember> entityModel = assembler.toModel(updatedBatMember);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);


//        return  repository.findById(id)
//                //if the BatMember is found, update the name and role
//                .map(batMember -> {
//                    batMember.setName(newBatMember.getName());
//                    batMember.setRole(newBatMember.getRole());
//                    return repository.save(batMember);
//                })
//                //if the BatMember is not found, create a new BatMember
//                .orElseGet(() -> {
//                    newBatMember.setId(id);
//                    return repository.save(newBatMember);
//                });
    }

    @DeleteMapping("/bat-members/{id}")
    ResponseEntity<?> deleteBatMember(@PathVariable Long id) {
        repository.deleteById(id);

        //return a 204 No Content response
        return ResponseEntity.noContent().build();
    }
}