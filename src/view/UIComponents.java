package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

class UIConstants {
    public static final Color BG_DARK = new Color(8, 8, 20);
    public static final Color BG_PANEL = new Color(15, 15, 35);
    public static final Color BG_CARD = new Color(20, 20, 45);
    public static final Color NEON_PURPLE = new Color(139, 92, 246);
    public static final Color NEON_CYAN = new Color(6, 182, 212);
    public static final Color NEON_GREEN = new Color(16, 185, 129);
    public static final Color NEON_RED = new Color(239, 68, 68);
    public static final Color NEON_YELLOW = new Color(245, 158, 11);
    public static final Color GLASS_BORDER = new Color(139, 92, 246, 80);
    public static final Color TEXT_WHITE = new Color(255, 255, 255);
    public static final Color TEXT_SOFT = new Color(203, 213, 225);
    public static final Color TEXT_MUTED = new Color(100, 116, 139);
    public static final Color GLOW_PURPLE = new Color(139, 92, 246, 40);
}

class GlassPanel extends JPanel {
    private Color borderColor;

    public GlassPanel(Color borderColor) {
        this.borderColor = borderColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Glass background
        g2.setColor(new Color(20, 20, 50, 200));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        // Neon border glow - outer
        g2.setColor(new Color(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(), 60));
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
        // Neon border - inner sharp
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 18, 18);
        g2.dispose();
        super.paintComponent(g);
    }
}

class NeonButton extends JButton {
    private Color neonColor;

    public NeonButton(String text, Color neonColor) {
        super(text);
        this.neonColor = neonColor;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Glow layer
        g2.setColor(new Color(neonColor.getRed(), neonColor.getGreen(), neonColor.getBlue(), 60));
        g2.fillRoundRect(-3, -3, getWidth() + 6, getHeight() + 6, 16, 16);
        // Button fill
        g2.setColor(new Color(neonColor.getRed(), neonColor.getGreen(), neonColor.getBlue(), 180));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        // Border
        g2.setColor(neonColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
        g2.dispose();
        super.paintComponent(g);
    }
}

class StarfieldPanel extends JPanel {
    private int[][] stars;

    public StarfieldPanel() {
        setOpaque(true);
        setBackground(new Color(8, 8, 20));
        stars = new int[80][3]; // x, y, size
        Random r = new Random(42);
        for (int i = 0; i < 80; i++) {
            stars[i][0] = r.nextInt(1920);
            stars[i][1] = r.nextInt(1080);
            stars[i][2] = r.nextInt(2) + 1;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(139, 92, 246, 30));
        g2.fillOval(-200, -200, 600, 600); // top-left glow orb
        g2.setColor(new Color(6, 182, 212, 20));
        g2.fillOval(getWidth() - 300, getHeight() - 300, 500, 500); // bottom-right glow orb
        g2.setColor(new Color(255, 255, 255, 150));
        for (int[] star : stars) {
            g2.fillOval(star[0] % getWidth(), star[1] % getHeight(), star[2], star[2]);
        }
        g2.dispose();
    }
}

class CustomTableStyle {
    public static void apply(JTable table) {
        table.setBackground(UIConstants.BG_PANEL);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(42);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(139, 92, 246, 180));
        table.setSelectionForeground(Color.WHITE);
        
        table.getTableHeader().setBackground(new Color(20, 20, 50));
        table.getTableHeader().setForeground(UIConstants.NEON_PURPLE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
    }
}
