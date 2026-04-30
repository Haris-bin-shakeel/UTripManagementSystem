package view;

import controller.TripController;
import model.Admin;
import model.TripRequest;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TripApprovalView extends JDialog {
    private JTextArea remarkArea;
    private TripRequest trip;
    private Admin admin;
    private TripController tripController;
    private Runnable onComplete;

    public TripApprovalView(JFrame parent, Admin admin, TripRequest trip, TripController tripController, Runnable onComplete) {
        super(parent, "Review Console", true);
        this.admin = admin;
        this.trip = trip;
        this.tripController = tripController;
        this.onComplete = onComplete;

        setSize(480, 520);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(UIConstants.BG_DARK);
        setLayout(new BorderLayout(20, 20));

        // Header Title
        JLabel title = new JLabel("TRIP REVIEW CONSOLE", SwingConstants.CENTER);
        title.setForeground(UIConstants.NEON_PURPLE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setBorder(new EmptyBorder(20, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        // Center Card
        JPanel mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setOpaque(false);
        mainWrapper.setBorder(new EmptyBorder(0, 20, 0, 20));

        GlassPanel card = new GlassPanel(UIConstants.NEON_CYAN);
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;

        addDetailRow(card, gbc, 0, "DESTINATION", trip.getDestination());
        addDetailRow(card, gbc, 1, "REQUESTED BY", trip.getTeacherId());
        addDetailRow(card, gbc, 2, "PROPOSED DATE", trip.getDate());
        addDetailRow(card, gbc, 3, "BUDGET ESTIMATE", "$" + trip.getBudgetEstimate());

        // Remarks
        gbc.gridy = 4;
        gbc.insets = new Insets(15, 0, 5, 0);
        JLabel rLabel = new JLabel("ADMIN REMARKS (MANDATORY FOR REJECTION)");
        rLabel.setForeground(UIConstants.TEXT_MUTED);
        rLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        card.add(rLabel, gbc);

        remarkArea = new JTextArea(4, 20);
        remarkArea.setBackground(UIConstants.BG_CARD);
        remarkArea.setForeground(Color.WHITE);
        remarkArea.setCaretColor(Color.WHITE);
        remarkArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        remarkArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UIConstants.NEON_PURPLE, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridy = 5;
        card.add(new JScrollPane(remarkArea), gbc);

        mainWrapper.add(card, BorderLayout.CENTER);
        add(mainWrapper, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        
        NeonButton approveBtn = new NeonButton("✅ APPROVE TRIP", UIConstants.NEON_GREEN);
        approveBtn.setPreferredSize(new Dimension(0, 45));
        NeonButton rejectBtn = new NeonButton("❌ REJECT TRIP", UIConstants.NEON_RED);
        rejectBtn.setPreferredSize(new Dimension(0, 45));
        
        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);
        add(btnPanel, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> {
            tripController.approveTrip(admin, trip.getTripId(), remarkArea.getText());
            dispose();
            if (onComplete != null) onComplete.run();
        });

        rejectBtn.addActionListener(e -> {
            String remark = remarkArea.getText().trim();
            if (remark.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Remarks are mandatory for rejection");
                return;
            }
            tripController.rejectTrip(admin, trip.getTripId(), remark);
            dispose();
            if (onComplete != null) onComplete.run();
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
