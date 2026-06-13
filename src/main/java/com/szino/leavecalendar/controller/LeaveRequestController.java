package com.szino.leavecalendar.controller;

import com.szino.leavecalendar.dto.LeaveRequestDto;
import com.szino.leavecalendar.dto.LeaveResponseDto;
import com.szino.leavecalendar.model.LeaveStatus;
import com.szino.leavecalendar.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @GetMapping
    public List<LeaveResponseDto> getAll(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) LeaveStatus status) {
        if (memberId != null)
            return leaveRequestService.getByMember(memberId);
        if (status != null)
            return leaveRequestService.getByStatus(status);
        return leaveRequestService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveResponseDto create(@Valid @RequestBody LeaveRequestDto dto) {
        return leaveRequestService.create(dto);
    }

    @PatchMapping("/{id}/status")
    public LeaveResponseDto updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return leaveRequestService.updateStatus(id, LeaveStatus.valueOf(status.toUpperCase()));
    }
}