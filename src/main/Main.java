// Sudoku game
// by Roope Rajala
//
// Uses an algorithm to first generate a complete grid.
// Then the grid is cut down grid until x (depending on difficulty)
// numbers are left using a solver to check if possible to cut certain cell.
// The check is done because a cut can cause sudoku to have
// multiple solutions which is not allowed.
// Tracks time taken to generate a finished sudoku.
//
// UI made with JFrame
//
//Todo: Change UI base from grid to something else so the controls/console can be made smaller in height

package main;

import main.UI.SudokuUI;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {
	public static final boolean SHOW_DEBUG = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SudokuUI ui = SudokuUI.getInstance();
				ui.setLocationRelativeTo(null);
				ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ui.setVisible(true);
			}
		});
	}

	// Used when debugging algorithms' progress
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}




