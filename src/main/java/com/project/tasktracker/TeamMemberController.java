package com.project.tasktracker;

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
class TeamMemberController {
    private final TeamMemberRepository repository;

    private final TeamMemberModelAssembler assembler;

    TeamMemberController(TeamMemberRepository repository, TeamMemberModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root   
    // tag::get-aggregate-root[]
    @GetMapping("/team-members")
    CollectionModel<EntityModel<TeamMember>> all() {
        List<EntityModel<TeamMember>> batMembers = repository.findAll().stream()
                .map(assembler::toModel)
                        .collect(Collectors.toList());
        //collect all TeamMembers and add a link to the method all() for team-members endpoint

        //methodOn is used to create a link to the method all() for team-members endpoint
        return CollectionModel.of(batMembers, linkTo(methodOn(TeamMemberController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/team-members")
    //ResponseEntity is a generic container part of Spring HATEOAS
    ResponseEntity<?> newBatMember(@RequestBody TeamMember newTeamMember) {
        EntityModel<TeamMember> entityModel = assembler.toModel(repository.save(newTeamMember));

        return ResponseEntity
                //necessary to provide a URI representing the location of the resource
                //when returning a 201 Created status
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()
                ).body(entityModel);

    }

    // Single item
    @GetMapping("/team-members/{id}")
    //EntityModel is a generic container part of Spring HATEOAS
    //used to wrap a single TeamMember and add links to it
    EntityModel<TeamMember> one(@PathVariable Long id) {
        TeamMember teamMember = repository.findById(id)
                .orElseThrow(() -> new TeamMemberNotFoundException(id));

        return assembler.toModel(teamMember);
    }

    @PutMapping("/team-members/{id}")
    ResponseEntity<?> replaceTeamMember(@RequestBody TeamMember newTeamMember, @PathVariable Long id) {
                TeamMember updatedTeamMember = repository.findById(id)
                        .map(batMember -> {
                    batMember.setName(newTeamMember.getName());
                    batMember.setRole(newTeamMember.getRole());
                    return repository.save(batMember);
                        })
                        .orElseGet(() -> {
                            newTeamMember.setId(id);
                            return repository.save(newTeamMember);
                        });

        EntityModel <TeamMember> entityModel = assembler.toModel(updatedTeamMember);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @DeleteMapping("/team-members/{id}")
    ResponseEntity<?> deleteTeamMember(@PathVariable Long id) {
        repository.deleteById(id);

        //return a 204 No Content response
        return ResponseEntity.noContent().build();
    }
}