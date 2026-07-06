import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class GameUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameLogic gameLogic;
    private JPanel gameplayBoardPanel;
    private JLabel statusLabel;

    private final Color WOOD_DARK = new Color(54, 29, 13);
    private final Color WOOD_MEDIUM = new Color(102, 51, 15);
    private final Color WOOD_LIGHT = new Color(160, 96, 48);
    private final Color WOOD_TEXT = new Color(245, 210, 160);
    private final Color DARK_3D = new Color(30, 30, 30);

    public GameUI() {
        gameLogic = new GameLogic();
        setTitle("Tic-Tac-Toe ★ Wood Edition");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createHomeScreen(), "HOME");
        mainPanel.add(createGridSelectionScreen(), "GRID_SELECT");
        mainPanel.add(createGameplayScreen(), "GAMEPLAY");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    private JPanel createWoodPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, WOOD_MEDIUM, w, 0, WOOD_DARK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);

                g2.setColor(new Color(40, 20, 10, 120));
                g2.setStroke(new BasicStroke(2));
                for (int i = 0; i < w; i += 75) {
                    g2.drawLine(i, 0, i, h);
                }
            }
        };
    }

    private JButton createWoodButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(35, 15, 5));
                g2.fill(new RoundRectangle2D.Float(0, 4, getWidth(), getHeight() - 4, 18, 18));

                GradientPaint faceGold = new GradientPaint(0, 0, WOOD_LIGHT, 0, getHeight(), WOOD_MEDIUM);
                g2.setPaint(faceGold);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 4, 18, 18));

                g2.setColor(new Color(255, 255, 255, 40));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 4, 18, 18));

                g2.setFont(new Font("Serif", Font.BOLD, 24));
                g2.setColor(WOOD_DARK);
                g2.drawString(getText(), (getWidth() - g2.getFontMetrics().stringWidth(getText())) / 2, (getHeight() / 2) + 6);
                g2.setColor(WOOD_TEXT);
                g2.drawString(getText(), (getWidth() - g2.getFontMetrics().stringWidth(getText())) / 2, (getHeight() / 2) + 4);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createHomeScreen() {
        JPanel panel = createWoodPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("TIC-X-O", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 65));
        titleLabel.setForeground(WOOD_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton onePlayerBtn = createWoodButton("1 Player");
        JButton twoPlayerBtn = createWoodButton("2 Players");

        onePlayerBtn.setMaximumSize(new Dimension(280, 60));
        twoPlayerBtn.setMaximumSize(new Dimension(280, 60));
        onePlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        twoPlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        onePlayerBtn.addActionListener(e -> {
            showCustomNameInputDialog();
        });

        twoPlayerBtn.addActionListener(e -> {
            showTwoPlayerNameInputDialog();
        });

        panel.add(Box.createVerticalStrut(100));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(100));
        panel.add(onePlayerBtn);
        panel.add(Box.createVerticalStrut(40));
        panel.add(twoPlayerBtn);

        return panel;
    }

    private JPanel createWoodDialogContentPanel(int width, int height) {
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WOOD_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(WOOD_MEDIUM);
                g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 20, 20);
            }
        };
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setPreferredSize(new Dimension(width, height));
        return content;
    }

    private JLabel createWoodDialogLabel(String text, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, fontSize));
        label.setForeground(WOOD_TEXT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createWoodTextField() {
        JTextField field = new JTextField(15);
        field.setMaximumSize(new Dimension(300, 42));
        field.setFont(new Font("Serif", Font.PLAIN, 20));
        field.setBackground(new Color(45, 22, 8));
        field.setForeground(WOOD_TEXT);
        field.setCaretColor(WOOD_TEXT);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createLineBorder(WOOD_LIGHT, 2));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        return field;
    }

    private void showTwoPlayerNameInputDialog() {
        JDialog dialog = new JDialog(this, "Player Setup", true);
        dialog.setUndecorated(true);
        dialog.setSize(420, 340);
        dialog.setLocationRelativeTo(this);

        JPanel content = createWoodDialogContentPanel(420, 340);

        JLabel promptLabel1 = createWoodDialogLabel("ENTER PLAYER 1 NAME:", 20);
        JTextField p1Field = createWoodTextField();

        JLabel promptLabel2 = createWoodDialogLabel("ENTER PLAYER 2 NAME:", 20);
        JTextField p2Field = createWoodTextField();

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setOpaque(false);

        JButton okBtn = createWoodButton("OK");
        JButton cancelBtn = createWoodButton("CANCEL");
        okBtn.setPreferredSize(new Dimension(140, 48));
        cancelBtn.setPreferredSize(new Dimension(140, 48));

        okBtn.addActionListener(e -> {
            String p1Name = p1Field.getText().trim();
            String p2Name = p2Field.getText().trim();
            gameLogic.setPlayer1Name(p1Name.isEmpty() ? "Player 1" : p1Name);
            gameLogic.setPlayer2Name(p2Name.isEmpty() ? "Player 2" : p2Name);
            gameLogic.initGame(3, false);
            dialog.dispose();
            cardLayout.show(mainPanel, "GRID_SELECT");
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        content.add(Box.createVerticalStrut(25));
        content.add(promptLabel1);
        content.add(Box.createVerticalStrut(12));
        content.add(p1Field);
        content.add(Box.createVerticalStrut(20));
        content.add(promptLabel2);
        content.add(Box.createVerticalStrut(12));
        content.add(p2Field);
        content.add(Box.createVerticalStrut(25));
        content.add(btnPanel);

        dialog.add(content);
        dialog.setVisible(true);
    }

    private void showCustomNameInputDialog() {
        JDialog dialog = new JDialog(this, "Player Identification", true);
        dialog.setUndecorated(true); 
        dialog.setSize(420, 240);
        dialog.setLocationRelativeTo(this);

        JPanel content = createWoodDialogContentPanel(420, 240);

        JLabel promptLabel = createWoodDialogLabel("ENTER YOUR NAME:", 22);
        JTextField nameField = createWoodTextField();

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.setOpaque(false);

        JButton okBtn = createWoodButton("OK");
        JButton cancelBtn = createWoodButton("CANCEL");
        okBtn.setPreferredSize(new Dimension(140, 48));
        cancelBtn.setPreferredSize(new Dimension(140, 48));

        okBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                name = "Player 1";
            }
            gameLogic.setPlayer1Name(name);
            gameLogic.initGame(3, true); 
            dialog.dispose();
            cardLayout.show(mainPanel, "GRID_SELECT");
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        content.add(Box.createVerticalStrut(30));
        content.add(promptLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(nameField);
        content.add(Box.createVerticalStrut(25));
        content.add(btnPanel);

        dialog.add(content);
        dialog.setVisible(true);
    }

    private JPanel createGridSelectionScreen() {
        JPanel panel = createWoodPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("CHOOSE A GRID", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 45));
        heading.setForeground(WOOD_TEXT);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel gridButtonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        gridButtonContainer.setOpaque(false);

        JButton g3x3 = createWoodButton("3 X 3");
        JButton g5x5 = createWoodButton("5 X 5");
        JButton g7x7 = createWoodButton("7 X 7");

        Dimension sizeDim = new Dimension(140, 70);
        g3x3.setPreferredSize(sizeDim);
        g5x5.setPreferredSize(sizeDim);
        g7x7.setPreferredSize(sizeDim);

        g3x3.addActionListener(e -> launchGameWithGrid(3));
        g5x5.addActionListener(e -> launchGameWithGrid(5));
        g7x7.addActionListener(e -> launchGameWithGrid(7));

        gridButtonContainer.add(g3x3);
        gridButtonContainer.add(g5x5);
        gridButtonContainer.add(g7x7);

        panel.add(Box.createVerticalStrut(150));
        panel.add(heading);
        panel.add(Box.createVerticalStrut(80));
        panel.add(gridButtonContainer);

        return panel;
    }

    private void launchGameWithGrid(int size) {
        // Yeh line ab gameLogic ke current setup se perfectly aligned hai
        gameLogic.initGame(size, gameLogic.isSinglePlayer());

        // Agar Single Player mode hai to grid choose hotay hi AI Difficulty poochni hai
        if (gameLogic.isSinglePlayer()) {
            showDifficultySelectionDialog();
        }

        buildDynamicGameplayGrid(size);
        cardLayout.show(mainPanel, "GAMEPLAY");
    }

    private void showDifficultySelectionDialog() {
        JDialog dialog = new JDialog(this, "Difficulty Selection", true);
        dialog.setUndecorated(true);
        dialog.setSize(420, 320);
        dialog.setLocationRelativeTo(this);

        JPanel content = createWoodDialogContentPanel(420, 320);

        JLabel promptLabel = createWoodDialogLabel("SELECT AI DIFFICULTY:", 22);

        JButton easyBtn = createWoodButton("Easy");
        JButton mediumBtn = createWoodButton("Medium");
        JButton hardBtn = createWoodButton("Hard");

        Dimension btnSize = new Dimension(220, 55);
        easyBtn.setMaximumSize(btnSize);
        mediumBtn.setMaximumSize(btnSize);
        hardBtn.setMaximumSize(btnSize);
        easyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        easyBtn.addActionListener(e -> {
            gameLogic.setDifficulty("EASY");
            dialog.dispose();
        });
        mediumBtn.addActionListener(e -> {
            gameLogic.setDifficulty("MEDIUM");
            dialog.dispose();
        });
        hardBtn.addActionListener(e -> {
            gameLogic.setDifficulty("HARD");
            dialog.dispose();
        });

        content.add(Box.createVerticalStrut(25));
        content.add(promptLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(easyBtn);
        content.add(Box.createVerticalStrut(15));
        content.add(mediumBtn);
        content.add(Box.createVerticalStrut(15));
        content.add(hardBtn);
        content.add(Box.createVerticalStrut(20));

        dialog.add(content);
        // Default rahega Medium agar dialog kisi tarah bina button click ke close ho jaye
        gameLogic.setDifficulty("MEDIUM");
        dialog.setVisible(true);
    }

    private JPanel createGameplayScreen() {
        JPanel panel = createWoodPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        statusLabel = new JLabel("Your Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 38));
        statusLabel.setForeground(WOOD_TEXT);
        panel.add(statusLabel, BorderLayout.NORTH);

        gameplayBoardPanel = new JPanel();
        gameplayBoardPanel.setOpaque(false);
        panel.add(gameplayBoardPanel, BorderLayout.CENTER);

        return panel;
    }

    private void buildDynamicGameplayGrid(int size) {
        gameplayBoardPanel.removeAll();
        gameplayBoardPanel.setLayout(new GridLayout(size, size, 12, 12));

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                final int row = r;
                final int col = c;
                
                JPanel cell = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.setColor(new Color(25, 12, 5));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                        g2.setColor(WOOD_MEDIUM);
                        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);

                        char marker = gameLogic.getBoard()[row][col];
                        if (marker == 'X') {
                            g2.setFont(new Font("SansSerif", Font.BOLD, getWidth() / 2 + 5));
                            g2.setColor(new Color(100, 0, 0));
                            g2.drawString("X", getWidth()/3 - 2, getHeight()/2 + getHeight()/5 + 2);
                            g2.setColor(Color.CYAN); 
                            g2.drawString("X", getWidth()/3, getHeight()/2 + getHeight()/5);
                        } else if (marker == 'O') {
                            g2.setFont(new Font("SansSerif", Font.BOLD, getWidth() / 2 + 5));
                            g2.setColor(DARK_3D);
                            g2.drawString("O", getWidth()/3 - 2, getHeight()/2 + getHeight()/5 + 2);
                            g2.setColor(new Color(240, 160, 40)); 
                            g2.drawString("O", getWidth()/3, getHeight()/2 + getHeight()/5);
                        }
                        g2.dispose();
                    }
                };

                cell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameLogic.makeMove(row, col)) {
                            gameplayBoardPanel.repaint();
                            updateStatusMessage();
                            if (gameLogic.isGameOver()) {
                                triggerCustomGameOverPopup();
                            }
                        }
                    }
                });
                gameplayBoardPanel.add(cell);
            }
        }
        updateStatusMessage();
        gameplayBoardPanel.revalidate();
        gameplayBoardPanel.repaint();
    }

    private void updateStatusMessage() {
        if (gameLogic.isGameOver()) {
            statusLabel.setText("Match Settled!");
        } else {
            statusLabel.setText(gameLogic.isPlayerXTurn() ? gameLogic.getPlayer1Name() + "'s Turn" : gameLogic.getPlayer2Name() + "'s Turn");
        }
    }

    private void triggerCustomGameOverPopup() {
        JDialog dialog = new JDialog(this, "Match Result", true);
        dialog.setUndecorated(true);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WOOD_DARK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(WOOD_MEDIUM);
                g2.fillRoundRect(8, 8, getWidth() - 16, getHeight() - 16, 20, 20);
            }
        };
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel resultLabel = new JLabel(gameLogic.getGameResult(), SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
        resultLabel.setForeground(WOOD_TEXT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton playAgainBtn = createWoodButton("PLAY AGAIN");
        playAgainBtn.setMaximumSize(new Dimension(220, 50));
        playAgainBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainBtn.addActionListener(e -> {
            dialog.dispose();
            cardLayout.show(mainPanel, "HOME");
        });

        content.add(Box.createVerticalStrut(40));
        content.add(resultLabel);
        content.add(Box.createVerticalStrut(35));
        content.add(playAgainBtn);

        dialog.add(content);
        dialog.setVisible(true);
    }
}
