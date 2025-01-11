package com.belote;

import com.belote.config.DatabaseConfig;
import com.belote.ui.MainFrame;

import javax.swing.*;
import java.io.File;

public class BeloteApplication {

    public static void main(String[] args) {
        // Initialize DB and ensure folder structure
        try {
            String storagePath = System.getenv("APPDATA") + File.separator + "jBelote";
            System.out.println("Storage folder: " + storagePath);
            File storageDir = new File(storagePath);
            if (!storageDir.isDirectory()) {
                storageDir.mkdir();
            }
            // Initialize DB config - sets up the connection URL
            DatabaseConfig.init(storageDir.getAbsolutePath());
            DatabaseConfig.importSQL(new File("create.sql"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error initializing the application. Check Java installation and folder permissions.\n" + e.getMessage(),
                "Initialization Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Launch MainFrame (UI)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }
}
