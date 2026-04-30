package view;

import controller.ResourceController;
import controller.TripController;
import model.*;
import lifecycle.TripStatus;
import util.DataStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private Admin admin;
    private TripController tripController = new TripController();
    private ResourceController resourceController = new ResourceController();
    
    private JTable pendingTable, assignTable, allTable;
    private DefaultTableModel pendingModel, assignModel, allModel;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        setTitle("Admin Portal — " + admin.getName());
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        StarfieldPanel mainContent = new StarfieldPanel();
        mainContent.setLayout(new BorderLayout());
        setContentPane(mainContent);

        // --- Header Bar ---
        GlassPanel header = new GlassPanel(UIConstants.NEON_PURPLE);
        header.setPreferredSize(new Dimension(1000, 70));
        header.setLayout(new BorderLayout(20, 0));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel portalLabel = new JLabel("⚙ Admin Portal", SwingConstants.LEFT);
        portalLabel.setForeground(Color.WHITE);
        portalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(portalLabel, BorderLayout.WEST);

        JLabel nameLabel = new JLabel(admin.getName(), SwingConstants.CENTER);
        nameLabel.setForeground(UIConstants.NEON_CYAN);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(nameLabel, BorderLayout.CENTER);

        NeonButton logoutBtn = new NeonButton("Logout", UIConstants.NEON_RED);
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
        header.add(logoutBtn, BorderLayout.EAST);

        mainContent.add(header, BorderLayout.NORTH);

        // --- Tabbed Pane ---
        UIManager.put("TabbedPane.background", UIConstants.BG_DARK);
        UIManager.put("TabbedPane.selected", UIConstants.NEON_PURPLE);
        UIManager.put("TabbedPane.foreground", UIConstants.TEXT_SOFT);
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        tabbedPane.addTab(" Pending Trips ", createPendingPanel());
        tabbedPane.addTab(" Assign Resources ", createAssignPanel());
        tabbedPane.addTab(" All Trips History ", createHistoryPanel());

        mainContent.add(tabbedPane, BorderLayout.CENTER);
        refreshAll();
    }

    private JPanel createPendingPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        pendingModel = new DefaultTableModel(new String[]{"Trip ID", "Destination", "Date", "Teacher"}, 0);
        pendingTable = new JTable(pendingModel);
        CustomTableStyle.apply(pendingTable);
        
        JScrollPane scroll = new JScrollPane(pendingTable);
        scroll.getViewport().setBackground(UIConstants.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 92, 246, 60), 1));
        card.add(scroll, BorderLayout.CENTER);

        NeonButton reviewBtn = new NeonButton("OPEN REVIEW CONSOLE", UIConstants.NEON_CYAN);
        reviewBtn.setPreferredSize(new Dimension(0, 45));
        reviewBtn.addActionListener(e -> {
            int row = pendingTable.getSelectedRow();
            if (row != -1) {
                String tripId = (String) pendingModel.getValueAt(row, 0);
                TripRequest trip = tripController.getTripById(tripId);
                new TripApprovalView(this, admin, trip, tripController, this::refreshAll).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Select a trip to review");
            }
        });
        card.add(reviewBtn, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAssignPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        assignModel = new DefaultTableModel(new String[]{"ID", "Destination", "Vehicle", "Driver"}, 0);
        assignTable = new JTable(assignModel);
        CustomTableStyle.apply(assignTable);
        
        JScrollPane scroll = new JScrollPane(assignTable);
        scroll.getViewport().setBackground(UIConstants.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 92, 246, 60), 1));
        card.add(scroll, BorderLayout.CENTER);

        JPanel assignForm = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        assignForm.setOpaque(false);
        
        JComboBox<String> vehicleCombo = new JComboBox<>();
        JComboBox<String> driverCombo = new JComboBox<>();
        for (Vehicle v : DataStore.vehicles) if (v.isAvailable()) vehicleCombo.addItem(v.getVehicleId());
        for (User u : DataStore.users) if (u instanceof Driver) driverCombo.addItem(u.getUserId());

        JLabel vLabel = new JLabel("VEHICLE:"); vLabel.setForeground(UIConstants.TEXT_MUTED);
        JLabel dLabel = new JLabel("DRIVER:"); dLabel.setForeground(UIConstants.TEXT_MUTED);
        
        assignForm.add(vLabel); assignForm.add(vehicleCombo);
        assignForm.add(dLabel); assignForm.add(driverCombo);
        
        NeonButton assignBtn = new NeonButton("DEPLOY RESOURCES", UIConstants.NEON_GREEN);
        assignBtn.setPreferredSize(new Dimension(180, 40));
        assignBtn.addActionListener(e -> {
            int row = assignTable.getSelectedRow();
            if (row != -1) {
                String tripId = (String) assignModel.getValueAt(row, 0);
                String vId = (String) vehicleCombo.getSelectedItem();
                String dId = (String) driverCombo.getSelectedItem();
                if (vId != null && dId != null) {
                    resourceController.assignVehicle(admin, tripId, vId);
                    resourceController.assignDriver(admin, tripId, dId);
                    JOptionPane.showMessageDialog(this, "Mission Ready: Resources deployed and enrollment opened!");
                    refreshAll();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select an approved trip to assign resources");
            }
        });
        assignForm.add(assignBtn);
        card.add(assignForm, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createHistoryPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        allModel = new DefaultTableModel(new String[]{"Trip ID", "Destination", "Date", "Current Status"}, 0);
        allTable = new JTable(allModel);
        CustomTableStyle.apply(allTable);
        
        JScrollPane scroll = new JScrollPane(allTable);
        scroll.getViewport().setBackground(UIConstants.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 92, 246, 60), 1));
        card.add(scroll, BorderLayout.CENTER);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private void refreshAll() {
        pendingModel.setRowCount(0);
        for (TripRequest t : tripController.getTripsByStatus(TripStatus.PENDING)) {
            pendingModel.addRow(new Object[]{t.getTripId(), t.getDestination(), t.getDate(), t.getTeacherId()});
        }

        assignModel.setRowCount(0);
        for (TripRequest t : tripController.getTripsByStatus(TripStatus.APPROVED)) {
            assignModel.addRow(new Object[]{t.getTripId(), t.getDestination(), t.getVehicleId(), t.getDriverId()});
        }

        allModel.setRowCount(0);
        for (TripRequest t : tripController.getAllTrips()) {
            allModel.addRow(new Object[]{t.getTripId(), t.getDestination(), t.getDate(), t.getStatus()});
        }
    }
}
