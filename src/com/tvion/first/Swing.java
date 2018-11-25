package com.tvion.first;

import javax.swing.*;
import java.awt.*;


public class Swing extends JFrame {
    public Swing() {
        super("Book Table");
        setSize(550, 350);
        setLocation(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BookModel m = new BookModel();
        JTable table = new JTable(m);
        JPanel buttons = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        addButton.addActionListener((event) -> {
            JDialog add = new AddDialog(this, m);
            add.setVisible(true);
        });
        deleteButton.addActionListener((event) -> {
            JDialog delete = new RemoveDialog(this, m, table.getSelectedRows());
            delete.setVisible(true);
        });
        editButton.addActionListener((event) -> {
            JDialog edit = new EditDialog(this, m, table.getSelectedRow());
            edit.setVisible(true);
        });

        table.getSelectionModel().addListSelectionListener((event) -> {
            if (table.getSelectedRow() == -1) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
            if (table.getSelectedRows().length > 0) {
                editButton.setEnabled(false);
                deleteButton.setEnabled(true);
            }
            if (table.getSelectedRows().length == 1) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });

        table.getColumnModel().getColumn(1).setMinWidth(250);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Swing());
    }
}



