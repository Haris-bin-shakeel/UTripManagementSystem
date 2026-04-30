package view;

import controller.AuthController;
import model.User;
import model.Admin;
import model.Teacher;
import model.Student;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;
    private JButton loginBtn;
    private AuthController authController = new AuthController();
    private Point initialClick;

    public LoginView() {
        setUndecorated(true);
        setTitle("UTMS — Login");
        setSize(520, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        StarfieldPanel starfield = new StarfieldPanel();
        starfield.setLayout(new BorderLayout());
        setContentPane(starfield);

        // Custom Title Bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(UIConstants.BG_CARD);
        titleBar.setPreferredSize(new Dimension(520, 30));
        
        JLabel titleLabel = new JLabel("  UTMS Secure Portal");
        titleLabel.setForeground(UIConstants.TEXT_SOFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleBar.add(titleLabel, BorderLayout.WEST);

        JButton closeBtn = new JButton("✕");
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(UIConstants.NEON_RED);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setOpaque(true);
        closeBtn.setPreferredSize(new Dimension(45, 30));
        closeBtn.addActionListener(e -> System.exit(0));
        titleBar.add(closeBtn, BorderLayout.EAST);

        // Make window draggable
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        titleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });
        starfield.add(titleBar, BorderLayout.NORTH);

        // Center Glass Card
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        
        GlassPanel card = new GlassPanel(UIConstants.NEON_PURPLE);
        card.setPreferredSize(new Dimension(420, 480));
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Icon
        JLabel iconLabel = new JLabel("🎓", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        gbc.gridy = 0;
        card.add(iconLabel, gbc);

        // Title
        JLabel headLabel = new JLabel("UTMS", SwingConstants.CENTER);
        headLabel.setForeground(UIConstants.NEON_PURPLE);
        headLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        gbc.gridy = 1;
        card.add(headLabel, gbc);

        // Subtitle
        JLabel subLabel = new JLabel("University Trip Management System", SwingConstants.CENTER);
        subLabel.setForeground(UIConstants.TEXT_MUTED);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 2;
        card.add(subLabel, gbc);

        // Divider
        JPanel divider = new JPanel();
        divider.setBackground(UIConstants.GLASS_BORDER);
        divider.setPreferredSize(new Dimension(100, 1));
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 40, 20, 40);
        card.add(divider, gbc);

        // Email field
        gbc.insets = new Insets(5, 40, 0, 40);
        JLabel emailHint = new JLabel("EMAIL ADDRESS");
        emailHint.setForeground(UIConstants.TEXT_MUTED);
        emailHint.setFont(new Font("Segoe UI", Font.BOLD, 10));
        gbc.gridy = 4;
        card.add(emailHint, gbc);

        emailField = new JTextField();
        emailField.setBackground(UIConstants.BG_PANEL);
        emailField.setForeground(UIConstants.TEXT_WHITE);
        emailField.setCaretColor(Color.WHITE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, UIConstants.NEON_PURPLE),
                new EmptyBorder(10, 12, 10, 12)
        ));
        gbc.gridy = 5;
        card.add(emailField, gbc);

        // Password field
        gbc.insets = new Insets(15, 40, 0, 40);
        JLabel passHint = new JLabel("PASSWORD");
        passHint.setForeground(UIConstants.TEXT_MUTED);
        passHint.setFont(new Font("Segoe UI", Font.BOLD, 10));
        gbc.gridy = 6;
        card.add(passHint, gbc);

        passField = new JPasswordField();
        passField.setBackground(UIConstants.BG_PANEL);
        passField.setForeground(UIConstants.TEXT_WHITE);
        passField.setCaretColor(Color.WHITE);
        passField.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, UIConstants.NEON_PURPLE),
                new EmptyBorder(10, 12, 10, 12)
        ));
        gbc.gridy = 7;
        card.add(passField, gbc);

        // Login Button
        gbc.insets = new Insets(30, 40, 10, 40);
        loginBtn = new NeonButton("LOGIN TO PORTAL", UIConstants.NEON_PURPLE);
        loginBtn.setPreferredSize(new Dimension(0, 45));
        gbc.gridy = 8;
        card.add(loginBtn, gbc);

        // Footer
        JLabel footer = new JLabel("Secure University Portal • UTMS v1.0", SwingConstants.CENTER);
        footer.setForeground(UIConstants.TEXT_MUTED);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        gbc.gridy = 9;
        gbc.insets = new Insets(20, 40, 10, 40);
        card.add(footer, gbc);

        centerWrapper.add(card);
        starfield.add(centerWrapper, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword()).trim();
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide both Email and Password.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        User user = authController.login(email, password);
        if (user != null) {
            dispose();
            if (user instanceof Admin) {
                new AdminDashboard((Admin) user).setVisible(true);
            } else if (user instanceof Teacher) {
                new TeacherDashboard((Teacher) user).setVisible(true);
            } else if (user instanceof Student) {
                new StudentDashboard((Student) user).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
