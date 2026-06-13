package com.szino.leavecalendar.repository;

import com.szino.leavecalendar.model.LeaveRequest;
import com.szino.leavecalendar.model.LeaveStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("""
        SELECT l FROM LeaveRequest l
        WHERE l.teamMember.id = :memberId
        AND l.status != :excludedStatus
        AND l.startDate <= :endDate
        AND l.endDate >= :startDate
    """)
    List<LeaveRequest> findOverlapping(
        @Param("memberId") Long memberId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("excludedStatus") LeaveStatus excludedStatus
    );

    List<LeaveRequest> findByTeamMemberId(Long teamMemberId);

    List<LeaveRequest> findByStatus(LeaveStatus status);

    @Query("""
        SELECT l FROM LeaveRequest l
        WHERE l.teamMember.id = :memberId
        AND l.status = 'APPROVED'
        AND l.startDate <= :weekEnd
        AND l.endDate >= :weekStart
    """)
    List<LeaveRequest> findApprovedLeavesInWeek(
        @Param("memberId") Long memberId,
        @Param("weekStart") LocalDate weekStart,
        @Param("weekEnd") LocalDate weekEnd
    );
}