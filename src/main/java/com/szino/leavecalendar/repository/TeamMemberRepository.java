package com.szino.leavecalendar.repository;

import com.szino.leavecalendar.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}