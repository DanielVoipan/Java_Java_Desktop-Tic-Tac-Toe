package tictactoe;

class Robot extends Thread {
    TicTacToe ticTacToe;

    public Robot(TicTacToe t) {
        ticTacToe = t;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                if (ticTacToe.isStartGame() && TicTacToe.currentPlayer.equals(TicTacToe.Player.ROBOT.name())) {
                    String positions = TicTacToe.getPlayerPosition();
                    String[] pos = positions.split(" ");
                    String buttonName = TicTacToe.getButtonName(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
                    Cells c = Board.map.get(buttonName);
                    Cells.Move(c, Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), 1);
                    boolean win = Cells.Win();
                    if (!win) {
                        TicTacToe.label.setText(TicTacToe.setLabel(TicTacToe.nextMove, TicTacToe.currentPlayer, 1));
                    }
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
