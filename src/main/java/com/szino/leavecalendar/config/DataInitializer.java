package com.szino.leavecalendar.config;

import com.szino.leavecalendar.model.TeamMember;
import com.szino.leavecalendar.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public void run(String... args) {
        if (teamMemberRepository.count() == 0) {
            teamMemberRepository.saveAll(List.of(
                    new TeamMember("Alice"),
                    new TeamMember("Bob"),
                    new TeamMember("Charlie"),
                    new TeamMember("Diana")
            ));
        }
    }
}