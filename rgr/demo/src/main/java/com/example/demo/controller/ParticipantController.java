package com.example.demo.controller;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService service;

    @GetMapping
    public List<ParticipantDto> all() { return service.getAll(); }

    @GetMapping("/{id}")
    public ParticipantDto one(@PathVariable int id) { return service.getOne(id); }

    @PostMapping
    public ParticipantDto create(@RequestBody ParticipantDto dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public ParticipantDto update(@PathVariable int id, @RequestBody ParticipantDto dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) { service.delete(id); }
}
