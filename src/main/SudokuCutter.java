package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates a complete sudoku for a person to solve from filled sudoku board
 * by hiding(cutting) cells while leaving only 1 possible solution.
 *
 * SudokuCutter can be used to make different layout / different difficulty sudokus
 * from the same filled sudoku
 */
public class SudokuCutter {
    private Sudoku sudoku;

    // Cells that are tested if they can be cutted (and then are cutted if so)
    private List<SudokuCell> cellsToTest;

    private final Random rand;

    public SudokuCutter(Sudoku sudoku, String seed) {
        this.sudoku = sudoku.copy();

        cellsToTest = new ArrayList<>();

        SudokuCell[][] allCells = this.sudoku.getCells();

        // Add all cells to the list
        for(int i = 0; i < allCells.length; i++) {
            for (int j = 0; j < allCells[i].length; j++) {
                cellsToTest.add(allCells[i][j]);
            }
        }

        if(seed != null) {
            this.rand = new Random(Long.valueOf(seed));
        } else {
            this.rand = new Random();
        }
    }

    public SudokuCutter(Sudoku sudoku) {
        this(sudoku, null);
    }

    public Sudoku cutDown(int wantedCuts) {
        int cuts = 0;   // Cuts done so far

        SudokuSolver solver = new SudokuSolver(sudoku);

        while(cuts < wantedCuts && cellsToTest.size() > 0) {
            int cellIndex = rand.nextInt(cellsToTest.size());
            SudokuCell cell = cellsToTest.get(cellIndex);
            cellsToTest.remove(cellIndex);

            cell.setHidden(true);

            int solutions = solver.findSolutions();

            if(solutions == 1) {
                cuts++;
            }
        }

        if(Main.SHOW_DEBUG) System.out.println("Made " + cuts + " cuts");
        return sudoku;
    }
}
