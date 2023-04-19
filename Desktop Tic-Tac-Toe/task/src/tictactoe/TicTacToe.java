package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TicTacToe extends JFrame {
    enum Player {
        ROBOT("Robot"),
        HUMAN("Human");

        final String name;

        Player(String name) {
            this.name = name;
        }

        static String getName(String s) {
            String value = null;
            for ( var p : Player.values()) {
                if (s.equalsIgnoreCase(String.valueOf(p))) {
                    value = p.name;
                }
            }
            return value;
        }
    }

    static JLabel label;
    static JButton buttonStartReset;
    static JButton buttonPlayer1;
    static JButton buttonPlayer2;

    static boolean startResetStatus;

    static boolean endGame;
    static boolean startGame;

    static boolean firstMove;

    static String lastMove;
    static String nextMove;

    static char[][] field;

    static String player1;
    static String player2;

    static String currentPlayer;

    static String lastPlayer;

    // create matrices
    static {
        field = new char[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(field[i], '_');
        }
        startResetStatus = false;
        endGame = false;
        startGame = false;
        firstMove = true;
        player1 = Player.HUMAN.name();
        player2 = Player.HUMAN.name();
        currentPlayer = player1;
        nextMove = "X";
    }

    public TicTacToe() {
        // add board to the game
        add(new Board(), BorderLayout.CENTER);
        add(new JPanel(), BorderLayout.EAST);
        add(new JPanel(), BorderLayout.WEST);
        // panel for buttons toolbar
        JPanel northPanel = new JPanel();

        buttonPlayer1 = new Cells("ButtonPlayer1", Player.HUMAN.name);
        buttonPlayer2 = new Cells("ButtonPlayer2", Player.HUMAN.name);
        buttonStartReset = new Cells("ButtonStartReset", "Start");
        northPanel.setLayout(new GridBagLayout());
        add(northPanel, BorderLayout.NORTH);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.insets = new Insets(4, 10, 6, 10);

        northPanel.add(buttonPlayer1, c);
        northPanel.add(buttonStartReset, c);
        northPanel.add(buttonPlayer2, c);

        // panel that contains the button and label
        JPanel GenPanel = new JPanel();
        GenPanel.setLayout(new BorderLayout());
        add(GenPanel,BorderLayout.SOUTH);

        // label to show game status
        label = new JLabel(setLabel(null, null, 0));
        label.setName("LabelStatus");
        JPanel labelPanel = new JPanel();
        GenPanel.add(labelPanel, BorderLayout.WEST);
        labelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        labelPanel.add(label);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuGame.setName("MenuGame");
        menuGame.setMnemonic(KeyEvent.VK_G);
        menuGame.setText("Game");

        JMenuItem menuHumanHuman = new JMenuItem("HumanHuman");
        menuHumanHuman.setName("MenuHumanHuman");
        menuHumanHuman.setText("Human vs Human");
        JMenuItem menuHumanRobot = new JMenuItem("MenuHumanRobot");
        menuHumanRobot.setText("Human vs Robot");
        menuHumanRobot.setName("MenuHumanRobot");
        JMenuItem menuRobotHuman = new JMenuItem("MenuRobotHuman");
        menuRobotHuman.setText("Robot vs Human");
        menuRobotHuman.setName("MenuRobotHuman");
        JMenuItem menuRobotRobot = new JMenuItem("MenuRobotRobot");
        menuRobotRobot.setText("Robot vs Robot");
        menuRobotRobot.setName("MenuRobotRobot");
        JMenuItem menuExit = new JMenuItem("MenuExit");
        menuExit.setName("MenuExit");
        menuExit.setText("Exit");

        menuGame.add(menuHumanHuman);
        menuGame.add(menuHumanRobot);
        menuGame.add(menuRobotHuman);
        menuGame.add(menuRobotRobot);
        menuGame.addSeparator();
        menuGame.add(menuExit);
        setJMenuBar(menuBar);
        menuBar.add(menuGame);

// action listeners for JMenu Items
        menuExit.addActionListener(e -> System.exit(0));
        menuHumanHuman.addActionListener(e -> {
            player1 = Player.HUMAN.name();
            buttonPlayer1.setText(Player.HUMAN.name);
            player2 = Player.HUMAN.name();
            buttonPlayer2.setText(Player.HUMAN.name);
            resetGame();
            startGame();
        });
        menuHumanRobot.addActionListener(e -> {
            player1 = Player.HUMAN.name();
            buttonPlayer1.setText(Player.HUMAN.name);
            player2 = Player.ROBOT.name();
            buttonPlayer2.setText(Player.ROBOT.name);
            resetGame();
            startGame();
        });
        menuRobotHuman.addActionListener(e -> {
            player1 = Player.ROBOT.name();
            buttonPlayer1.setText(Player.ROBOT.name);
            player2 = Player.HUMAN.name();
            buttonPlayer2.setText(Player.HUMAN.name);
            resetGame();
            startGame();
        });
        menuRobotRobot.addActionListener(e -> {
            player1 = Player.ROBOT.name();
            buttonPlayer1.setText(Player.ROBOT.name);
            player2 = Player.ROBOT.name();
            buttonPlayer2.setText(Player.ROBOT.name);
            resetGame();
            startGame();
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
        setVisible(true);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setLayout(new BorderLayout());
    }

    // put sign in the matrices
    static void drawSign(char c, int line, int column) {
        field[line][column] = c;
    }

    // get matrices coordinates based on button name
    static int[] getCoordinates(String button) {
        switch (button) {
            case "ButtonA1" -> {
                return new int[]{0, 0};
            }
            case "ButtonA2" -> {
                return new int[]{0, 1};
            }
            case "ButtonA3" -> {
                return new int[]{0, 2};
            }
            case "ButtonB1" -> {
                return new int[]{1, 0};
            }
            case "ButtonB2" -> {
                return new int[]{1, 1};
            }
            case "ButtonB3" -> {
                return new int[]{1, 2};
            }
            case "ButtonC1" -> {
                return new int[]{2, 0};
            }
            case "ButtonC2" -> {
                return new int[]{2, 1};
            }
            case "ButtonC3" -> {
                return new int[]{2, 2};
            }
        }
        return null;
    }

    // get button name based on coordinates
    static String getButtonName(int pos1, int pos2) {
        if (pos1 == 0 && pos2 == 0) {
            return "ButtonA1";
        } else if (pos1 == 0 && pos2 == 1) {
            return "ButtonA2";
        } else if (pos1 == 0 && pos2 == 2) {
            return "ButtonA3";
        } else if (pos1 == 1 && pos2 == 0) {
            return "ButtonB1";
        } else if (pos1 == 1 && pos2 == 1) {
            return "ButtonB2";
        } else if (pos1 == 1 && pos2 == 2) {
            return "ButtonB3";
        } else if (pos1 == 2 && pos2 == 0) {
            return "ButtonC1";
        } else if (pos1 == 2 && pos2 == 1) {
            return "ButtonC2";
        } else if (pos1 == 2 && pos2 == 2) {
            return "ButtonC3";
        }
        return null;
    }

    // check status of game, draw, not finished, win;
    static int checkStatusGame(char sign) {
        boolean win = false;
        int noMoreMoves = 0;
        int total = 0;

        // parse the field lines
        for (char[] chars : field) {
            int nrLines = 0;
            int other = 0;
            for (int j = 0; j < field.length; j++) {
                if (chars[j] == sign) {
                    nrLines++;
                } else if (chars[j] != '_') {
                    other++;
                }
                if (chars[j] != '_') {
                    total++;
                }
            }
            if (nrLines == 3) {
                win = true;
            } else if (nrLines > 0 && other > 0) {
                noMoreMoves++;
            }
        }

        // parse the columns
        for (int i = 0; i < 3; i++) {
            int nrLines = 0;
            for (char[] chars : field) {
                if (chars[i] == sign) {
                    nrLines++;
                }
                if (nrLines == 3) {
                    win = true;
                }
            }
        }
        // parse the diagonals
        if (field[0][0] == sign && field[1][1] == sign && field[2][2] == sign) {
            win = true;
        } else if (field[0][2] == sign && field[1][1] == sign && field[2][0] == sign) {
            win = true;
        } else if ((field[0][0] != sign || field[1][1] != sign || field[2][2] != sign) && (field[0][0] != '_' || field[1][1] != '_' || field[2][2] != '_'))  {
            noMoreMoves++;
        }
        if ((field[0][2] != sign || field[1][1] != sign || field[2][0] != sign) && (field[0][2] != '_' || field[1][1] != '_' || field[2][0] != '_'))  {
            noMoreMoves++;
        }
        if (win) {
            return 2;
        } else if (noMoreMoves == 8) {
            return 3;
        } else if (total == 9) {
            return 3;
        } else {
            return 4;
        }
    }

    // start game
    static void startGame() {
        TicTacToe.label.setText(TicTacToe.setLabel(TicTacToe.lastMove, TicTacToe.currentPlayer, 1));
        startGame = true;
        buttonStartReset.setText("Reset");
        startResetStatus = true;
        buttonPlayer1.setEnabled(false);
        buttonPlayer2.setEnabled(false);
        for (var v : Board.map.entrySet()) {
            Cells c = v.getValue();
            c.setEnabled(true);
        }
    }

    static void resetGame() {
        for (int i = 0; i < 3; i++) {
            Arrays.fill(TicTacToe.field[i], '_');
        }
        for (var v : Board.map.entrySet()) {
            Cells c = v.getValue();
            c.setText(" ");
        }
        endGame = false;
        firstMove = true;
        label.setText(setLabel(null, null, 0));
        for (var v : Board.map.entrySet()) {
            Cells c = v.getValue();
            c.setBackground(Color.yellow);
            c.setEnabled(false);
        }
        buttonStartReset.setText("Start");
        startResetStatus = false;
        startGame = false;
        currentPlayer = player1;
        lastMove = "X";
        buttonPlayer1.setEnabled(true);
        buttonPlayer2.setEnabled(true);
    }

    // get player positions
    static String getPlayerPosition() {
        ArrayList<String> positions = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == '_') {
                    positions.add(i + " " + j);
                }
            }
        }
        Random nr = new Random();
        return positions.get(nr.nextInt(positions.size()));
    }

    static String setLabel(String currentMove, String player, int type) {
        if (Objects.equals(currentMove, null)) {
            currentMove = "X";
        }
        return switch (type) {
            case 0 -> "Game is not started";
            case 1 -> "The turn of " + Player.getName(player) + " Player (" + currentMove + ")";
            case 2 -> "The " + Player.getName(player) + " Player (" + currentMove + ") wins";
            case 3 -> "Draw";
            default -> null;
        };
    }

    public boolean isStartGame() {
        return startGame;
    }
}