package com.tvion.first.dialogs;

import com.tvion.first.model.BookModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitDialog extends JDialog {
    public ExitDialog(BookModel model) {
        setTitle("Saving");
        setSize(270, 100);
        setLocation(550, 300);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);

        JPanel panel = new JPanel();
        JPanel buttons = new JPanel();
        JLabel removeLabel = new JLabel("Do you want to save?");
        JButton saveButton = new JButton("Save");
        JButton notSaveButton = new JButton("Don't save");
        JButton cancelButton = new JButton("Cancel");

        add(panel);
        panel.add(removeLabel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        buttons.add(saveButton);
        buttons.add(notSaveButton);
        buttons.add(cancelButton);

        cancelButton.addActionListener((event) -> this.dispose());
        saveButton.addActionListener((event) -> {
            model.saveChanges();
            System.exit(0);
        });

        notSaveButton.addActionListener((event) -> System.exit(0));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
