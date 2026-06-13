package com.szino.leavecalendar.controller;

import com.szino.leavecalendar.model.TeamMember;
import com.szino.leavecalendar.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class TeamMemberController {

    private final TeamMemberRepository teamMemberRepository;

    @GetMapping
    public List<TeamMember> getAll() {
        return teamMemberRepository.findAll();
    }
}