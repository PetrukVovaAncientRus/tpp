package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participant")
@Getter @Setter @NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="first_name", nullable=false, length=60)
    private String firstName;

    @Column(name="last_name", nullable=false, length=60)
    private String lastName;

    @Column(length=60)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id") // має існувати колонка participant.team_id в БД
    private Team team;
}
