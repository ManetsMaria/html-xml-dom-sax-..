package main.view;

import main.Student;

import javax.swing.*;
import java.awt.*;


public class ButtonPanel extends JPanel {
    private MainPanel mainPanel;

    public ButtonPanel(MainPanel mainPanel) {
        super();
        this.mainPanel = mainPanel;
        setLayout(new GridLayout(6, 2));

        JTextField surnameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField universityField = new JTextField();
        JTextField recordBookField = new JTextField(7);
        JButton addStudentButton = new JButton("Add student");

        addStudentButton.addActionListener(event -> {
            String surname = surnameField.getText().trim();
            String ageStr = ageField.getText().trim();
            String university = universityField.getText().trim();
            String recordBook = recordBookField.getText().trim();
            if (surname.isEmpty() || recordBook.isEmpty()
                    || ageStr.isEmpty() || university.isEmpty()) {
                JOptionPane.showMessageDialog(this, "To add new student, fill all fields");
            } else {
                try {
                    int age = Integer.parseInt(ageStr);
                    if (age < 0)
                        throw new NumberFormatException();
                    Student student = new Student(age, surname, university, recordBook);
                    mainPanel.onNewStudent(student);
                } catch (NumberFormatException e) {
                    showMessage("Age should be positive integer number");
                    return;
                }

            }
        });


        add(new JLabel("Add new student"));
        add(new JLabel());
        add(new JLabel("Surname:"));
        add(surnameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("University:"));
        add(universityField);
        add(new JLabel("Record book:"));
        add(recordBookField);
        add(addStudentButton);
    }

    private void showMessage(String text) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, text);
        });
    }

}
