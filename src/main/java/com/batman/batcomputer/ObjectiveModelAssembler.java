package com.batman.batcomputer;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class ObjectiveModelAssembler implements RepresentationModelAssembler<Objective, EntityModel<Objective>> {
    @Override
    @NonNull
    public EntityModel<Objective> toModel(@NonNull Objective objective) {

        //unconditional links to single-item resource and aggregate root

        EntityModel<Objective> objectiveModel = EntityModel.of(objective,
                linkTo(methodOn(ObjectiveController.class).one(objective.getId())).withSelfRel(),
                linkTo(methodOn(ObjectiveController.class).all()).withRel("objectives"));

        //conditional links based on state of the objective, (helps decouple the controller from the state of the objective)
        //name() method returns the name of the enum constant
        if (objective.getStatus().equals(Status.IN_PROGRESS.name())) {
            objectiveModel.add(linkTo(methodOn(ObjectiveController.class).cancel(objective.getId())).withRel("cancel"));
            objectiveModel.add(linkTo(methodOn(ObjectiveController.class).complete(objective.getId())).withRel("complete"));


        }

        return objectiveModel;
    }
}