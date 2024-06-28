import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SnakeAndLadder {
    // states
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int boardSize;
    private int status;
    private int playerInTurn;

    public SnakeAndLadder(int boardSize) {
        this.boardSize = boardSize;
        this.players = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.status = 0;
    }

    public void initiateGame() {
        // set the ladders
        int[][] ladders = {
                {2, 23},
                {8, 34},
                {20, 77},
                {32, 68},
                {41, 79},
                {74, 88},
                {82, 100},
                {85, 95}
        };
        addLadders(ladders);
        // set the snakes
        int[][] snakes = {
                {29, 9},
                {38, 15},
                {47, 5},
                {53, 33},
                {62, 37},
                {86, 54},
                {92, 70},
                {97, 25}
        };
        addSnakes(snakes);
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPlayerInTurn(int p) {
        this.playerInTurn = p;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void addSnake(Snake s) {
        this.snakes.add(s);
    }

    public void addSnakes(int[][] s) {
        for (int i = 0; i < s.length; i++) {
            Snake snake = new Snake(s[i][0], s[i][1]);
            this.snakes.add(snake);
        }
    }

    public void addLadder(Ladder l) {
        this.ladders.add(l);
    }

    public void addLadders(int[][] l) {
        for (int m = 0; m < l.length; m++) {
            Ladder ladder = new Ladder(l[m][0], l[m][1]);
            this.ladders.add(ladder);
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getStatus() {
        return status;
    }

    public int getPlayerInTurn() {
        return playerInTurn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public ArrayList<Ladder> getLadders() {
        return ladders;
    }

    public int getTurn() {
        // Determine whose turn it is
        return playerInTurn;
    }

    public void movePlayer(Player p, int x) {
        p.moveAround(x, this.boardSize);

        // Checking the ladder
        for (Ladder l : this.ladders) {
            if (p.getPosition() == l.getFromPosition()) {
                p.setPosition(l.getToPosition());
                System.out.println(p.getUserName() + " got ladder from " + l.getFromPosition() + " climb to " + l.getToPosition());
            }
        }

        // Checking the snake
        for (Snake s : this.snakes) {
            if (p.getPosition() == s.getFromPosition()) {
                p.setPosition(s.getToPosition());
                System.out.println(p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to " + s.getToPosition());
            }
        }

        System.out.println(p.getUserName() + " new position is " + p.getPosition());

        // Check if the player landed on a multiple of 20
        if (p.getPosition() % 20 == 0 && p.getPosition() != 100) {
            System.out.println(p.getUserName() + " landed on a multiple of 20! Rolling again...");
            int extraRoll = p.rollDice();
            System.out.println("Extra roll: " + extraRoll);
            movePlayer(p, extraRoll);  // Recursive call to move the player again
        }

        if (p.getPosition() == this.boardSize) {
            this.status = 2;
            System.out.println("The winner is: " + p.getUserName());
        }
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        String firstPlayer = JOptionPane.showInputDialog(null, "Enter name player 1", "Player 1");
        String secondPlayer = JOptionPane.showInputDialog(null, "Enter name player 2", "Player 2");

        Player p1 = new Player(firstPlayer);
        Player p2 = new Player(secondPlayer);

        initiateGame();

        addPlayer(p1);
        addPlayer(p2);

        // Initial turn
        setPlayerInTurn(0); // Player 1 starts first

        while (getStatus() != 2) {
            Player playerInTurn = getPlayers().get(getPlayerInTurn());
            System.out.println("---------------------------------");
            System.out.println("Player in turn is " + playerInTurn.getUserName());

            // Player in turn rolls dice
            System.out.println(playerInTurn.getUserName() + " it's your turn, please press enter to roll dice");
            String input = sc.nextLine();
            int x = 0;
            if (input.isEmpty()) {
                x = playerInTurn.rollDice();
            }

            System.out.println("Dice number: " + x);
            movePlayer(playerInTurn, x);

            // Switch player
            setPlayerInTurn((getPlayerInTurn() + 1) % 2); // Switch between 0 and 1
        }
    }
}
