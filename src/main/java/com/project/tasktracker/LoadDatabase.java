package com.project.tasktracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class LoadDatabase {

    private static final Logger log =
            LoggerFactory.getLogger(LoadDatabase.class.getName());
            //using getName() explicitly to avoid any confusion

    //command line runner to save the TeamMembers to the database
    @Bean
    CommandLineRunner initDatabase(TeamMemberRepository batRepository, TaskRepository taskRepository) {
        //lambda expression to save the TeamMembers to the database
        return args -> {
            batRepository.save(new TeamMember("Bruce", "Wayne", "Batman"));
            batRepository.save(new TeamMember("Alfred", "Pennyworth", "Butler"));


            batRepository.findAll().forEach(batMember ->
                    log.info("Preloaded member: " + batMember));

            taskRepository.save(new Task("Survey the city", Status.COMPLETED.name()));
            taskRepository.save(new Task("Investigate recent reports", Status.IN_PROGRESS.name()));
            taskRepository.findAll().forEach(objective -> {
                log.info("Preloaded objective: " + objective);
            });
        };
    }
}
