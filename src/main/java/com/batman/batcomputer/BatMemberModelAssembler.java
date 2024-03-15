package com.batman.batcomputer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class BatMemberModelAssembler implements RepresentationModelAssembler<BatMember, EntityModel<BatMember>> {
    //converts a BatMember into an EntityModel
    //entity model wraps a BatMember and adds links to it
    @Override
    @NonNull
    public EntityModel<BatMember> toModel(@NonNull BatMember batMember) {
        return EntityModel.of(batMember,
                linkTo(methodOn(BatMemberController.class).one(batMember.getId())).withSelfRel(),
                linkTo(methodOn(BatMemberController.class).all()).withRel("bat-members"));

    }


}
