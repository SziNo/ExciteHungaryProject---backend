package com.szino.leavecalendar.dto;

import com.szino.leavecalendar.model.LeaveRequest;
import com.szino.leavecalendar.model.LeaveStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LeaveResponseDto {

    private final Long id;
    private final Long teamMemberId;
    private final String teamMemberName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String reason;
    private final LeaveStatus status;

    public LeaveResponseDto(LeaveRequest leave) {
        this.id = leave.getId();
        this.teamMemberId = leave.getTeamMember().getId();
        this.teamMemberName = leave.getTeamMember().getName();
        this.startDate = leave.getStartDate();
        this.endDate = leave.getEndDate();
        this.reason = leave.getReason();
        this.status = leave.getStatus();
    }
}