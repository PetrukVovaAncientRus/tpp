package com.example.demo.service;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.entity.Participant;
import com.example.demo.entity.Team;
import com.example.demo.repository.ParticipantRepository;
import com.example.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {

    private final ParticipantRepository repo;
    private final TeamRepository teamRepo;

    public List<ParticipantDto> getAll() {
        return repo.findAll().stream()
                .map(p -> new ParticipantDto(
                        p.getId(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getNickname(),
                        p.getTeam() != null ? p.getTeam().getId() : null
                ))
                .toList();
    }

    public ParticipantDto getOne(int id) {
        Participant p = repo.findById(id).orElseThrow(() -> new RuntimeException("Participant not found"));
        Integer teamId = p.getTeam() != null ? p.getTeam().getId() : null;
        return new ParticipantDto(p.getId(), p.getFirstName(), p.getLastName(), p.getNickname(), teamId);
    }

    public ParticipantDto create(ParticipantDto dto) {
        Participant p = new Participant();
        p.setFirstName(dto.firstName());
        p.setLastName(dto.lastName());
        p.setNickname(dto.nickname());

        if (dto.teamId() != null) {
            Team t = teamRepo.findById(dto.teamId()).orElseThrow(() -> new RuntimeException("Team not found"));
            p.setTeam(t);
        }

        Participant saved = repo.save(p);
        log.info("Created participant id={}", saved.getId());
        Integer teamId = saved.getTeam() != null ? saved.getTeam().getId() : null;
        return new ParticipantDto(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getNickname(), teamId);
    }

    public ParticipantDto update(int id, ParticipantDto dto) {
        Participant p = repo.findById(id).orElseThrow(() -> new RuntimeException("Participant not found"));

        p.setFirstName(dto.firstName());
        p.setLastName(dto.lastName());
        p.setNickname(dto.nickname());

        if (dto.teamId() == null) {
            p.setTeam(null);
        } else {
            Team t = teamRepo.findById(dto.teamId()).orElseThrow(() -> new RuntimeException("Team not found"));
            p.setTeam(t);
        }

        Participant saved = repo.save(p);
        log.info("Updated participant id={}", saved.getId());
        Integer teamId = saved.getTeam() != null ? saved.getTeam().getId() : null;
        return new ParticipantDto(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getNickname(), teamId);
    }

    public void delete(int id) {
        repo.deleteById(id);
        log.info("Deleted participant id={}", id);
    }
}
