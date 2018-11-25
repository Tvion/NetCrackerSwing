package com.tvion.first;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDialog extends JDialog {
    AddDialog(JFrame frame, BookModel model) {
        super(frame, "Adding Book");
        setSize(350, 250);
        setLocation(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel buttons = new JPanel();
        JTextField name = new JTextField(1);
        JTextField authorName = new JTextField(1);
        JTextField authorEmail = new JTextField(1);
        JTextField authorGender = new JTextField(1);
        JTextField price = new JTextField(1);
        JTextField count = new JTextField(1);
        JLabel nameL = new JLabel("Name");
        JLabel authorsNameL = new JLabel("Author's Name");
        JLabel authorEmailL = new JLabel("Author's Email");
        JLabel authorGenderL = new JLabel("Authors Gender");
        JLabel priceL = new JLabel("Price");
        JLabel countL = new JLabel("Count");
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        panel.add(nameL);
        panel.add(name);
        panel.add(authorsNameL);
        panel.add(authorName);
        panel.add(authorEmailL);
        panel.add(authorEmail);
        panel.add(authorGenderL);
        panel.add(authorGender);
        panel.add(priceL);
        panel.add(price);
        panel.add(countL);
        panel.add(count);
        buttons.add(addButton);
        buttons.add(cancelButton);
        this.add(panel);
        this.add(buttons, BorderLayout.SOUTH);
        addButton.addActionListener((event) -> {
            String[] values = {name.getText(), count.getText(), price.getText(), authorName.getText(), authorEmail.getText(), authorGender.getText()};
            if (verifyValues(this, values)) addBook(model, values);
        });
        cancelButton.addActionListener((event) -> this.dispose());
    }

    private void addBook(BookModel m, String... values) {
        Author bookAuthor = new Author(values[3], values[4], values[5]);
        Book addingBook = new Book(values[0], Integer.parseInt(values[1]), Double.parseDouble(values[2]), bookAuthor);
        m.addBook(addingBook);
        this.dispose();
    }

    public static boolean verifyValues(JDialog dialog, String[] values) {

        Pattern names = Pattern.compile("^[a-zA-Z0-9]{3,15}$");
        Pattern num = Pattern.compile("^[0-9]{1,10000000}$");
        Pattern price = Pattern.compile("^[0-9]{1,10000000}+\\.[0-9]{1,10000000}$");
        Pattern gender = Pattern.compile("^[f,m]{1}$", Pattern.CASE_INSENSITIVE);
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher m = names.matcher(values[0]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Name must contains 3-15 symbols A-z");
            return false;
        }
        m = names.matcher(values[3]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Authors name must contains 3-15 symbols A-z");
            return false;
        }
        m = num.matcher(values[1]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Count must be from 1 to 10000000");
            return false;
        }
        m = price.matcher(values[2]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Price must be Double");
            return false;
        }
        m = gender.matcher(values[5]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Gender must be f or m");
            return false;
        }
        m = email.matcher(values[4]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Wrong Email Format");
            return false;
        }
        return true;
    }

}

