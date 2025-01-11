package com.belote.ui;

import com.belote.domain.MatchEntity;
import com.belote.domain.Team;
import com.belote.domain.Tournament;
import com.belote.service.TournamentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final TournamentService tournamentService;

    // UI components
    private JLabel statusLabel;
    private JList<String> tournamentList;
    private JButton createTournamentBtn;
    private JButton selectTournamentBtn;
    private JButton deleteTournamentBtn;

    private Tournament currentTournament;

    public MainFrame() {
        super("Belote Tournament Manager");
        this.tournamentService = new TournamentService();

        initComponents();
        layoutComponents();
        bindListeners();

        refreshTournaments();
    }

    private void initComponents() {
        statusLabel = new JLabel("No tournament selected");
        createTournamentBtn = new JButton("Create Tournament");
        selectTournamentBtn = new JButton("Select Tournament");
        deleteTournamentBtn = new JButton("Delete Tournament");
        tournamentList = new JList<>();
    }

    private void layoutComponents() {
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(statusLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(tournamentList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createTournamentBtn);
        bottomPanel.add(selectTournamentBtn);
        bottomPanel.add(deleteTournamentBtn);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void bindListeners() {
        createTournamentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTournament();
            }
        });
        selectTournamentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTournament();
            }
        });
        deleteTournamentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTournament();
            }
        });
    }

    private void refreshTournaments() {
        List<Tournament> tournaments = tournamentService.listAllTournaments();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Tournament t : tournaments) {
            model.addElement(t.getName());
        }
        tournamentList.setModel(model);
        tournamentList.revalidate();
        tournamentList.repaint();

        if (model.isEmpty()) {
            selectTournamentBtn.setEnabled(false);
            deleteTournamentBtn.setEnabled(false);
        } else {
            selectTournamentBtn.setEnabled(true);
            deleteTournamentBtn.setEnabled(true);
            tournamentList.setSelectedIndex(0);
        }
    }

    private void createTournament() {
        String name = JOptionPane.showInputDialog(this, "Tournament name?");
        if (name != null && !name.trim().isEmpty()) {
            boolean created = tournamentService.createTournament(name.trim());
            if (!created) {
                JOptionPane.showMessageDialog(this,
                        "Failed to create tournament. Possibly a duplicate name or invalid input.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            refreshTournaments();
        }
    }

    private void selectTournament() {
        String selectedName = tournamentList.getSelectedValue();
        if (selectedName == null) return;
        this.currentTournament = tournamentService.findTournamentByName(selectedName);
        if (this.currentTournament == null) {
            JOptionPane.showMessageDialog(this, "Tournament not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        statusLabel.setText("Selected: " + currentTournament.getName());
        // Now we can show another panel or card layout to handle teams, matches, etc.
        openTournamentDetails();
    }

    private void openTournamentDetails() {
        // For brevity, you could load a new panel or window with teams, matches, etc.
        // e.g., new TournamentDetailsDialog(this, tournamentService, currentTournament).setVisible(true);
        JOptionPane.showMessageDialog(this,
            "Here you'd show the details for tournament: " + currentTournament.getName(),
            "Tournament Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteTournament() {
        String selectedName = tournamentList.getSelectedValue();
        if (selectedName == null) return;
        Tournament t = tournamentService.findTournamentByName(selectedName);
        if (t != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + t.getName() + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (tournamentService.deleteTournament(t.getId())) {
                    JOptionPane.showMessageDialog(this, "Tournament deleted.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    refreshTournaments();
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting tournament", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
