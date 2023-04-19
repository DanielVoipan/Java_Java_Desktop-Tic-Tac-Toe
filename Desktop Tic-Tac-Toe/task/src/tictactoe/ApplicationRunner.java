package tictactoe;

public class ApplicationRunner {
    public static void main(String[] args) {
        TicTacToe t = new TicTacToe();
        Robot r = new Robot(t);
        r.start();
    }
}
