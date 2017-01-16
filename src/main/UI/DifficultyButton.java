package main.UI;

import main.Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UI button that starts a new game with specific difficulty
 */
public class DifficultyButton extends JButton implements ActionListener {
    Sudoku.Difficulty difficulty;

    public DifficultyButton(Sudoku.Difficulty diffEnum) {
        this.difficulty = diffEnum;

        addActionListener(this);
        setText("New " + diffEnum.getName().toLowerCase() + " game");
        setMaximumSize(new Dimension(200, 450));
    }

    public void actionPerformed(ActionEvent event) {
        SudokuUI.getInstance().newGame(this.difficulty);
    }
}
