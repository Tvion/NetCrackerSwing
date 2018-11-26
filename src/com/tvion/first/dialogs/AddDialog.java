package com.tvion.first.dialogs;

import com.tvion.first.entities.Author;
import com.tvion.first.entities.Book;
import com.tvion.first.model.BookModel;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDialog extends JDialog {

    private String authorGender = "Male";

    public AddDialog(JFrame frame, BookModel model) {
        super(frame, "Adding Book");
        setSize(260, 270);
        setLocation(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel buttons = new JPanel();
        JTextField name = new JTextField();
        JTextField authorName = new JTextField();
        JTextField authorEmail = new JTextField();
        JTextField price = new JTextField();
        JTextField count = new JTextField();

        Box radioBox = Box.createHorizontalBox();
        JRadioButton genderMaleButton = new JRadioButton("Male");
        JRadioButton genderFemaleButton = new JRadioButton("Female");

        JLabel nameL = new JLabel("Name");
        JLabel authorsNameL = new JLabel("Author's Name");
        JLabel authorEmailL = new JLabel("Author's Email");
        JLabel authorGenderL = new JLabel("Authors Gender");
        JLabel priceL = new JLabel("Price");
        JLabel countL = new JLabel("Count");

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        ButtonGroup bg = new ButtonGroup();
        bg.add(genderFemaleButton);
        bg.add(genderMaleButton);

        radioBox.add(genderMaleButton);
        radioBox.add(genderFemaleButton);


        panel.add(nameL);
        panel.add(name);
        panel.add(authorsNameL);
        panel.add(authorName);
        panel.add(authorEmailL);
        panel.add(authorEmail);
        panel.add(authorGenderL);
        panel.add(radioBox);
        panel.add(priceL);
        panel.add(price);
        panel.add(countL);
        panel.add(count);

        buttons.add(addButton);
        buttons.add(cancelButton);

        this.add(panel);
        this.add(buttons, BorderLayout.SOUTH);

        genderMaleButton.setSelected(true);

        genderFemaleButton.addActionListener((event) -> authorGender = "Female");

        genderMaleButton.addActionListener((event) -> authorGender = "Male");

        addButton.addActionListener((event) -> {
            String[] values = {name.getText(), count.getText(), price.getText(), authorName.getText(), authorEmail.getText(), authorGender};
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

        Pattern name = Pattern.compile("^[a-zA-Z0-9\\s]{3,25}$");
        Pattern nameAuthor = Pattern.compile("^[a-zA-Z\\s]{3,25}");
        Pattern num = Pattern.compile("^[0-9]{1,10000000}$");
        Pattern price = Pattern.compile("^[0-9]{1,10000000}+\\.[0-9]{1,10000000}$");
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher m = name.matcher(values[0]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Name must contains 3-25 symbols A-z and numbers");
            return false;
        }
        m = nameAuthor.matcher(values[3]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Authors name must contains 3-25 symbols A-z");
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
        m = email.matcher(values[4]);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(dialog, "Wrong Email Format");
            return false;
        }
        return true;
    }

}

