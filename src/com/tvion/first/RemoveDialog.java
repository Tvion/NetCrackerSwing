package com.tvion.first;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RemoveDialog extends JDialog {
    public RemoveDialog(JFrame frame, BookModel model, int[] selectedRows) {
        super(frame, "Removing Book");
        String f = Arrays.toString(selectedRows);
        setSize(200, 130);
        setLocation(550, 300);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);
        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JLabel removeLabel = new JLabel("Delete selected books?");
        JButton removeButton = new JButton("Remove");
        JButton cancelButton = new JButton("Cancel");
        add(panel);
        panel.add(removeLabel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        buttons.add(removeButton);
        buttons.add(cancelButton);
        cancelButton.addActionListener((event) -> this.dispose());
        removeButton.addActionListener((event) -> {
            model.removeBooks(selectedRows);
            this.dispose();
        });
    }
}

