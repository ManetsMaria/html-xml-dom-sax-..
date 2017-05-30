package main.view;

import main.Student;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class MainPanel extends JPanel {
    private StudentTableModel tableModel = new StudentTableModel();



    public MainPanel() {
        super(new GridLayout(1,0));
        JTable table = new JTable(tableModel);
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\u007f') {
                    tableModel.deleteRaws(table.getSelectedRows());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        add(new ButtonPanel(this));
    }

    public StudentTableModel getTableModel() {
        return tableModel;
    }

    public void onNewStudentList(List<Student> students) {
        tableModel.setStudents(students);
    }

    public void onNewStudent(Student student) {
        tableModel.addStudent(student);
    }

    static class StudentTableModel extends AbstractTableModel {
        private static final String[] HEADERS = {
                "Surname", "Age", "University", "RecordBook"
        };
        private List<Student> students = new ArrayList();


        @Override
        public int getRowCount() {
            return students.size();
        }

        @Override
        public int getColumnCount() {
            return HEADERS.length;
        }

        @Override
        public String getColumnName(int column) {
            return HEADERS[column];
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
            fireTableDataChanged();
        }

        private boolean isUnique(Student student) {
            return students.indexOf(student) == -1;
        }

        public void addStudent(Student student) {
            if (isUnique(student)) {
                students.add(student);
                fireTableRowsInserted(students.size() - 1,  students.size() - 1);
            }
        }

        public void deleteRaws(int[] rawIndexes) {
            for (int i = rawIndexes.length - 1; i >= 0; i--) {
                students.remove(rawIndexes[i]);
            }
            fireTableDataChanged();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Student rowStudent = students.get(rowIndex);
            if (columnIndex == 0) {
                return rowStudent.surname;
            } else if (columnIndex == 1) {
                return rowStudent.age;
            } else if (columnIndex == 2) {
                return rowStudent.university;
            } else if (columnIndex == 3) {
                return rowStudent.recordBook;
            }
            return null;
        }
    }
}
