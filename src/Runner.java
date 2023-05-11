import helpers.Artist;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

public class Runner {

    public JFrame frame;
    public JPanel panel;
    public Image X, O;
    public Graphics panelG, frameG;
    public Texture[] pieces;
    public int index;
    public int[][] board;

    public static void main(String[] args) throws InterruptedException {
        new Runner();
    }

    public Runner() throws InterruptedException {
        board = new int[][]{
                {-10, -10, -10},
                {-10, -10, -10},
                {-10, -10, -10}};

        //this.X = Toolkit.getDefaultToolkit().getImage("src/res/X.png");
        //this.O = Toolkit.getDefaultToolkit().getImage("src/res/O.png");
        this.pieces = new Texture[]{null, QuickLoad("X"),  QuickLoad("O")};
        this.index = 1;




        while (true){
            updater(panelG);
        }
    }

    public void setupWindow(){
        frame = new JFrame("Tic Tac Toe"){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };
        panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                updater(g);
            }
        };

        Dimension expectedDimension = new Dimension(300, 300);

        panel.setPreferredSize(expectedDimension);
        panel.setMaximumSize(expectedDimension);
        panel.setMinimumSize(expectedDimension);

        //panel.setBackground(Color.RED); // for debug only

        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        frame.add(box);

        panel.setDoubleBuffered(true);
        frame.setSize(1000, 1000);
        frame.setIconImage(X);
        this.panelG = panel.getGraphics();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Working!");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {



            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        frame.setVisible(true);
    }

    public void drawBoard(Graphics g){
        g.drawLine(panel.getWidth() / 3, 0, panel.getWidth() / 3, panel.getHeight());
        g.drawLine(2 * panel.getWidth() / 3, 0, 2 * panel.getWidth() / 3, panel.getHeight());
        g.drawLine(0, panel.getWidth() / 3, panel.getHeight(), panel.getWidth() / 3);
        g.drawLine(0, 2 * panel.getWidth() / 3, panel.getHeight(), 2 * panel.getWidth() / 3);
    }

    public void drawBoardPieces(Graphics g) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] != -10){
                    DrawQuadTex(pieces[board[i][j]], (j) * (panel.getWidth() / 3) + (panel.getWidth() / 3) / 5, (i) * (panel.getWidth() / 3) + (panel.getWidth() / 3) / 5, 64, 64);
                }
            }
        }


    }

    public boolean[] checkWin(){            // returns array of 2 booleans, first is if win, 2nd is type (true == x, false == o)
        int lineTotal = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                lineTotal += board[i][j];
            }
            //System.out.println(lineTotal);
            if(lineTotal == 6){
                System.out.println("O wins across");
                return new boolean[]{true, false};
            }else if(lineTotal == 3) {
                System.out.println("X wins across");
                return new boolean[]{true, true};
            }
            lineTotal = 0;

        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                lineTotal += board[j][i];
            }
            //System.out.println(lineTotal);
            if(lineTotal == 6){
                System.out.println("O wins down");
                return new boolean[]{true, false};
            }else if(lineTotal == 3) {
                System.out.println("X wins down");
                return new boolean[]{true, true};
            }
            lineTotal = 0;

        }

        for (int i = 0; i < board.length; i++) {
            lineTotal += board[i][i];
        }
        //System.out.println(lineTotal);
        if(lineTotal == 6){
            System.out.println("O wins l2r");
            return new boolean[]{true, false};
        }else if(lineTotal == 3) {
            System.out.println("X wins l2r");
            return new boolean[]{true, true};
        }
        lineTotal = 0;

        for (int i = board.length - 1; i >= 0; i--) {
            lineTotal += board[i][Math.abs(i-2)];

        }
        //System.out.println(lineTotal);
        if(lineTotal == 6){
            System.out.println("O wins r2l");
            return new boolean[]{true, false};
        }else if(lineTotal == 3) {
            System.out.println("X wins r2l");
            return new boolean[]{true, true};
        }

        return new boolean[]{false, false};
    }

    public boolean isDraw(){
        int amountOfEmptyPieces = 0;
        for (int[] row: board) {
            for (int col: row) {
                if(col == -10){
                    amountOfEmptyPieces++;
                }
            }
        }
        if(amountOfEmptyPieces == 0){
            return true;
        }

        return false;
    }

    public void resetGame(){
        board = new int[][]{
                {-10, -10, -10},
                {-10, -10, -10},
                {-10, -10, -10}};
    }

    public void drawTurn(){
        //Drawinator.DrawQuadIMG(50, 50, turn[index], frame.getGraphics());
        Artist.DrawQuadTex(QuickLoad("X"), 50, 50, 64, 64);
    }

    public void updater(Graphics g){
        int arrX = 0;
        int arrY = 0;

        if(Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);

            if (mouseClicked) {
                if(Mouse.getX() > 0 && Mouse.getX() < panel.getWidth() / 3){
                    arrX = 0;
                }else if(Mouse.getX() > panel.getWidth() / 3 && Mouse.getX() < 2 * panel.getWidth() / 3){
                    arrX = 1;
                }else if(Mouse.getX() > 2 * panel.getWidth() / 3 && Mouse.getX() < panel.getWidth()){
                    arrX = 2;
                }
                if(Mouse.getY() > 0 && Mouse.getY() < panel.getWidth() / 3){
                    arrY = 0;
                }else if(Mouse.getY() > panel.getWidth() / 3 && Mouse.getY() < 2 * panel.getWidth() / 3){
                    arrY = 1;
                }else if(Mouse.getY() > 2 * panel.getWidth() / 3 && Mouse.getY() < panel.getWidth()){
                    arrY = 2;
                }

                if(board[arrY][arrX] == -10) {
                    board[arrY][arrX] = index;
                    if(index == 2){
                        index = 1;
                    }else {
                        index = 2;
                    }
                    frame.repaint();
                    drawTurn();
                }

                boolean[] win = checkWin();
                if(win[0]){
                    if(win[1]){
                        JOptionPane.showMessageDialog(null, "X wins", "Winner", JOptionPane.WARNING_MESSAGE);
                    }else if(!win[1]){
                        JOptionPane.showMessageDialog(null, "O wins", "Winner", JOptionPane.WARNING_MESSAGE);
                    }
                    resetGame();
                }

                if(isDraw()){
                    JOptionPane.showMessageDialog(null, "No Winners :(", "Draw", JOptionPane.WARNING_MESSAGE);
                    resetGame();
                }

            }
        }


        drawTurn();
        drawBoard(g);
        drawBoardPieces(g);


    }


}
