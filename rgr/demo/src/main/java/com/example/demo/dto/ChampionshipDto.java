package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Set;

public record ChampionshipDto(
        Integer id,
        String title,
        String game,
        LocalDate startDate,
        LocalDate endDate,
        Set<Integer> teamIds
) {}
