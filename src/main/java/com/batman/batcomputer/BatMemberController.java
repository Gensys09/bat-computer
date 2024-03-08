package com.batman.batcomputer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BatMemberController {
    private final BatMemberRepository repository;

    BatMemberController(BatMemberRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/bat-members")
    List<BatMember> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/bat-members")
    BatMember newBatMember(@RequestBody BatMember newBatMember) {
        return repository.save(newBatMember);
    }

    // Single item
    @GetMapping("/bat-members/{id}")
    BatMember one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BatMemberNotFoundException(id));
    }

    @PutMapping("/bat-members/{}")
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
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
