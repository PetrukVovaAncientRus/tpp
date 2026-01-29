package com.example.demo.repository;

import com.example.demo.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionshipRepository extends JpaRepository<Championship, Integer> {}
