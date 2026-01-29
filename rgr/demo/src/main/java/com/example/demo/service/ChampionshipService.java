package com.example.demo.service;

import com.example.demo.dto.ChampionshipDto;
import com.example.demo.entity.Championship;
import com.example.demo.entity.Team;
import com.example.demo.repository.ChampionshipRepository;
import com.example.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChampionshipService {

    private final ChampionshipRepository repo;
    private final TeamRepository teamRepo;

    public List<ChampionshipDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public ChampionshipDto getOne(int id) {
        Championship ch = repo.findById(id).orElseThrow(() -> new RuntimeException("Championship not found"));
        return toDto(ch);
    }

    public ChampionshipDto create(ChampionshipDto dto) {
        Championship ch = new Championship();
        ch.setTitle(dto.title());
        ch.setGame(dto.game());
        ch.setStartDate(dto.startDate());
        ch.setEndDate(dto.endDate());

        if (dto.teamIds() != null && !dto.teamIds().isEmpty()) {
            Set<Team> teams = new HashSet<>(teamRepo.findAllById(dto.teamIds()));
            ch.setTeams(teams);
        }

        Championship saved = repo.save(ch);
        log.info("Created championship id={}", saved.getId());
        return toDto(saved);
    }

    public ChampionshipDto update(int id, ChampionshipDto dto) {
        Championship ch = repo.findById(id).orElseThrow(() -> new RuntimeException("Championship not found"));
        ch.setTitle(dto.title());
        ch.setGame(dto.game());
        ch.setStartDate(dto.startDate());
        ch.setEndDate(dto.endDate());

        if (dto.teamIds() != null) {
            Set<Team> teams = new HashSet<>(teamRepo.findAllById(dto.teamIds()));
            ch.setTeams(teams);
        }

        Championship saved = repo.save(ch);
        log.info("Updated championship id={}", saved.getId());
        return toDto(saved);
    }

    public void delete(int id) {
        repo.deleteById(id);
        log.info("Deleted championship id={}", id);
    }

    // зв’язки: додати/прибрати команду в чемпіонат
    public ChampionshipDto addTeam(int championshipId, int teamId) {
        Championship ch = repo.findById(championshipId).orElseThrow(() -> new RuntimeException("Championship not found"));
        Team t = teamRepo.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        ch.getTeams().add(t);
        Championship saved = repo.save(ch);
        log.info("Added team {} to championship {}", teamId, championshipId);
        return toDto(saved);
    }

    public ChampionshipDto removeTeam(int championshipId, int teamId) {
        Championship ch = repo.findById(championshipId).orElseThrow(() -> new RuntimeException("Championship not found"));
        ch.getTeams().removeIf(t -> t.getId().equals(teamId));
        Championship saved = repo.save(ch);
        log.info("Removed team {} from championship {}", teamId, championshipId);
        return toDto(saved);
    }

    private ChampionshipDto toDto(Championship ch) {
        Set<Integer> teamIds = ch.getTeams() == null ? Set.of()
                : ch.getTeams().stream().map(Team::getId).collect(java.util.stream.Collectors.toSet());

        return new ChampionshipDto(
                ch.getId(),
                ch.getTitle(),
                ch.getGame(),
                ch.getStartDate(),
                ch.getEndDate(),
                teamIds
        );
    }
}
