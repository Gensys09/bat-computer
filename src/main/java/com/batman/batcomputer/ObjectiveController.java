package com.batman.batcomputer;

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
public class ObjectiveController {
    private final ObjectiveRepository repository;
    private final ObjectiveModelAssembler assembler;

    ObjectiveController(ObjectiveRepository repository, ObjectiveModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/objectives")
    CollectionModel<EntityModel<Objective>> all() {
        List<EntityModel<Objective>> objectives = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(objectives,
                linkTo(methodOn(ObjectiveController.class).all()).withSelfRel());
    }

    @GetMapping("/objectives/{id}")
    EntityModel<Objective> one(@PathVariable Long id) {
        Objective objective = repository.findById(id)
                .orElseThrow(() -> new ObjectiveNotFoundException(id));

        return assembler.toModel(objective);
    }

    @PostMapping("/objectives")
    ResponseEntity<EntityModel<Objective>> newObjective(@RequestBody Objective objective) {
        //converting enum to string by valueOf() method
        objective.setStatus(String.valueOf(Status.IN_PROGRESS));

        Objective newObjective = repository.save(objective);
        return ResponseEntity
                .created(linkTo(methodOn(ObjectiveController.class).one(newObjective.getId())).toUri())
                .body(assembler.toModel(newObjective));
    }

    @DeleteMapping("/objectives/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        Objective objective = repository.findById(id)
                .orElseThrow(() -> new ObjectiveNotFoundException(id));

        if (objective.getStatus().equals(Status.IN_PROGRESS.name())) {
            objective.setStatus(Status.CANCELLED.name());
            return ResponseEntity.ok(assembler.toModel(repository.save(objective)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an objective that is in the " + objective.getStatus() + " status"));
    }

    @PutMapping("/objectives/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        Objective objective = repository.findById(id)
                .orElseThrow( () -> new ObjectiveNotFoundException(id));
        if (objective.getStatus().equals(Status.IN_PROGRESS.name())) {
            objective.setStatus(Status.COMPLETED.name());
            return ResponseEntity.ok(assembler.toModel(repository.save(objective)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE,
                        MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an objective that is in the "
                                + objective.getStatus() + " status"));
    }
}
