package com.example.demo.service;

import com.example.demo.dto.TeamDto;
import com.example.demo.entity.Team;
import com.example.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository repo;

    public List<TeamDto> getAll() {
        return repo.findAll().stream()
                .map(t -> new TeamDto(t.getId(), t.getName(), t.getCity()))
                .toList();
    }

    public TeamDto getOne(int id) {
        Team t = repo.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        return new TeamDto(t.getId(), t.getName(), t.getCity());
    }

    public TeamDto create(TeamDto dto) {
        Team t = new Team();
        t.setName(dto.name());
        t.setCity(dto.city());
        Team saved = repo.save(t);
        log.info("Created team id={}", saved.getId());
        return new TeamDto(saved.getId(), saved.getName(), saved.getCity());
    }

    public TeamDto update(int id, TeamDto dto) {
        Team t = repo.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
        t.setName(dto.name());
        t.setCity(dto.city());
        Team saved = repo.save(t);
        log.info("Updated team id={}", saved.getId());
        return new TeamDto(saved.getId(), saved.getName(), saved.getCity());
    }

    public void delete(int id) {
        repo.deleteById(id);
        log.info("Deleted team id={}", id);
    }
}
