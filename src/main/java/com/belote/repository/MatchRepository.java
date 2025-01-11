package com.belote.repository;

import com.belote.config.DatabaseConfig;
import com.belote.domain.MatchEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchRepository {

    public List<MatchEntity> findMatchesByTournament(int tournamentId) {
        List<MatchEntity> matches = new ArrayList<>();
        String sql = "SELECT * FROM matchs WHERE id_tournoi=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tournamentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MatchEntity m = new MatchEntity(
                        rs.getInt("id_match"),
                        rs.getInt("equipe1"),
                        rs.getInt("equipe2"),
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("num_tour"),
                        "oui".equals(rs.getString("termine"))
                    );
                    matches.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }

    public boolean updateMatch(MatchEntity match) {
        String sql = "UPDATE matchs SET score1=?, score2=?, termine=? WHERE id_match=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, match.getScore1());
            ps.setInt(2, match.getScore2());
            ps.setString(3, match.isFinished() ? "oui" : "non");
            ps.setInt(4, match.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Insert / Delete logic if needed, e.g., for generating new matches or removing them
}
