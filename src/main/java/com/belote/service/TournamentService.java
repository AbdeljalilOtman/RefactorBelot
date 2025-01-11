package com.belote.service;

import com.belote.domain.MatchEntity;
import com.belote.domain.Team;
import com.belote.domain.Tournament;
import com.belote.repository.MatchRepository;
import com.belote.repository.TeamRepository;
import com.belote.repository.TournamentRepository;

import java.util.List;

/**
 * Contains business logic for handling Tournaments, Teams, and Matches.
 */
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public TournamentService() {
        this.tournamentRepository = new TournamentRepository();
        this.teamRepository = new TeamRepository();
        this.matchRepository = new MatchRepository();
    }

    /**
     * Creates a new tournament with the given name.
     */
    public boolean createTournament(String name) {
        if (name == null || name.isEmpty()) return false;
        // We can add checks to see if name is taken, etc.
        Tournament t = new Tournament(0, name, 0, 10);
        return tournamentRepository.save(t);
    }

    public List<Tournament> listAllTournaments() {
        return tournamentRepository.findAllTournaments();
    }

    public Tournament findTournamentByName(String name) {
        return tournamentRepository.findByName(name);
    }

    public boolean deleteTournament(int tournamentId) {
        return tournamentRepository.deleteTournament(tournamentId);
    }

    // --- Teams ---
    public List<Team> getTeamsForTournament(int tournamentId) {
        return teamRepository.findTeamsByTournament(tournamentId);
    }

    public boolean addTeam(int tournamentId, int number, String p1, String p2) {
        return teamRepository.insertTeam(tournamentId, number, p1, p2);
    }

    public boolean updateTeam(Team team) {
        return teamRepository.updateTeam(team);
    }

    public boolean deleteTeam(int teamId) {
        return teamRepository.deleteTeam(teamId);
    }

    // --- Matches ---
    public List<MatchEntity> getMatchesForTournament(int tournamentId) {
        return matchRepository.findMatchesByTournament(tournamentId);
    }

    public boolean updateMatch(MatchEntity match) {
        // Optionally check if new scores require 'termine' = oui
        if (match.getScore1() > 0 || match.getScore2() > 0) {
            match.setFinished(true);
        }
        return matchRepository.updateMatch(match);
    }

    // Additional business logic for generating schedules, adding rounds, etc. goes here
}
