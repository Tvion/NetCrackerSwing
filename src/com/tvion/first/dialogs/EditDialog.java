package com.tvion.first.dialogs;

import com.tvion.first.entities.Author;
import com.tvion.first.entities.Book;
import com.tvion.first.model.BookModel;

import javax.swing.*;
import java.awt.*;

public class EditDialog extends JDialog {

    private String authorGender = "Male";

    public EditDialog(JFrame frame, BookModel model, int selectedRow) {
        super(frame, "Adding Book");
        setSize(300, 270);
        setLocation(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel buttons = new JPanel();
        Book selectedBook = model.getBook(selectedRow);
        Author author = selectedBook.getAuthor();
        JTextField name = new JTextField(selectedBook.getName());
        JTextField authorName = new JTextField(author.getName());
        JTextField authorEmail = new JTextField(author.getEmail());
        JTextField price = new JTextField(String.valueOf(selectedBook.getPrice()));
        JTextField count = new JTextField(String.valueOf(selectedBook.getQty()));
        JRadioButton genderMaleButton = new JRadioButton();
        JRadioButton genderFemaleButton = new JRadioButton();
        JLabel nameL = new JLabel("Name");
        JLabel authorsNameL = new JLabel("Author's Name");
        JLabel authorEmailL = new JLabel("Author's Email");
        JLabel authorGenderL = new JLabel("Authors Gender");
        JLabel priceL = new JLabel("Price");
        JLabel countL = new JLabel("Count");
        JLabel maleL = new JLabel("Male");
        JLabel femaleL = new JLabel("Female");
        JButton addButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonsPanel = new JPanel();
        ButtonGroup bg = new ButtonGroup();
        bg.add(genderFemaleButton);
        bg.add(genderMaleButton);
        buttonsPanel.add(genderMaleButton);
        buttonsPanel.add(maleL);
        buttonsPanel.add(genderFemaleButton);
        buttonsPanel.add(femaleL);

        panel.add(nameL);
        panel.add(name);
        panel.add(authorsNameL);
        panel.add(authorName);
        panel.add(authorEmailL);
        panel.add(authorEmail);
        panel.add(authorGenderL);
        panel.add(buttonsPanel);
        panel.add(priceL);
        panel.add(price);
        panel.add(countL);
        panel.add(count);
        buttons.add(addButton);
        buttons.add(cancelButton);
        this.add(panel);
        this.add(buttons, BorderLayout.SOUTH);

        if (author.getGender().equals("Female")) {
            genderFemaleButton.setSelected(true);
        } else {
            genderMaleButton.setSelected(true);
        }

        genderFemaleButton.addActionListener((event) -> {
            authorGender = "Female";
        });

        genderMaleButton.addActionListener((event) -> {
            authorGender = "Male";
        });

        addButton.addActionListener((event) -> {
            String[] values = {name.getText(), count.getText(), price.getText(), authorName.getText(), authorEmail.getText(), authorGender};
            if (AddDialog.verifyValues(this, values)) setBook(selectedRow, model, values);
        });
        cancelButton.addActionListener((event) -> this.dispose());
    }

    private void setBook(int index, BookModel m, String... values) {
        Author bookAuthor = new Author(values[3], values[4], values[5]);
        Book editingBook = new Book(values[0], Integer.parseInt(values[1]), Double.parseDouble(values[2]), bookAuthor);
        m.setBook(index, editingBook);
        this.dispose();
    }


}

