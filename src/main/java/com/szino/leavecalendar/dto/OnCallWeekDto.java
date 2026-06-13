package com.szino.leavecalendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class OnCallWeekDto {

    private final int weekNumber;
    private final LocalDate weekStart;
    private final LocalDate weekEnd;
    private final Long teamMemberId;
    private final String teamMemberName;
    private final boolean hasConflict;
}