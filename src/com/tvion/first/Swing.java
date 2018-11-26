package com.tvion.first;

import com.tvion.first.dialogs.AddDialog;
import com.tvion.first.dialogs.EditDialog;
import com.tvion.first.dialogs.ExitDialog;
import com.tvion.first.dialogs.RemoveDialog;
import com.tvion.first.model.BookModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Swing extends JFrame {
    private BookModel m = new BookModel();

    public Swing() {
        super("Book Table");
        setSize(550, 350);
        setLocation(350, 250);
        setDefaultCloseOperation(0);
        JTable table = new JTable(m);
        JPanel buttons = new JPanel();
        JMenuBar menu = new JMenuBar();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JScrollPane jScrollPane = new JScrollPane(table);
        setJMenuBar(menu);
        menu.add(createMenu());

        add(jScrollPane, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);


        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(30);
        table.getColumnModel().getColumn(5).setPreferredWidth(30);
        table.setRowHeight(20);

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                JDialog exitDialog = new ExitDialog(m);
                exitDialog.setVisible(true);
            }
        });

        setVisible(true);
    }


    private JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As");


        open.addActionListener((event) -> {
            JFileChooser fileOpener = new JFileChooser();
            fileOpener.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return isXml(file);
                }

                @Override
                public String getDescription() {
                    return ".xml files";
                }
            });
            int ret = fileOpener.showDialog(this, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileOpener.getSelectedFile();
                    m.setSource(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        saveAs.addActionListener((event) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return isXml(file);
                }

                @Override
                public String getDescription() {
                    return ".xml";
                }
            });
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    String path = fileChooser.getSelectedFile().getPath();
                    File newFile = new File(path + ".xml");
                    newFile.createNewFile();
                    m.saveChangesAs(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        save.addActionListener((event) -> m.saveChanges());


        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        return fileMenu;
    }

    private boolean isXml(File file) {
        if (file == null) return false;
        if (file.isDirectory()) return true;
        String[] nameAndPath = file.getName().split("\\.");
        if (nameAndPath.length > 1) {
            String ext = nameAndPath[nameAndPath.length - 1];
            return "xml".equals(ext.toLowerCase());
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Swing::new);
    }
}



