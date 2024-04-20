import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650; // 50px for the text panel on top

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel buttonPanel = new JPanel(); // New panel for the button
    JPanel scorePanel = new JPanel(); // New panel for the scoreboard

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;
    int scoreX = 0; // Score for player X
    int scoreO = 0; // Score for player O

    JLabel scoreLabelX = new JLabel("Player X Score: " + scoreX);
    JLabel scoreLabelO = new JLabel("Player O Score: " + scoreO);

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth + 200, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.pink);
        textLabel.setForeground(Color.black);
        textLabel.setFont(new Font("Algerian", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new FlowLayout()); // Set layout for button panel
        frame.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to frame

        // Score Panel
        scorePanel.setLayout(new GridLayout(2, 1));
        scorePanel.setPreferredSize(new Dimension(200, boardHeight)); // Set the preferred size for scoreboard panel
        scorePanel.setBackground(Color.lightGray);

        // Initialize score labels
        scoreLabelX.setFont(new Font("Algerian", Font.BOLD, 20));
        scoreLabelO.setFont(new Font("Algerian", Font.BOLD, 20));

        scorePanel.add(scoreLabelX);
        scorePanel.add(scoreLabelO);
        frame.add(scorePanel, BorderLayout.EAST); // Add scoreboard panel to frame

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.pink);
                tile.setForeground(Color.black);
                tile.setFont(new Font("Algerian", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    void checkWinner() {
        // Horizontal
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                endGame(board[r][0].getText() + " is the winner!");
                return;
            }
        }

        // Vertical
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                endGame(board[0][c].getText() + " is the winner!");
                return;
            }
        }

        // Diagonal
        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            endGame(board[0][0].getText() + " is the winner!");
            return;
        }

        // Anti-diagonal
        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            endGame(board[0][2].getText() + " is the winner!");
            return;
        }

        // Draw
        if (turns == 9) {
            endGame("It's a draw!");
        }
    }

    void endGame(String message) {
        textLabel.setText(message);
        gameOver = true;

        if (message.contains("X is the winner")) {
            scoreX++;
            highlightWinningEntries("X");
        } else if (message.contains("O is the winner")) {
            scoreO++;
            highlightWinningEntries("O");
        }

        scoreLabelX.setText("Player X Score: " + scoreX);
        scoreLabelO.setText("Player O Score: " + scoreO);

        JButton newGameButton = new JButton("New Game");
        buttonPanel.add(newGameButton); // Add button to button panel
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        frame.revalidate(); // Refresh the frame to display the new button
    }

    void highlightWinningEntries(String player) {
        // Horizontal
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText()) &&
                board[r][0].getText().equals(player)) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setBackground(Color.green); // Highlight winning entries
                }
                return;
            }
        }

        // Vertical
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText()) &&
                board[0][c].getText().equals(player)) {
                for (int r = 0; r < 3; r++) {
                    board[r][c].setBackground(Color.green); // Highlight winning entries
                }
                return;
            }
        }

        // Diagonal
        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            board[0][0].getText().equals(player)) {
            for (int i = 0; i < 3; i++) {
                board[i][i].setBackground(Color.green); // Highlight winning entries
            }
            return;
        }

        // Anti-diagonal
        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            board[0][2].getText().equals(player)) {
            for (int i = 0; i < 3; i++) {
                board[i][2 - i].setBackground(Color.green); // Highlight winning entries
            }
            return;
        }
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.pink);
                board[r][c].setForeground(Color.black);
            }
        }
        currentPlayer = playerX;
        textLabel.setText("Tic-Tac-Toe");
        gameOver = false;
        turns = 0;

        buttonPanel.removeAll(); // Remove the button after resetting the game
        frame.revalidate(); // Refresh the frame to remove the button
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
