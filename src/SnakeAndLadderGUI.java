import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SnakeAndLadderGUI extends JFrame {
    private BoardPanel boardPanel;
    private SnakeAndLadder game;
    private JLabel playerTurnLabel;
    private JLabel diceResultLabel;
    private JButton rollDiceButton;

    private Color[] playerColors = {Color.BLUE, Color.RED}; // Warna pemain 1 dan pemain 2

    public SnakeAndLadderGUI() {
        game = new SnakeAndLadder(100);
        game.initiateGame();

        String firstPlayer = JOptionPane.showInputDialog(this, "Enter name player 1", "Player 1");
        String secondPlayer = JOptionPane.showInputDialog(this, "Enter name player 2", "Player 2");

        Player p1 = new Player(firstPlayer);
        Player p2 = new Player(secondPlayer);

        game.addPlayer(p1);
        game.addPlayer(p2);

        boardPanel = new BoardPanel(game.getPlayers(), game.getSnakes(), game.getLadders(), playerColors);
        playerTurnLabel = new JLabel("Player 1's Turn");
        diceResultLabel = new JLabel("Dice Result: ");
        rollDiceButton = new JButton("Roll Dice");

        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int playerTurn = game.getPlayerInTurn();
                Player player = game.getPlayers().get(playerTurn);
                int roll = player.rollDice();
                diceResultLabel.setText("Dice Result: " + roll);
                game.movePlayer(player, roll);
                boardPanel.updatePlayers(game.getPlayers());

                // If game is not won yet, check for extra roll condition
                while (game.getStatus() != 2 && player.getPosition() % 20 == 0 && player.getPosition() != 100) {
                    int extraRoll = player.rollDice();
                    diceResultLabel.setText("Extra Roll Result: " + extraRoll);
                    game.movePlayer(player, extraRoll);
                    boardPanel.updatePlayers(game.getPlayers());
                }

                if (game.getStatus() == 2) {
                    playerTurnLabel.setText("The winner is: " + player.getUserName());
                    rollDiceButton.setEnabled(false);
                } else {
                    // Switch player turn
                    game.setPlayerInTurn((playerTurn + 1) % game.getPlayers().size());
                    playerTurnLabel.setText("Player " + (game.getPlayerInTurn() + 1) + "'s Turn");
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(playerTurnLabel);
        controlPanel.add(diceResultLabel);
        controlPanel.add(rollDiceButton);

        this.setLayout(new BorderLayout());
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);

        this.setTitle("Snake and Ladder Game");
        this.setSize(640, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SnakeAndLadderGUI();
            }
        });
    }
}