/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asmedit.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.TitledBorder;


/**
 *
 * @author koukola
 */






public class RegisterView extends JPanel {
    private long value;
    private final JLabel valueLabel;
    private final JComboBox<String> formatSelector;

    // Formatting constants
    private static final String HEX = "HEX";
    private static final String DEC = "DEC";
    private static final String BIN = "BIN";

    
    public RegisterView() {
        this("r15", 44);
    }
    
    public RegisterView(String registerName, long initialValue) {
        this.value = initialValue;

        // 1. Layout & Border (The "Box")
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), 
                registerName, 
                TitledBorder.LEFT, 
                TitledBorder.TOP
        ));

        // 2. The Value Display
        valueLabel = new JLabel();
        valueLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(valueLabel, BorderLayout.CENTER);

        // 3. The Format Selector
        String[] formats = { HEX, DEC, BIN };
        formatSelector = new JComboBox<>(formats);
        formatSelector.addActionListener(e -> updateDisplay());
        add(formatSelector, BorderLayout.SOUTH);

        // Initial render
        updateDisplay();
    }

    public void setValue(long newValue) {
        this.value = newValue;
        updateDisplay();
    }

    private void updateDisplay() {
        String selected = (String) formatSelector.getSelectedItem();
        String text = "";

        switch (selected) {
            case HEX -> text = "0x" + Long.toHexString(value).toUpperCase();
            case DEC -> text = Long.toString(value);
            case BIN -> {
                // Binary padding to 16-bits (adjust as needed for your architecture)
                String rawBin = Long.toBinaryString(value);
                text = String.format("%16s", rawBin).replace(' ', '0');
            }
        }
        valueLabel.setText(text);
    }
}