package view;

import controller.EnrollmentController;
import model.Student;
import model.TripRequest;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentEnrollmentView extends JPanel {
    private Student student;
    private TripRequest trip;
    private EnrollmentController enrollmentController;
    private Runnable onEnroll;

    public StudentEnrollmentView(Student student, TripRequest trip, EnrollmentController enrollmentController, Runnable onEnroll) {
        this.student = student;
        this.trip = trip;
        this.enrollmentController = enrollmentController;
        this.onEnroll = onEnroll;

        setOpaque(false);
        setLayout(new BorderLayout());
        
        GlassPanel card = new GlassPanel(UIConstants.NEON_CYAN);
        card.setPreferredSize(new Dimension(400, 350));
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        JLabel title = new JLabel("TRIP CONFIRMATION", SwingConstants.CENTER);
        title.setForeground(UIConstants.NEON_CYAN);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        card.add(title, gbc);

        addDetailRow(card, gbc, 1, "DESTINATION", trip.getDestination());
        addDetailRow(card, gbc, 2, "DATE", trip.getDate());
        addDetailRow(card, gbc, 3, "PURPOSE", trip.getPurpose());
        addDetailRow(card, gbc, 4, "AVAILABLE SEATS", String.valueOf(trip.getEstimatedCount()));

        NeonButton confirmBtn = new NeonButton("🚀 CONFIRM ENROLLMENT", UIConstants.NEON_PURPLE);
        confirmBtn.setPreferredSize(new Dimension(0, 50));
        gbc.gridy = 5;
        gbc.insets = new Insets(25, 0, 0, 0);
        card.add(confirmBtn, gbc);

        add(card, BorderLayout.CENTER);

        confirmBtn.addActionListener(e -> {
            if (enrollmentController.enrollStudent(student, trip.getTripId()) != null) {
                JOptionPane.showMessageDialog(this, "Mission Accomplished: Successfully enrolled in " + trip.getDestination());
                if (onEnroll != null) onEnroll.run();
            } else {
                JOptionPane.showMessageDialog(this, "Systems Error: Enrollment failed. Trip capacity exceeded.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void addDetailRow(JPanel p, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridy = row;
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setOpaque(false);
        
        JLabel l = new JLabel(label);
        l.setForeground(UIConstants.TEXT_MUTED);
        l.setFont(new Font("Segoe UI", Font.BOLD, 10));
        
        JLabel v = new JLabel(value, SwingConstants.RIGHT);
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        rowPanel.add(l, BorderLayout.WEST);
        rowPanel.add(v, BorderLayout.EAST);
        p.add(rowPanel, gbc);
    }
}
