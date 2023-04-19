package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class Cells extends JButton implements ActionListener {

    public Cells(String name, String realName) {
        setName(name);
        addActionListener(this);
        setActionCommand(name);
        if (name.matches("Button[ABC][0-9]")) {
            setFocusPainted(false);
            setBackground(Color.yellow);
            Font font = new Font("Curier", Font.BOLD, 42);
            setFont(font);
            setText(" ");
        } else {
            setText(realName);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String buttonName = actionEvent.getActionCommand();
        // start/reset button
        switch (buttonName) {
            case "ButtonStartReset" -> {
                if (TicTacToe.startResetStatus) {
                    TicTacToe.resetGame();
                    return;
                } else {
                    TicTacToe.startGame();
                }
            }
            case "ButtonPlayer1" -> {
                if (!TicTacToe.startGame) {
                    if (TicTacToe.player1.equals(TicTacToe.Player.HUMAN.name())) {
                        TicTacToe.player1 = TicTacToe.Player.ROBOT.name();
                        TicTacToe.buttonPlayer1.setText(TicTacToe.Player.ROBOT.name);
                        TicTacToe.currentPlayer = TicTacToe.player1;
                    } else {
                        TicTacToe.player1 = TicTacToe.Player.HUMAN.name();
                        TicTacToe.buttonPlayer1.setText(TicTacToe.Player.HUMAN.name);
                        TicTacToe.currentPlayer = TicTacToe.player1;
                    }
                    return;
                }
            }
            case "ButtonPlayer2" -> {
                if (!TicTacToe.startGame) {
                    if (TicTacToe.player2.equals(TicTacToe.Player.HUMAN.name())) {
                        TicTacToe.player2 = TicTacToe.Player.ROBOT.name();
                        TicTacToe.buttonPlayer2.setText(TicTacToe.Player.ROBOT.name);
                    } else {
                        TicTacToe.player2 = TicTacToe.Player.HUMAN.name();
                        TicTacToe.buttonPlayer2.setText(TicTacToe.Player.HUMAN.name);
                    }
                    return;
                }
            }
        }
        if (!TicTacToe.startGame) {
            return;
        }
        if (TicTacToe.currentPlayer.equals(TicTacToe.Player.ROBOT.name())) {
            return;
        }
        int[] coordinates = TicTacToe.getCoordinates(buttonName);
        if (!getText().equals(" ")) {
            return;
        }
        Move(this, coordinates[0], coordinates[1], 0);
        boolean check = Win();
        if (!check) {
            TicTacToe.label.setText(TicTacToe.setLabel(TicTacToe.nextMove, TicTacToe.currentPlayer, 1));
        }
    }

    static boolean Win() {
        int win = TicTacToe.checkStatusGame(TicTacToe.lastMove.charAt(0));
        if (win == 2 || win == 3) {
            TicTacToe.label.setText(TicTacToe.setLabel(TicTacToe.lastMove, TicTacToe.currentPlayer, win));
            TicTacToe.endGame = true;
            TicTacToe.startGame = false;
            for (var v : Board.map.entrySet()) {
                Cells c = v.getValue();
                c.setBackground(Color.GRAY);
                c.setEnabled(false);
            }
            return true;
        }
        return false;
    }

    static void Move(Cells cel, int pos1, int pos2, int type) {
        if (TicTacToe.firstMove) {
            TicTacToe.lastMove = "X";
            TicTacToe.nextMove = "O";
            TicTacToe.firstMove = false;
            TicTacToe.currentPlayer = TicTacToe.player2;
            TicTacToe.lastPlayer = TicTacToe.player1;
        } else {
            if (TicTacToe.lastMove.equals("X")) {
                TicTacToe.lastMove = "O";
                TicTacToe.currentPlayer = TicTacToe.player1;
                TicTacToe.lastPlayer = TicTacToe.player2;

                TicTacToe.nextMove = "X";
            } else {
                TicTacToe.lastMove = "X";
                TicTacToe.nextMove = "O";
                TicTacToe.currentPlayer = TicTacToe.player2;
                TicTacToe.lastPlayer = TicTacToe.player1;

            }
        }
        cel.setText(TicTacToe.lastMove);
        TicTacToe.drawSign(TicTacToe.lastMove.charAt(0), pos1, pos2);
    }
}
