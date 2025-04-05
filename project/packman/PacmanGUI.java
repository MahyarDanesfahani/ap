package project.packman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

    public class PacmanGUI extends JFrame implements KeyListener {

        Point pacmanPoint = new Point();
        final int width = 300, height = 300, boxSize = 5;
        static int direction = 1;
        static int score = 0;
        final static int MAX_SCORE = 5;
        final static long MAX_TIME = 60 * 1000;
        long startTime;
        boolean gameOver = false;
        final int LEFT = 1, RIGHT = 2, TOP = 3, BOTTOM = 4;
        Point dotPoint = new Point();

        public PacmanGUI() {
            addKeyListener(this);
            pacmanPoint.setLocation((width / boxSize) / 2, (height / boxSize) / 2);
            getNewDotPointLocation();
            setSize(width, height);
            startTime = System.currentTimeMillis();
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g.clearRect(0, 0, width, height);
            logic();
            drawPacman(g2D);
            drawDotPoint(g2D);
            drawScore(g2D);
            setVisible(true);
        }

        private void drawPacman(Graphics2D g2d) {
            g2d.setColor(Color.BLUE);
            g2d.fillRect(pacmanPoint.x * boxSize, pacmanPoint.y * boxSize, boxSize, boxSize);
        }

        private void drawDotPoint(Graphics2D g2d) {
            g2d.setColor(Color.RED);
            g2d.fillRect(dotPoint.x * boxSize, dotPoint.y * boxSize, boxSize, boxSize);
        }

        private void drawScore(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            long currentTime = System.currentTimeMillis();
            long timeElapsedSec = (currentTime - startTime) / 1000;
            String s = "Score : " + score + " | Time : " + timeElapsedSec + " s ";
            g2d.drawString(s, 25, 50);
        }

        private void logic() {
            if (gameOver) return;
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;

            if (elapsedTime > MAX_TIME) {
                JOptionPane.showMessageDialog(this, "Game time is up . \nTime allowed " + (MAX_TIME / 1000) + "Seconds . ");
                System.exit(0);
            }
            if (score >= MAX_SCORE) {
                JOptionPane.showMessageDialog(this, "The game is over and you have reached the final score . " + score);
                gameOver = true;
                return;
            }
            if (dotPoint.x == pacmanPoint.x && dotPoint.y == pacmanPoint.y) {
                score++;
                System.out.println("Score increased! Current score : " + score);
                getNewDotPointLocation();
            }

            movePacman();
        }

        private void movePacman() {
            int xMovement = 1;
            int yMovement = 0;
            switch (direction) {
                case LEFT:
                    xMovement = -1;
                    yMovement = 0;
                    break;
                case RIGHT:
                    xMovement = 1;
                    yMovement = 0;
                    break;
                case TOP:
                    xMovement = 0;
                    yMovement = -1;
                    break;
                case BOTTOM:
                    xMovement = 0;
                    yMovement = 1;
                    break;
                default:
                    xMovement = yMovement = 0;
                    break;
            }
            pacmanPoint.setLocation(pacmanPoint.x + xMovement, pacmanPoint.y + yMovement);
            handleCrossBorder();
        }

        private void getNewDotPointLocation() {
            Random rand = new Random();
            int delta = boxSize * 2;
            dotPoint.setLocation(rand.nextInt(width / boxSize - 2 * delta) + delta, rand.nextInt(height / boxSize - 2 * delta) + delta);
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP)
                direction = TOP;
            else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                direction = BOTTOM;
            else if (e.getKeyCode() == KeyEvent.VK_LEFT)
                direction = LEFT;
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                direction = RIGHT;
            else if (e.getKeyCode() == KeyEvent.VK_P)
                direction = 0;
            else if (e.getKeyCode() == KeyEvent.VK_Q)
                System.exit(0);
            else
                direction = -1;
            System.out.println("direction:" + direction + "    <- e.getKeyCode()=" + e.getKeyCode());

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        private void resetGame() {
            pacmanPoint.setLocation((width / boxSize) / 2, (height / boxSize) / 2);

            score = 0;

            getNewDotPointLocation();

            direction = RIGHT;

            repaint();
        }

        private void handleCrossBorder() {
            int maxX = width / boxSize;
            int maxY = height / boxSize;

            if (pacmanPoint.x < 0 || pacmanPoint.x >= maxX || pacmanPoint.y < 0 || pacmanPoint.y >= maxY) {
                System.out.println("Out of bounds! Resetting game...");
                resetGame();
            }
        }

        public static void main(String[] args) {
            PacmanGUI frame = new PacmanGUI();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
