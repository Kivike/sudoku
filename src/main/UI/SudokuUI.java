package main.UI;

import main.SudokuCell;
import main.SudokuFactory;
import main.Sudoku;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuUI extends JFrame implements ActionListener {
    private static SudokuUI instance;

    private static final long serialVersionUID = 002;
    private static final double versionNumber = 0.2;
    private final int WINDOW_WIDTH = 512;
    private final int WINDOW_HEIGHT = 682;

    private CellPanel[][] cells;

    Sudoku sudoku;

    // Default difficulty for first game loaded
    private Sudoku.Difficulty difficulty = Sudoku.Difficulty.EASY;

    // Check if user's solution is correct
    private JButton checkButton;
    // Fills the sudoku with the correct solution
    private JButton solutionButton;
    // Shows various messages
    public JTextArea console;

    private SudokuUI() {
        setTitle("rSudoku "+Double.toString(versionNumber));
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        GridLayout squareLayout4x3 = new GridLayout(4, 3);
        GridLayout squareLayout3x3 = new GridLayout(3, 3);
        setLayout(squareLayout4x3); // Main layout (3x3 for sudoku and 1x3 for bottom part)

        Border outline = BorderFactory.createLineBorder(Color.black);
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.BOTH;

        JPanel diffPanel = new JPanel();    // New game buttons
        JPanel checkPanel = new JPanel();   // Check/show solution buttons

        diffPanel.setLayout(new BoxLayout(diffPanel, BoxLayout.PAGE_AXIS));

        diffPanel.add(new DifficultyButton(Sudoku.Difficulty.EASY));
        diffPanel.add(new DifficultyButton(Sudoku.Difficulty.MEDIUM));
        diffPanel.add(new DifficultyButton(Sudoku.Difficulty.HARD));

        checkButton = new JButton();
        checkButton.addActionListener(this);
        checkButton.setText("Check");
        checkButton.setMaximumSize(new Dimension(200, 100));
        checkButton.setEnabled(false);

        solutionButton = new JButton();
        solutionButton.addActionListener(this);
        solutionButton.setText("Show solution");
        solutionButton.setMaximumSize(new Dimension(200, 100));
        solutionButton.setEnabled(false);

        checkPanel.setLayout(new BoxLayout(checkPanel, BoxLayout.PAGE_AXIS));
        checkPanel.add(checkButton);
        checkPanel.add(solutionButton);
        console = new JTextArea();
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        console.setFont(new Font("SansSerif", Font.BOLD, 16));
        console.setMargin(new Insets(5, 5, 5, 5));
        JPanel[] panels = new JPanel[9];
        cells = new CellPanel[9][];

        for(int i = 0; i < 9; i++) {
            panels[i] = new JPanel();
            cells[i] = new CellPanel[9];
            panels[i].setLayout(squareLayout3x3);
            panels[i].setBorder(outline);
            add(panels[i]);
        }

        // Add empty cells
        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++ ) {
                int sq = getSquareNum(row, column);
                CellPanel panel = new CellPanel(false);
                panels[sq].add(panel);
                cells[row][column] = panel;
            }
        }

        add(diffPanel);
        add(checkPanel);
        add(console);

        newGame(difficulty);
    }

    // Singleton
    public static SudokuUI getInstance() {
        if(instance == null) {
            instance = new SudokuUI();
        }
        return instance;
    }

    public void newGame(Sudoku.Difficulty diff) {
        long startTime = System.currentTimeMillis();

        sudoku = new SudokuFactory().create(diff);

        long timeDiff = System.currentTimeMillis() - startTime;

        console.setText(String.format("Generated %s sudoku in %d milliseconds.",
                diff.getName().toLowerCase(), timeDiff));

        mapCells(sudoku);
        checkButton.setEnabled(true);
        solutionButton.setEnabled(true);
    }

    private void mapCells(Sudoku sudoku) {
        SudokuCell[][] cellData = sudoku.getCells();

        for(int i = 0; i < cellData.length; i++) {
            for(int j = 0; j < cellData.length; j++) {
                cells[i][j].setSudokuCell(cellData[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == checkButton) {
            if(isCorrectlyFilled()) {
                console.setText("CORRECT!");
            } else {
                console.setText("Incorrect");
            }
        }

        if(e.getSource() == solutionButton) {
            showSolution();
            console.setText("");
        }
    }

    /**
     * Check that every cell has correct value filled by player
     * @return
     */
    private boolean isCorrectlyFilled() {
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                if(!cells[r][c].isCorrectValue()) return false;
            }
        }
        return true;
    }

    private void showSolution() {
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9;col++) {
                cells[row][col].setCorrectValue();
            }
        }
    }

    /**
     * Get which big 3x3 square the cell in given position belongs to
     * @return Returns 0-8
     *
     * 0 1 2
     * 3 4 5
     * 6 7 8
     */
    private int getSquareNum(int row, int col) {
        int[] ar = new int[2];
        ar[0] = row / 3;
        ar[1] = col / 3;

        int sqnum = ar[0] * 3 + ar[1];
        return sqnum;
    }
}
