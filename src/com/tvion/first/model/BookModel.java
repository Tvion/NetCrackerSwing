package com.tvion.first.model;

import com.tvion.first.entities.Author;
import com.tvion.first.entities.Book;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BookModel extends AbstractTableModel {
    private final int TAB_COUNT = 2;

    private List<Book> books = new ArrayList<>();

    File source = new File("Books.xml");

    public BookModel() {
        try {
            if (!source.exists()) {
                source.createNewFile();
            }
            parseSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book b) {
        books.add(b);
        fireTableDataChanged();
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public void setBook(int index, Book book) {
        books.set(index, book);
        fireTableDataChanged();
    }

    public void setSource(File source) throws FileNotFoundException {
        this.source = source;
        books.clear();
        parseSource();
        fireTableDataChanged();
    }

    public void saveChangesAs(File newFile) throws FileNotFoundException {
        source = newFile;
        saveChanges();
        books.clear();
        parseSource();
    }

    public void removeBooks(int[] booksNumbers) {
        ArrayList<Book> selectedBooks = new ArrayList<>();
        for (int i : booksNumbers)
            selectedBooks.add(books.get(i));
        books.removeAll(selectedBooks);
        fireTableDataChanged();
    }

    public void saveChanges() {
        try (PrintWriter printWriter = new PrintWriter(source);) {
            if (source.exists()) {
                source.delete();
            }
            source.createNewFile();

            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            printWriter.println("<books>");
            books.forEach((book) -> {
                int tabs = 0;
                tabs++;
                printWriter.println(tab(tabs) + "<book>");
                tabs++;
                printWriter.print(tab(tabs) + "<name>");
                printWriter.print(" " + book.getName());
                printWriter.println(" " + "</name>");

                printWriter.print(tab(tabs) + "<quantity>");
                printWriter.print(" " + book.getQty());
                printWriter.println(" " + "</quantity>");

                printWriter.print(tab(tabs) + "<price>");
                printWriter.print(" " + book.getPrice());
                printWriter.println(" " + "</price>");

                printWriter.println(tab(tabs) + "<author>");
                tabs++;
                printWriter.print(tab(tabs) + "<name>");
                printWriter.print(" " + book.getAuthor().getName());
                printWriter.println(" " + "</name>");

                printWriter.print(tab(tabs) + "<email>");
                printWriter.print(" " + book.getAuthor().getEmail());
                printWriter.println(" " + "</email>");

                printWriter.print(tab(tabs) + "<gender>");
                printWriter.print(" " + book.getAuthor().getGender());
                printWriter.println(" " + "</gender>");
                tabs--;
                printWriter.println(tab(tabs) + "</author>");
                tabs--;
                printWriter.println(tab(tabs) + "</book>");
            });
            printWriter.print("</books>");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSource() throws FileNotFoundException {
        Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            String name;
            int quantity;
            double price;
            String authorName;
            String authorEmail;
            String authorGender;
            if (scanner.nextLine().equals("  <book>")) {
                name = scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1];
                quantity = Integer.parseInt(scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1]);
                price = Double.parseDouble(scanner.nextLine().split(" ")[TAB_COUNT * 2 + 1]);
                scanner.nextLine();
                authorName = scanner.nextLine().split(" ")[TAB_COUNT * 3 + 1];
                authorEmail = scanner.nextLine().split(" ")[TAB_COUNT * 3 + 1];
                authorGender = scanner.nextLine().split(" ")[TAB_COUNT * 3 + 1];
                books.add(new Book(name, quantity, price, new Author(authorName, authorEmail, authorGender)));
            }
        }
        fireTableDataChanged();
    }

    private String tab(int countTab) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < countTab; i++) {
            result.append("  ");
        }
        return result.toString();
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book cur = books.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cur.getName();
            case 1:
                return cur.getAuthor().getName();
            case 2:
                return cur.getAuthor().getGender();
            case 3:
                return cur.getAuthor().getEmail();
            case 4:
                return cur.getPrice();
            case 5:
                return cur.getQty();
        }
        return null;
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Book name";
            case 1:
                return "Author's Name";
            case 2:
                return "Author's Gender";
            case 3:
                return "Author's Email";
            case 4:
                return "Price";
            case 5:
                return "Count";
        }
        return "";
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Double.class;
            case 5:
                return Integer.class;
        }
        return Object.class;
    }
}