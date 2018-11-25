package com.tvion.first;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditDialog extends JDialog {
    EditDialog(JFrame frame, BookModel model, int selectedRow){
        super(frame,"Adding Book");
        setSize(350, 250);
        setLocation(400, 300);
        setDefaultCloseOperation( HIDE_ON_CLOSE );
        setModal(true);
        setResizable(false);
        JPanel panel=new JPanel(new GridLayout(0,2,10,10));
        JPanel buttons =new JPanel();
        Book selectedBook = model.getBook(selectedRow);
        Author author = selectedBook.getAuthor();
        JTextField name = new JTextField(selectedBook.getName());
        JTextField authorName = new JTextField(author.getName());
        JTextField authorEmail = new JTextField(author.getEmail());
        JTextField authorGender = new JTextField(author.getGender());
        JTextField price = new JTextField(String.valueOf(selectedBook.getPrice()));
        JTextField count = new JTextField(String.valueOf(selectedBook.getQty()));
        JLabel nameL=new JLabel("Name");
        JLabel authorsNameL=new JLabel("Author's Name");
        JLabel authorEmailL=new JLabel("Author's Email");
        JLabel authorGenderL=new JLabel("Authors Gender");
        JLabel priceL=new JLabel("Price");
        JLabel countL=new JLabel("Count");
        JButton addButton=new JButton("Add");
        JButton cancelButton=new JButton("Cancel");
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
        this.add(buttons,BorderLayout.SOUTH);
        addButton.addActionListener((event)->{
            String[] values= {name.getText(),count.getText(),price.getText(),authorName.getText(),authorEmail.getText(),authorGender.getText()};
            if(AddDialog.verifyValues(this,values))setBook(selectedRow,model,values);
        });
        cancelButton.addActionListener((event)->this.dispose());
    }

    private void setBook(int index,BookModel m,String... values){
        Author bookAuthor= new Author(values[3],values[4],values[5]);
        Book editingBook = new Book(values[0],Integer.parseInt(values[1]),Double.parseDouble(values[2]),bookAuthor);
        m.setBook(index,editingBook);
        this.dispose();
    }


}

