package com.example.demo.controller;

import com.example.demo.dto.ChampionshipDto;
import com.example.demo.service.ChampionshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/championships")
@RequiredArgsConstructor
public class ChampionshipController {

    private final ChampionshipService service;

    @GetMapping
    public List<ChampionshipDto> all() { return service.getAll(); }

    @GetMapping("/{id}")
    public ChampionshipDto one(@PathVariable int id) { return service.getOne(id); }

    @PostMapping
    public ChampionshipDto create(@RequestBody ChampionshipDto dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public ChampionshipDto update(@PathVariable int id, @RequestBody ChampionshipDto dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) { service.delete(id); }

    @PostMapping("/{id}/teams/{teamId}")
    public ChampionshipDto addTeam(@PathVariable int id, @PathVariable int teamId) {
        return service.addTeam(id, teamId);
    }

    @DeleteMapping("/{id}/teams/{teamId}")
    public ChampionshipDto removeTeam(@PathVariable int id, @PathVariable int teamId) {
        return service.removeTeam(id, teamId);
    }
}
