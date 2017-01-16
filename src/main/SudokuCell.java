package main;

/**
 * Contains data for single sudoku cell in the 9x9 grid
 */
public class SudokuCell {
    private int value;

	private int row;
	private int column;

	private SudokuCell previousCell;
    private SudokuCell nextCell;

    // Hidden cell is one that user needs to fill
    private boolean hidden;

	public SudokuCell(int row, int column) {
	    this.row = row;
        this.column = column;

        this.hidden = false;
	}

    public SudokuCell(int row, int index, int value) {
        this(row, index);

        this.value = value;
    }

    /**
     * Hide the value so that user must figure it out
     * @param hidden
     */
	public void setHidden(boolean hidden) {
	    this.hidden = hidden;
    }

    /**
     * Create new SudokuCell with same row, column and value
     * @return
     */
    public SudokuCell copy() {
        return new SudokuCell(getRow(), getColumn(), getValue());
    }

    public boolean isHidden() {
        return this.hidden;
    }

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	public void setPreviousCell(SudokuCell cell) {
	    this.previousCell = cell;
    }

    public SudokuCell getPreviousCell() {
        return this.previousCell;
    }

    public void setNextCell(SudokuCell cell) { this.nextCell = cell; }

    public SudokuCell getNextCell() { return this.nextCell; }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
