package com.belote.repository;

import com.belote.config.DatabaseConfig;
import com.belote.domain.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentRepository {

    public List<Tournament> findAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        String sql = "SELECT id_tournoi, nb_matchs, nom_tournoi, statut FROM tournois";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tournament t = new Tournament(
                    rs.getInt("id_tournoi"),
                    rs.getString("nom_tournoi"),
                    rs.getInt("statut"),
                    rs.getInt("nb_matchs")
                );
                tournaments.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournaments;
    }

    public Tournament findByName(String name) {
        String sql = "SELECT id_tournoi, nb_matchs, nom_tournoi, statut FROM tournois WHERE nom_tournoi = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Tournament(
                        rs.getInt("id_tournoi"),
                        rs.getString("nom_tournoi"),
                        rs.getInt("statut"),
                        rs.getInt("nb_matchs")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(Tournament tournament) {
        // If id=0 => insert, else update
        if (tournament.getId() == 0) {
            return insertTournament(tournament);
        } else {
            return updateTournament(tournament);
        }
    }

    private boolean insertTournament(Tournament tournament) {
        String sql = "INSERT INTO tournois (nb_matchs, nom_tournoi, statut) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournament.getNbMatches());
            ps.setString(2, tournament.getName());
            ps.setInt(3, tournament.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateTournament(Tournament tournament) {
        String sql = "UPDATE tournois SET nb_matchs=?, nom_tournoi=?, statut=? WHERE id_tournoi=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournament.getNbMatches());
            ps.setString(2, tournament.getName());
            ps.setInt(3, tournament.getStatus());
            ps.setInt(4, tournament.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTournament(int id) {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement psMatch = conn.prepareStatement("DELETE FROM matchs WHERE id_tournoi=?");
             PreparedStatement psTeam = conn.prepareStatement("DELETE FROM equipes WHERE id_tournoi=?");
             PreparedStatement psT = conn.prepareStatement("DELETE FROM tournois WHERE id_tournoi=?")) {
            psMatch.setInt(1, id);
            psMatch.executeUpdate();
            psTeam.setInt(1, id);
            psTeam.executeUpdate();
            psT.setInt(1, id);
            psT.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
