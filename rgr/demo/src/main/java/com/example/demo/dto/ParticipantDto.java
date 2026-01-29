package com.example.demo.dto;

public record ParticipantDto(
        Integer id,
        String firstName,
        String lastName,
        String nickname,
        Integer teamId
) {}
