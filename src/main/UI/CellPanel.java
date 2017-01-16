package main.UI;

import main.SudokuCell;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * UI panel for single sudoku cell/number slot
 */
public class CellPanel extends JTextField {

    // Text field that only allows single digit
    // modified from http://stackoverflow.com/questions/24472935/how-does-java-limit-text-fields
    private class JSingleDigitTextField extends PlainDocument {
        int[] allowedDigits;

        JSingleDigitTextField(int... allowedDigits) {
            super();
            this.allowedDigits = allowedDigits;
        }

        public void insertString
                (int offset, String  str, AttributeSet attr)
                throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= 1) {
                int val;

                try {
                    val = Integer.parseInt(str);
                } catch(NumberFormatException e) {
                    return;
                }

                if(digitAllowed(val)) {
                    super.insertString(offset, str, attr);
                }
            }
        }

        private boolean digitAllowed(int digit) {
            for(int i : allowedDigits) {
                if(i == digit) return true;
            }
            return false;
        }
    }

    // Font for prefilled numbers
    static final Font prefilledFont = new Font("SansSerif", Font.BOLD, 20);
    static final Color prefilledColor = Color.black;

    // Font for numbers that user enters
    static final Font userFont = new Font("SansSerif", Font.BOLD, 20);
    static final Color userColor = Color.blue;

    SudokuCell cell;

    public CellPanel(boolean editable) {
        super();

        setDocument(new JSingleDigitTextField(1, 2, 3, 4, 5, 6, 7, 8, 9));
        setPreferredSize(new Dimension(25, 25));
        setHorizontalAlignment(JTextField.CENTER);
        setEditable(editable);
        setText("");
        setBackground(Color.white);

    }

    public void setSudokuCell(SudokuCell cell) {
        this.cell = cell;

        setEditable(cell.isHidden());

        if(!cell.isHidden()) {
            setText(Integer.toString(cell.getValue()));
            setFont(prefilledFont);
            setForeground(prefilledColor);
        } else {
            setText("");
            setFont(userFont);
            setForeground(userColor);
        }
    }

    // Check if filled value matches the correct one
    public boolean isCorrectValue() {
        try {
            return Integer.valueOf(getText()) == cell.getValue();
        } catch(NumberFormatException ex) {
            return false;
        }

    }

    // Show the correct value in the panel
    public void setCorrectValue() {
        setText(String.valueOf(cell.getValue()));
    }
}
