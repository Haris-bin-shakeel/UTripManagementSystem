package view;

import controller.TripController;
import model.Teacher;
import model.TripRequest;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TeacherDashboard extends JFrame {
    private Teacher teacher;
    private TripController tripController = new TripController();
    private JTable tripsTable;
    private DefaultTableModel tableModel;

    public TeacherDashboard(Teacher teacher) {
        this.teacher = teacher;
        setTitle("Teacher Portal — " + teacher.getName());
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        StarfieldPanel mainContent = new StarfieldPanel();
        mainContent.setLayout(new BorderLayout());
        setContentPane(mainContent);

        // Header
        GlassPanel header = new GlassPanel(UIConstants.NEON_PURPLE);
        header.setPreferredSize(new Dimension(900, 70));
        header.setLayout(new BorderLayout(20, 0));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel portalLabel = new JLabel("👨‍🏫 Teacher Portal", SwingConstants.LEFT);
        portalLabel.setForeground(Color.WHITE);
        portalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(portalLabel, BorderLayout.WEST);

        JLabel nameLabel = new JLabel(teacher.getName(), SwingConstants.CENTER);
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

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        tabbedPane.addTab(" My Active Trips ", createTripsPanel());
        tabbedPane.addTab(" ✈ New Trip Request ", createRequestPanel());

        mainContent.add(tabbedPane, BorderLayout.CENTER);
        refreshTripsTable();
    }

    private JPanel createTripsPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        tableModel = new DefaultTableModel(new String[]{"Trip ID", "Destination", "Date", "Current Status"}, 0);
        tripsTable = new JTable(tableModel);
        CustomTableStyle.apply(tripsTable);
        
        JScrollPane scroll = new JScrollPane(tripsTable);
        scroll.getViewport().setBackground(UIConstants.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 92, 246, 60), 1));
        card.add(scroll, BorderLayout.CENTER);

        NeonButton refreshBtn = new NeonButton("REFRESH DATA", UIConstants.NEON_CYAN);
        refreshBtn.setPreferredSize(new Dimension(0, 40));
        refreshBtn.addActionListener(e -> refreshTripsTable());
        card.add(refreshBtn, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRequestPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(new CreateTripView(teacher, tripController, this::refreshTripsTable));
        return panel;
    }

    private void refreshTripsTable() {
        tableModel.setRowCount(0);
        List<TripRequest> allTrips = tripController.getAllTrips();
        for (TripRequest t : allTrips) {
            if (t.getTeacherId().equals(teacher.getUserId())) {
                tableModel.addRow(new Object[]{t.getTripId(), t.getDestination(), t.getDate(), t.getStatus()});
            }
        }
    }
}
