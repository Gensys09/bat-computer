package com.batman.batcomputer;

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

    //command line runner to save the BatMembers to the database
    @Bean
    CommandLineRunner initDatabase(BatMemberRepository batRepository, ObjectiveRepository objectiveRepository) {
        //lambda expression to save the BatMembers to the database
        return args -> {
            batRepository.save(new BatMember("Bruce", "Wayne", "Batman"));
            batRepository.save(new BatMember("Alfred", "Pennyworth", "Butler"));


            batRepository.findAll().forEach(batMember ->
                    log.info("Preloaded member: " + batMember));

            objectiveRepository.save(new Objective("Survey the city", Status.COMPLETED.name()));
            objectiveRepository.save(new Objective("Investigate recent reports", Status.IN_PROGRESS.name()));
            objectiveRepository.findAll().forEach(objective -> {
                log.info("Preloaded objective: " + objective);
            });
        };
    }
}
