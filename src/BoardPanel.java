import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private Color[] playerColors;

    public BoardPanel(ArrayList<Player> players, ArrayList<Snake> snakes, ArrayList<Ladder> ladders, Color[] playerColors) {
        this.players = players;
        this.snakes = snakes;
        this.ladders = ladders;
        this.playerColors = playerColors;

        setPreferredSize(new Dimension(600, 600));
    }

    public void updatePlayers(ArrayList<Player> players) {
        this.players = players;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Menggambar board dan angka
        drawBoard(g);

        // Menggambar pemain
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int position = player.getPosition();

            int row = 9 - (position - 1) / 10;
            int col;
            if (row % 2 == 0) {
                col = 9 - (position - 1) % 10;
            } else {
                col = (position - 1) % 10;
            }

            int x = col * 60 + 10;
            int y = row * 60 + 10;

            // Menggambar pemain dengan warna yang sesuai
            g.setColor(playerColors[i]);
            g.fillOval(x + 5, y + 5, 50, 50);

            // Menggambar teks angka pada posisi pemain
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String playerNumber = Integer.toString(position);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(playerNumber);
            int textHeight = fm.getHeight();
            g.drawString(playerNumber, x + 25 - textWidth / 2, y + 35 + textHeight / 2);
        }

        // Menggambar tangga dan ular
        drawSnakesAndLadders(g);
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        boolean isBlack = true;
        int number = 1;
        for (int row = 9; row >= 0; row--) {
            for (int col = 0; col < 10; col++) {
                int drawcol = (row % 2 == 0) ? 9 - col : col;

                if (isBlack) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(drawcol * 60, row * 60, 60, 60);
                isBlack = !isBlack;

                // Menggambar angka
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                String numberString = Integer.toString(number);
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(numberString);
                int textHeight = fm.getHeight();
                g.drawString(numberString, drawcol * 60 + 30 - textWidth / 2, row * 60 + 30 + textHeight / 2);

                number++;
            }

            isBlack = !isBlack; // Tambahkan ini untuk mengubah warna awal setiap baris
        }
    }

    private void drawSnakesAndLadders(Graphics g) {
        // Menggambar ular
        g.setColor(Color.RED);
        for (Snake snake : snakes) {
            int fromPosition = snake.getFromPosition();
            int toPosition = snake.getToPosition();

            int fromRow = 9 - (fromPosition - 1) / 10;
            int fromCol;
            if (fromRow % 2 == 0) {
                fromCol = 9 - (fromPosition - 1) % 10;
            } else {
                fromCol = (fromPosition - 1) % 10;
            }

            int toRow = 9 - (toPosition - 1) / 10;
            int toCol;
            if (toRow % 2 == 0) {
                toCol = 9 - (toPosition - 1) % 10;
            } else {
                toCol = (toPosition - 1) % 10;
            }

            int fromX = fromCol * 60 + 30;
            int fromY = fromRow * 60 + 30;
            int toX = toCol * 60 + 30;
            int toY = toRow * 60 + 30;

            drawArrow(g, fromX, fromY, toX, toY, Color.RED);
        }

        // Menggambar tangga
        g.setColor(Color.GREEN);
        for (Ladder ladder : ladders) {
            int fromPosition = ladder.getFromPosition();
            int toPosition = ladder.getToPosition();

            int fromRow = 9 - (fromPosition - 1) / 10;
            int fromCol;
            if (fromRow % 2 == 0) {
                fromCol = 9 - (fromPosition - 1) % 10;
            } else {
                fromCol = (fromPosition - 1) % 10;
            }

            int toRow = 9 - (toPosition - 1) / 10;
            int toCol;
            if (toRow % 2 == 0) {
                toCol = 9 - (toPosition - 1) % 10;
            } else {
                toCol = (toPosition - 1) % 10;
            }

            int fromX = fromCol * 60 + 30;
            int fromY = fromRow * 60 + 30;
            int toX = toCol * 60 + 30;
            int toY = toRow * 60 + 30;

            drawArrow(g, fromX, fromY, toX, toY, Color.GREEN);
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2, Color color) {
        // Menggambar garis panah
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);

        // Menggambar kepala panah
        int dx = x2 - x1;
        int dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = 10; // Panjang kepala panah
        int arrowWidth = 8; // Lebar kepala panah

        // Koordinat titik-titik panah
        int x3 = (int) (x2 - len * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - len * Math.sin(angle - Math.PI / 6));
        int x4 = (int) (x2 - len * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - len * Math.sin(angle + Math.PI / 6));

        // Menggambar kepala panah
        g.fillPolygon(new int[]{x2, x3, x4}, new int[]{y2, y3, y4}, 3);
    }
}