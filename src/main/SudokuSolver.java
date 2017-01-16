package main;

import java.util.*;

public class SudokuSolver {
	private Sudoku sudoku;

	// Hidden cells (the ones player needs to fill in) with list of values not tested
    private Map<SudokuCell, List<Integer>> cellsToFill;

	public SudokuSolver(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

    /**
     * Check solutions for the sudoku
     * @return Returns how many possible solutions the sudoku has
     *
     * Proper sudoku only has one solution
     */
	public int findSolutions() {
	    // Backup the original
	    Sudoku origSudoku = sudoku.copy();

		int solutions = 1;

		// Hidden cells are the ones that the player has to fill in
        List<SudokuCell> hiddenCells = getHiddenCells();
        cellsToFill = new HashMap<>();

        for(int i = 0; i < hiddenCells.size(); i++) {
            SudokuCell cell = hiddenCells.get(i);
            cell.setValue(0);

            List<Integer> valuesToTest;

            if(!cellsToFill.containsKey(cell)) {
                valuesToTest = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                cellsToFill.put(hiddenCells.get(i), valuesToTest);
            } else {
                valuesToTest = cellsToFill.get(cell);
            }

            int correctValues = 0;  // How many correct values are found for the cell

            while(valuesToTest.size() > 0) {
                Integer testValue = valuesToTest.get(0);
                valuesToTest.remove(testValue);

                if(sudoku.testCellWithValue(hiddenCells.get(i), testValue)) {
                    hiddenCells.get(i).setValue(testValue);
                    correctValues++;

                    if(correctValues > 1) {
                        // 2 solutions possible for the sudoku, NOT ALLOWED!
                        break;
                    }
                }
            }

            if(correctValues == 0) {
                cell.setValue(0);
                cellsToFill.put(cell, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
                // Previous cell(s) filled needs different value
                i -= 2;
            } else if(correctValues > 1) {
                solutions = 2;
                break;
            } else {
                return 1;
            }
        }

        // Restore the sudoku to unfilled state
        sudoku = origSudoku;

		return solutions;
	}

	// Get cells that user needs to fill
	private List<SudokuCell> getHiddenCells() {
	    List<SudokuCell> hiddenCells = new ArrayList<>();

        for(int row = 0; row < 9; row++) {
            for(int column = 0; column < 9; column++) {
                SudokuCell sc = sudoku.getCellAt(row, column);

                if(sc.isHidden()) {
                    hiddenCells.add(sc);
                }
            }
        }
        return hiddenCells;
    }
}
