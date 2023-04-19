package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Board extends JPanel {

    static final Map<String, Cells> map = new HashMap<>();

    final static int LINE_NR = 3;
    final static int COL_NR = 3;

    public Board() {
        int counter = 3;
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < LINE_NR; i++) {
            for (int j = 0; j < COL_NR; j++) {
                if (j == 0) {
                    Cells c = new Cells("ButtonA"+ counter, null);
                    c.setEnabled(false);
                    add(c);
                    map.put("ButtonA"+ counter, c);
                } else if (j == 1) {
                    Cells c = new Cells("ButtonB"+ counter, null);
                    c.setEnabled(false);
                    add(c);
                    map.put("ButtonB"+ counter, c);
                } else {
                    Cells c = new Cells("ButtonC"+ counter, null);
                    c.setEnabled(false);
                    add(c);
                    map.put("ButtonC"+ counter, c);
                }
            }
            counter--;
        }
    }
}
