package com.batman.batcomputer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
class LoadDatabase {

    private static final Logger log =
            Logger.getLogger(LoadDatabase.class.getName());
            //using getName() explicitly to avoid any confusion

    //command line runner to save the BatMembers to the database
    @Bean
    CommandLineRunner initDatabase(BatMemberRepository repository) {
        //lambda expression to save the BatMembers to the database
        return args -> {
            log.info("Preloading " + repository.save(new BatMember("Bruce Wayne", "Batman")));
            log.info("Preloading " + repository.save(new BatMember("Alfred Pennyworth", "Butler")));
        };
    }
}
