package main;

import util.DataStore;
import view.LoginView;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Initialize sample data
        DataStore.initSampleData();

        // UI Enhancements
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {}

        UIManager.put("OptionPane.background", new Color(15, 15, 35));
        UIManager.put("Panel.background", new Color(15, 15, 35));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(139, 92, 246));
        UIManager.put("Button.foreground", Color.WHITE);
        
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Launch LoginView on EDT
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
