/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rule30;

/**
 *
 * @author mary'sRose
 */
import java.awt.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class Generator extends JPanel {

    byte[][] cells;
    int rule = 30;
    String inp = null;

    public Generator() {
        Scanner sc = new Scanner(System.in);
        boolean invalid = true;

        while (invalid) {
            System.out.print("Enter valid initial state: ");
            inp = sc.nextLine();
            Pattern regEx = Pattern.compile("[01]+$");
            Matcher matcher = regEx.matcher(inp);
            if (matcher.find()) {
                invalid = false;
            }

            Dimension dim = new Dimension(900, 600);
            setPreferredSize(dim);
            setBackground(Color.white);

            cells = new byte[dim.height][dim.width];
            for (int i = 0; i < dim.width; i++) {
                cells[0][i] = 0;
            }
            for (int i = ((dim.width / 2) - (inp.length() / 2)) + 1, j = 0; j < inp.length(); i++, j++) {
                if (inp.toCharArray()[j] == '1') {
                    cells[0][i] = 1;
                }
            }
        }
    }

    private byte rule(int lhs, int mid, int rhs) {
        int idx = (lhs << 2 | mid << 1 | rhs);
        return (byte) (rule >> idx & 1);
    }

    void draw(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        for (int r = 0; r < cells.length - 1; r++) {
            for (int c = 1; c < cells[r].length - 1; c++) {
                byte lhs = cells[r][c - 1];
                byte mid = cells[r][c];
                byte rhs = cells[r][c + 1];
                cells[r + 1][c] = rule(lhs, mid, rhs); //next gen
                if (cells[r][c] == 1) {
                    g.fillRect(c, r, 1, 1);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D gp = (Graphics2D) gg;
//        gp.scale(3, 3);
        draw(gp);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Rule 30");
            f.setResizable(false);
            f.add(new Generator(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
