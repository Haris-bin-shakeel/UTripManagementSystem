package view;

import controller.TripController;
import model.Teacher;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CreateTripView extends JPanel {
    private JTextField destField, purposeField, dateField, timeField, budgetField;
    private JSpinner participantsSpinner;
    private Teacher teacher;
    private TripController tripController;
    private Runnable onSuccess;

    public CreateTripView(Teacher teacher, TripController tripController, Runnable onSuccess) {
        this.teacher = teacher;
        this.tripController = tripController;
        this.onSuccess = onSuccess;

        setOpaque(false);
        setLayout(new BorderLayout());
        
        GlassPanel card = new GlassPanel(UIConstants.NEON_CYAN);
        card.setPreferredSize(new Dimension(500, 500));
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;

        JLabel title = new JLabel("✈ New Trip Request", SwingConstants.CENTER);
        title.setForeground(UIConstants.NEON_CYAN);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        card.add(title, gbc);

        gbc.insets = new Insets(5, 0, 5, 0);
        
        destField = createStyledField(card, gbc, 1, "DESTINATION");
        purposeField = createStyledField(card, gbc, 3, "PURPOSE");
        dateField = createStyledField(card, gbc, 5, "DATE (DD/MM/YYYY)");
        timeField = createStyledField(card, gbc, 7, "TIME");
        
        // Participants Spinner
        JLabel pLabel = new JLabel("ESTIMATED PARTICIPANTS");
        pLabel.setForeground(UIConstants.TEXT_MUTED);
        pLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        gbc.gridy = 9;
        card.add(pLabel, gbc);

        participantsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        participantsSpinner.setBackground(UIConstants.BG_CARD);
        JComponent editor = participantsSpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setBackground(UIConstants.BG_CARD);
        tf.setForeground(Color.WHITE);
        tf.setBorder(new EmptyBorder(10, 12, 10, 12));
        participantsSpinner.setBorder(new MatteBorder(0, 0, 1, 0, UIConstants.NEON_PURPLE));
        gbc.gridy = 10;
        card.add(participantsSpinner, gbc);

        budgetField = createStyledField(card, gbc, 11, "BUDGET ESTIMATE");

        NeonButton submitBtn = new NeonButton("🚀 SUBMIT TRIP REQUEST", UIConstants.NEON_PURPLE);
        submitBtn.setPreferredSize(new Dimension(0, 50));
        gbc.gridy = 13;
        gbc.insets = new Insets(20, 0, 0, 0);
        card.add(submitBtn, gbc);

        add(card, BorderLayout.CENTER);

        submitBtn.addActionListener(e -> handleSubmit());
    }

    private JTextField createStyledField(JPanel panel, GridBagConstraints gbc, int row, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setForeground(UIConstants.TEXT_MUTED);
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        gbc.gridy = row;
        panel.add(label, gbc);

        JTextField field = new JTextField();
        field.setBackground(UIConstants.BG_CARD);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, UIConstants.NEON_PURPLE),
                new EmptyBorder(10, 12, 10, 12)
        ));
        gbc.gridy = row + 1;
        panel.add(field, gbc);
        return field;
    }

    private void handleSubmit() {
        String dest = destField.getText().trim();
        String purpose = purposeField.getText().trim();
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();
        int participants = (int) participantsSpinner.getValue();
        double budget = 0;

        try {
            budget = Double.parseDouble(budgetField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid budget format", "Data Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dest.isEmpty() || purpose.isEmpty() || date.isEmpty() || time.isEmpty() || budget <= 0) {
            JOptionPane.showMessageDialog(this, "All fields are mandatory and must be valid", "Validation Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tripController.createTrip(teacher, dest, purpose, date, time, participants, budget);
        JOptionPane.showMessageDialog(this, "Trip request broadcasted to Admin Portal!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear fields
        destField.setText("");
        purposeField.setText("");
        dateField.setText("");
        timeField.setText("");
        budgetField.setText("");
        participantsSpinner.setValue(1);
        
        if (onSuccess != null) onSuccess.run();
    }
}
