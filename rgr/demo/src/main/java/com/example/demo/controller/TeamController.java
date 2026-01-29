package com.example.demo.controller;

import com.example.demo.dto.TeamDto;
import com.example.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService service;

    @GetMapping
    public List<TeamDto> all() { return service.getAll(); }

    @GetMapping("/{id}")
    public TeamDto one(@PathVariable int id) { return service.getOne(id); }

    @PostMapping
    public TeamDto create(@RequestBody TeamDto dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public TeamDto update(@PathVariable int id, @RequestBody TeamDto dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) { service.delete(id); }
}
