package com.example.demo.controller;

import com.example.demo.dto.ChampionshipDto;
import com.example.demo.dto.ParticipantDto;
import com.example.demo.dto.TeamDto;
import com.example.demo.service.ChampionshipService;
import com.example.demo.service.ParticipantService;
import com.example.demo.service.TeamService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final TeamService teamService;
    private final ParticipantService participantService;
    private final ChampionshipService championshipService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("teams", teamService.getAll());
        model.addAttribute("participants", participantService.getAll());
        model.addAttribute("championships", championshipService.getAll());

        // форми для створення
        model.addAttribute("teamForm", new TeamForm());
        model.addAttribute("participantForm", new ParticipantForm());
        model.addAttribute("championshipForm", new ChampionshipForm());

        return "index";
    }

    // ---------- Teams (ADMIN) ----------
    @PostMapping("/ui/teams/create")
    public String createTeam(@ModelAttribute TeamForm f) {
        teamService.create(new TeamDto(null, f.getName(), f.getCity()));
        return "redirect:/";
    }

    @PostMapping("/ui/teams/update")
    public String updateTeam(@ModelAttribute TeamForm f) {
        teamService.update(f.getId(), new TeamDto(f.getId(), f.getName(), f.getCity()));
        return "redirect:/";
    }

    @PostMapping("/ui/teams/delete")
    public String deleteTeam(@RequestParam int id) {
        teamService.delete(id);
        return "redirect:/";
    }

    // ---------- Participants (ADMIN) ----------
    @PostMapping("/ui/participants/create")
    public String createParticipant(@ModelAttribute ParticipantForm f) {
        participantService.create(new ParticipantDto(
                null, f.getFirstName(), f.getLastName(), f.getNickname(), f.getTeamId()
        ));
        return "redirect:/";
    }

    @PostMapping("/ui/participants/update")
    public String updateParticipant(@ModelAttribute ParticipantForm f) {
        participantService.update(f.getId(), new ParticipantDto(
                f.getId(), f.getFirstName(), f.getLastName(), f.getNickname(), f.getTeamId()
        ));
        return "redirect:/";
    }

    @PostMapping("/ui/participants/delete")
    public String deleteParticipant(@RequestParam int id) {
        participantService.delete(id);
        return "redirect:/";
    }

    // ---------- Championships (ADMIN) ----------
    @PostMapping("/ui/championships/create")
    public String createChampionship(@ModelAttribute ChampionshipForm f) {
        ChampionshipDto dto = new ChampionshipDto(
                null,
                f.getTitle(),
                f.getGame(),
                f.getStartDate(),
                f.getEndDate(),
                Set.of() // команди додамо окремо
        );
        championshipService.create(dto);
        return "redirect:/";
    }

    @PostMapping("/ui/championships/update")
    public String updateChampionship(@ModelAttribute ChampionshipForm f) {
        ChampionshipDto dto = new ChampionshipDto(
                f.getId(),
                f.getTitle(),
                f.getGame(),
                f.getStartDate(),
                f.getEndDate(),
                null // не чіпати список teams
        );
        championshipService.update(f.getId(), dto);
        return "redirect:/";
    }

    @PostMapping("/ui/championships/delete")
    public String deleteChampionship(@RequestParam int id) {
        championshipService.delete(id);
        return "redirect:/";
    }

    // додати/прибрати команду в чемпіонат (ADMIN)
    @PostMapping("/ui/championships/addTeam")
    public String addTeamToChampionship(@RequestParam int championshipId, @RequestParam int teamId) {
        championshipService.addTeam(championshipId, teamId);
        return "redirect:/";
    }

    @PostMapping("/ui/championships/removeTeam")
    public String removeTeamFromChampionship(@RequestParam int championshipId, @RequestParam int teamId) {
        championshipService.removeTeam(championshipId, teamId);
        return "redirect:/";
    }

    // --------- Form classes ---------
    @Data
    public static class TeamForm {
        private Integer id;
        private String name;
        private String city;
    }

    @Data
    public static class ParticipantForm {
        private Integer id;
        private String firstName;
        private String lastName;
        private String nickname;
        private Integer teamId; // може бути null
    }

    @Data
    public static class ChampionshipForm {
        private Integer id;
        private String title;
        private String game;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
