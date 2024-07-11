package com.project.tasktracker;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TaskController {
    private final TaskRepository repository;
    private final TaskModelAssembler assembler;

    TaskController(TaskRepository repository, TaskModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/objectives")
    CollectionModel<EntityModel<Task>> all() {
        List<EntityModel<Task>> objectives = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(objectives,
                linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }

    @GetMapping("/objectives/{id}")
    EntityModel<Task> one(@PathVariable Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ObjectiveNotFoundException(id));

        return assembler.toModel(task);
    }

    @PostMapping("/objectives")
    ResponseEntity<EntityModel<Task>> newObjective(@RequestBody Task task) {
        //converting enum to string by valueOf() method
        task.setStatus(String.valueOf(Status.IN_PROGRESS));

        Task newTask = repository.save(task);
        return ResponseEntity
                .created(linkTo(methodOn(TaskController.class).one(newTask.getId())).toUri())
                .body(assembler.toModel(newTask));
    }

    @DeleteMapping("/objectives/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ObjectiveNotFoundException(id));

        if (task.getStatus().equals(Status.IN_PROGRESS.name())) {
            task.setStatus(Status.CANCELLED.name());
            return ResponseEntity.ok(assembler.toModel(repository.save(task)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel a task that is in the " + task.getStatus() + " status"));
    }

    @PutMapping("/objectives/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        Task task = repository.findById(id)
                .orElseThrow( () -> new ObjectiveNotFoundException(id));
        if (task.getStatus().equals(Status.IN_PROGRESS.name())) {
            task.setStatus(Status.COMPLETED.name());
            return ResponseEntity.ok(assembler.toModel(repository.save(task)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete a task that is in the "
                                + task.getStatus() + " status"));
    }
}
