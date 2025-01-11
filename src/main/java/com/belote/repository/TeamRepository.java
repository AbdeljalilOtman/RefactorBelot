package com.belote.repository;

import com.belote.config.DatabaseConfig;
import com.belote.domain.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    public List<Team> findTeamsByTournament(int tournamentId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT id_equipe, num_equipe, nom_j1, nom_j2 FROM equipes WHERE id_tournoi=? ORDER BY num_equipe";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournamentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Team team = new Team(
                        rs.getInt("id_equipe"),
                        rs.getInt("num_equipe"),
                        rs.getString("nom_j1"),
                        rs.getString("nom_j2")
                    );
                    teams.add(team);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public boolean insertTeam(int tournamentId, int number, String p1, String p2) {
        String sql = "INSERT INTO equipes (num_equipe, id_tournoi, nom_j1, nom_j2) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, number);
            ps.setInt(2, tournamentId);
            ps.setString(3, p1);
            ps.setString(4, p2);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTeam(Team team) {
        String sql = "UPDATE equipes SET nom_j1=?, nom_j2=? WHERE id_equipe=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, team.getPlayer1());
            ps.setString(2, team.getPlayer2());
            ps.setInt(3, team.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTeam(int teamId) {
        String sql = "DELETE FROM equipes WHERE id_equipe=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
