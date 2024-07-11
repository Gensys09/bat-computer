package com.project.tasktracker;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class TeamMemberModelAssembler implements RepresentationModelAssembler<TeamMember, EntityModel<TeamMember>> {
    //converts a TeamMember into an EntityModel
    //entity model wraps a TeamMember and adds links to it
    @Override
    @NonNull
    public EntityModel<TeamMember> toModel(@NonNull TeamMember teamMember) {
        return EntityModel.of(teamMember,
                linkTo(methodOn(TeamMemberController.class).one(teamMember.getId())).withSelfRel(),
                linkTo(methodOn(TeamMemberController.class).all()).withRel("bat-members"));

    }


}
