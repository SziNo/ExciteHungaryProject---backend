package com.szino.leavecalendar.service;

import com.szino.leavecalendar.dto.OnCallWeekDto;
import com.szino.leavecalendar.model.TeamMember;
import com.szino.leavecalendar.repository.LeaveRequestRepository;
import com.szino.leavecalendar.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OnCallService { // ← EZ HIÁNYZOTT!

    private final TeamMemberRepository teamMemberRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public List<OnCallWeekDto> getOnCallSchedule(LocalDate from, LocalDate to) {
        List<TeamMember> members = teamMemberRepository.findAll();
        List<OnCallWeekDto> schedule = new ArrayList<>();

        // Rögzített epoch: 2026-01-05 (az év első hétfője)
        LocalDate epoch = LocalDate.of(2026, 1, 5);

        LocalDate weekStart = from.with(DayOfWeek.MONDAY);

        while (!weekStart.isAfter(to)) {
            LocalDate weekEnd = weekStart.plusDays(6);

            long weeksSinceEpoch = ChronoUnit.WEEKS.between(epoch, weekStart);
            int weekIndex = (int) Math.floorMod(weeksSinceEpoch, members.size());

            TeamMember onCallMember = members.get(weekIndex);

            boolean hasConflict = !leaveRequestRepository.findApprovedLeavesInWeek(
                    onCallMember.getId(),
                    weekStart,
                    weekEnd).isEmpty();

            int weekNumber = weekStart.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

            schedule.add(new OnCallWeekDto(
                    weekNumber,
                    weekStart,
                    weekEnd,
                    onCallMember.getId(),
                    onCallMember.getName(),
                    hasConflict));

            weekStart = weekStart.plusWeeks(1);
        }

        return schedule;
    }
}