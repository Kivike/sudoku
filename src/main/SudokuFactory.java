package main;

import java.util.*;

/**
 * Creates play-ready sudoku
 */
public class SudokuFactory {
    private Sudoku sudoku;
    private Random randomizer;

    // Cells with their possible values
    private Map<SudokuCell, List<Integer>> possibleValues;

    public SudokuFactory() {
        this.randomizer = new Random();
    }

    /**
     * Create new sudoku
     * @param difficulty Difficulty (how many numbers are left hidden)
     * @return
     */
    public Sudoku create(Sudoku.Difficulty difficulty) {
        this.sudoku = new Sudoku();

        randomizer = new Random();

        // Start with the first cell
        SudokuCell cell = sudoku.getCells()[0][0];

        possibleValues = new HashMap<>();

        do {
            List<Integer> values = possibleValues.get(cell);

            if(values == null) {
                values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                possibleValues.put(cell, values);
            }

            if(!testEveryCellValue(cell)) {
                // No possible value found for the cell
                possibleValues.put(cell, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
                cell = cell.getPreviousCell();
            } else {
                // Found a value for the cell
                if(cell.getNextCell() != null) {
                    cell = cell.getNextCell();
                } else {
                    // All cells filled
                    break;
                }
            }
        } while (true);

        if(Main.SHOW_DEBUG) System.out.println(sudoku.toString());

        sudoku = new SudokuCutter(sudoku).cutDown(difficulty.getValue());

        if(Main.SHOW_DEBUG) System.out.println(sudoku.toString());

        return sudoku;
    }

    /**
     * Test every available value for a cell
     * @param cell Cell to test
     * @return Returns false if none of the values work
     */
    private boolean testEveryCellValue(SudokuCell cell) {
        List<Integer> availableTestValues = possibleValues.get(cell);
        Integer curTestValue;

        do {
            if(availableTestValues.size() == 0) {
                cell.setValue(0);
                return false;
            }

            curTestValue = availableTestValues.get(randomizer.nextInt(availableTestValues.size()));
            availableTestValues.remove(curTestValue);

            if(sudoku.testCellWithValue(cell, curTestValue)) {
                // Cell passed all tests, set its value
                cell.setValue(curTestValue);
                break;
            }
        } while(true);

        return true;
    }
}
