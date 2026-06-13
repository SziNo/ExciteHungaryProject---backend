package com.szino.leavecalendar.service;

import com.szino.leavecalendar.dto.LeaveRequestDto;
import com.szino.leavecalendar.dto.LeaveResponseDto;
import com.szino.leavecalendar.model.LeaveRequest;
import com.szino.leavecalendar.model.LeaveStatus;
import com.szino.leavecalendar.model.TeamMember;
import com.szino.leavecalendar.repository.LeaveRequestRepository;
import com.szino.leavecalendar.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final TeamMemberRepository teamMemberRepository;

    public List<LeaveResponseDto> getAll() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(LeaveResponseDto::new)
                .toList();
    }

    public List<LeaveResponseDto> getByMember(Long memberId) {
        return leaveRequestRepository.findByTeamMemberId(memberId)
                .stream()
                .map(LeaveResponseDto::new)
                .toList();
    }

    public List<LeaveResponseDto> getByStatus(LeaveStatus status) {
        return leaveRequestRepository.findByStatus(status)
                .stream()
                .map(LeaveResponseDto::new)
                .toList();
    }

    public LeaveResponseDto create(LeaveRequestDto dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date cannot be before start date");
        }

        TeamMember member = teamMemberRepository.findById(dto.getTeamMemberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team member not found"));

        List<LeaveRequest> overlapping = leaveRequestRepository.findOverlapping(
                dto.getTeamMemberId(),
                dto.getStartDate(),
                dto.getEndDate(),
                LeaveStatus.REJECTED
        );

        if (!overlapping.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Leave request overlaps with an existing one");
        }

        LeaveRequest leave = new LeaveRequest();
        leave.setTeamMember(member);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setReason(dto.getReason());

        return new LeaveResponseDto(leaveRequestRepository.save(leave));
    }

    public LeaveResponseDto updateStatus(Long id, LeaveStatus newStatus) {
        LeaveRequest leave = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave request not found"));

        leave.setStatus(newStatus);
        return new LeaveResponseDto(leaveRequestRepository.save(leave));
    }
}