package com.szino.leavecalendar.controller;

import com.szino.leavecalendar.dto.OnCallWeekDto;
import com.szino.leavecalendar.service.OnCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/oncall")
@RequiredArgsConstructor
public class OnCallController {

    private final OnCallService onCallService;

    @GetMapping
    public List<OnCallWeekDto> getSchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return onCallService.getOnCallSchedule(from, to);
    }
}