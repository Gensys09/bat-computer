package com.project.tasktracker;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {
    @Override
    @NonNull
    public EntityModel<Task> toModel(@NonNull Task task) {

        //unconditional links to single-item resource and aggregate root

        EntityModel<Task> objectiveModel = EntityModel.of(task,
                linkTo(methodOn(TaskController.class).one(task.getId())).withSelfRel(),
                linkTo(methodOn(TaskController.class).all()).withRel("objectives"));

        //conditional links based on state of the task, (helps decouple the controller from the state of the objective)
        //name() method returns the name of the enum constant
        if (task.getStatus().equals(Status.IN_PROGRESS.name())) {
            objectiveModel.add(linkTo(methodOn(TaskController.class).cancel(task.getId())).withRel("cancel"));
            objectiveModel.add(linkTo(methodOn(TaskController.class).complete(task.getId())).withRel("complete"));


        }

        return objectiveModel;
    }
}