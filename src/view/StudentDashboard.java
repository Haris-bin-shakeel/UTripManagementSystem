package view;

import controller.EnrollmentController;
import controller.TripController;
import model.Enrollment;
import model.Student;
import model.TripRequest;
import lifecycle.TripStatus;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {
    private Student student;
    private TripController tripController = new TripController();
    private EnrollmentController enrollmentController = new EnrollmentController();
    private JTable availTable, myEnrTable;
    private DefaultTableModel availModel, myEnrModel;

    public StudentDashboard(Student student) {
        this.student = student;
        setTitle("Student Portal — " + student.getName());
        setSize(880, 630);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        StarfieldPanel mainContent = new StarfieldPanel();
        mainContent.setLayout(new BorderLayout());
        setContentPane(mainContent);

        // Header
        GlassPanel header = new GlassPanel(UIConstants.NEON_PURPLE);
        header.setPreferredSize(new Dimension(880, 70));
        header.setLayout(new BorderLayout(20, 0));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel portalLabel = new JLabel("🎓 Student Portal", SwingConstants.LEFT);
        portalLabel.setForeground(Color.WHITE);
        portalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(portalLabel, BorderLayout.WEST);

        JLabel nameLabel = new JLabel(student.getName(), SwingConstants.CENTER);
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

        tabbedPane.addTab(" 🌏 Discover Trips ", createDiscoveryPanel());
        tabbedPane.addTab(" 📝 My Enrollments ", createMyEnrollmentsPanel());

        mainContent.add(tabbedPane, BorderLayout.CENTER);
        refreshAvailableTrips();
        refreshMyEnrollments();
    }

    private JPanel createDiscoveryPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        availModel = new DefaultTableModel(new String[]{"Trip ID", "Destination", "Date", "Capacity"}, 0);
        availTable = new JTable(availModel);
        CustomTableStyle.apply(availTable);
        
        JScrollPane scroll = new JScrollPane(availTable);
        scroll.getViewport().setBackground(UIConstants.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 92, 246, 60), 1));
        card.add(scroll, BorderLayout.CENTER);

        NeonButton enrollBtn = new NeonButton("JOIN SELECTED TRIP", UIConstants.NEON_GREEN);
        enrollBtn.setPreferredSize(new Dimension(0, 45));
        enrollBtn.addActionListener(e -> {
            int row = availTable.getSelectedRow();
            if (row != -1) {
                String tripId = (String) availModel.getValueAt(row, 0);
                TripRequest trip = tripController.getTripById(tripId);
                
                JDialog dialog = new JDialog(this, "Trip Details", true);
                dialog.setLayout(new BorderLayout());
                dialog.add(new StudentEnrollmentView(student, trip, enrollmentController, () -> {
                    dialog.dispose();
                    refreshAvailableTrips();
                    refreshMyEnrollments();
                }));
                dialog.pack();
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Select a trip to enroll");
            }
        });
        card.add(enrollBtn, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMyEnrollmentsPanel() {
        StarfieldPanel panel = new StarfieldPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        myEnrModel = new DefaultTableModel(new String[]{"Enroll ID", "Trip ID", "Date", "Status"}, 0);
        myEnrTable = new JTable(myEnrModel);
        CustomTableStyle.apply(myEnrTable);
        
        JScrollPane scroll = new JScrollPane(myEnrTable);
        scroll.getViewport().setBackground(UIConstants.BG_DARK);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(139, 92, 246, 60), 1));
        card.add(scroll, BorderLayout.CENTER);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private void refreshAvailableTrips() {
        availModel.setRowCount(0);
        List<TripRequest> trips = tripController.getTripsByStatus(TripStatus.RESOURCE_ASSIGNED);
        for (TripRequest t : trips) {
            availModel.addRow(new Object[]{t.getTripId(), t.getDestination(), t.getDate(), t.getEstimatedCount()});
        }
    }

    private void refreshMyEnrollments() {
        myEnrModel.setRowCount(0);
        List<Enrollment> list = enrollmentController.getEnrollmentsForStudent(student.getUserId());
        for (Enrollment e : list) {
            myEnrModel.addRow(new Object[]{e.getEnrollId(), e.getTripId(), e.getEnrollDate(), e.isAttendanceMarked() ? "Present" : "Confirmed"});
        }
    }
}
