package com.tvion.first.dialogs;

import com.tvion.first.model.BookModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitDialog extends JDialog {
    private static boolean isCancel = false;

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        ExitDialog.isCancel = cancel;
    }

    public ExitDialog(BookModel model, boolean isClosing) {
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

        cancelButton.addActionListener((event) -> {
            isCancel = true;
            this.dispose();
        });
        saveButton.addActionListener((event) -> {
            model.saveChanges();
            if (isClosing) {
                System.exit(0);
            }
            isCancel = false;
            this.dispose();
        });

        notSaveButton.addActionListener((event) -> {
            if (isClosing) {
                System.exit(0);
            }
            isCancel = false;
            this.dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isCancel = true;
                dispose();
            }
        });


    }
}
