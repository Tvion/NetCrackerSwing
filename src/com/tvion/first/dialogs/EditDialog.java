package com.tvion.first.dialogs;

import com.tvion.first.entities.Author;
import com.tvion.first.entities.Book;
import com.tvion.first.model.BookModel;

import javax.swing.*;
import java.awt.*;

public class EditDialog extends JDialog {

    private String authorGender = "Male";

    public EditDialog(JFrame frame, BookModel model, int selectedRow) {
        super(frame, "Editing Book");
        setSize(260, 270);
        setLocation(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        Book selectedBook = model.getBook(selectedRow);
        Author author = selectedBook.getAuthor();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel buttons = new JPanel();
        JTextField name = new JTextField(selectedBook.getName());
        JTextField authorName = new JTextField(author.getName());
        JTextField authorEmail = new JTextField(author.getEmail());
        JTextField price = new JTextField(String.valueOf(selectedBook.getPrice()));
        JTextField count = new JTextField(String.valueOf(selectedBook.getQty()));

        Box radioBox = Box.createHorizontalBox();
        JRadioButton genderMaleButton = new JRadioButton("Male");
        JRadioButton genderFemaleButton = new JRadioButton("Female");

        JLabel nameL = new JLabel("Name");
        JLabel authorsNameL = new JLabel("Author's Name");
        JLabel authorEmailL = new JLabel("Author's Email");
        JLabel authorGenderL = new JLabel("Authors Gender");
        JLabel priceL = new JLabel("Price");
        JLabel countL = new JLabel("Count");

        JButton addButton = new JButton("Edit");
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

        if (author.getGender().equals("Female")) {
            genderFemaleButton.setSelected(true);
        } else {
            genderMaleButton.setSelected(true);
        }

        genderFemaleButton.addActionListener((event) -> authorGender = "Female");

        genderMaleButton.addActionListener((event) -> authorGender = "Male");

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

