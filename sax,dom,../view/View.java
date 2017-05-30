package main.view;

import main.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by NotePad.by on 21.05.2017.
 */
public class View implements ActionListener {
    private JFrame frame;
    private JMenuItem openXmlWithDOMParserItem;
    private JMenuItem openXmlWithSAXParserItem;
    private JMenuItem openBinaryFileItem;
    private JMenuItem saveAsBinaryFileItem;
    private JMenuItem saveAsXMLFileItem;
    private JMenuItem saveAsTxtFileItem;
    private JMenuItem saveAsHtmlTableItem;
    private MainPanel mainPanel;

    private static Random random = new Random();


    public View() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenu());

        mainPanel = new MainPanel();
        mainPanel.setOpaque(true);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenu openFileMenu = new JMenu("Open");
        openXmlWithDOMParserItem = new JMenuItem("XML with DOM parser");
        openXmlWithSAXParserItem = new JMenuItem("XML with SAX parser");
        openBinaryFileItem = new JMenuItem("Binary file");
        openXmlWithDOMParserItem.addActionListener(this);
        openXmlWithSAXParserItem.addActionListener(this);
        openBinaryFileItem.addActionListener(this);

        openFileMenu.add(openXmlWithDOMParserItem);
        openFileMenu.add(openXmlWithSAXParserItem);
        openFileMenu.add(openBinaryFileItem);

        JMenu saveFileMenu = new JMenu("Save as");
        saveAsXMLFileItem    = new JMenuItem("XML file");
        saveAsBinaryFileItem = new JMenuItem("Binary file");
        saveAsTxtFileItem = new JMenuItem("Txt file");
        saveAsHtmlTableItem = new JMenuItem("Html table");

        saveAsXMLFileItem.addActionListener(this);
        saveAsBinaryFileItem.addActionListener(this);
        saveAsTxtFileItem.addActionListener(this);
        saveAsHtmlTableItem.addActionListener(this);

        saveFileMenu.add(saveAsXMLFileItem);
        saveFileMenu.add(saveAsBinaryFileItem);
        saveFileMenu.add(saveAsTxtFileItem);
        saveFileMenu.add(saveAsHtmlTableItem);

        fileMenu.add(openFileMenu);
        fileMenu.add(saveFileMenu);
        return fileMenu;
    }

    public void onNewStudentList(List<Student> students) {
        mainPanel.onNewStudentList(students);
    }

    private List<Student> getStudents() {
        return mainPanel.getTableModel().getStudents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        JFileChooser fileChooser =
                new JFileChooser("D:\\Projects\\IdeaProjects\\UP_2017_11");
        int result = fileChooser.showDialog(frame, "OK");
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        if (source == openXmlWithSAXParserItem) {
            File file = fileChooser.getSelectedFile();
            SAXParser saxParser = new SAXParser();
            try {
                saxParser.parse(file);
//                onNewStudentList(saxParser.getStudents());
                onNewStudentList(new ArrayList<>());
                int numberOfStudents = saxParser.getNumberOfStudents();
                int averageAge = saxParser.getAverageStudentAge();
                int numberOfBsuStudents = saxParser.getNumberOfBsustudents();
                showMessage(String.format("Number of students = %d, " +
                        "number of BSU students = %d, average age = %d",
                        numberOfStudents, numberOfBsuStudents, averageAge));
            } catch (ParserConfigurationException
                    | SAXException
                    | IOException e1) {
                showMessage("Couldn't open XML file with SAX");

            }
        } else if (source == openXmlWithDOMParserItem) {
            File file = fileChooser.getSelectedFile();
            DOMParser domParser = new DOMParser();
            try {
                domParser.parse(file);
                onNewStudentList(domParser.getStudents());
            } catch (ParserConfigurationException
                    | SAXException
                    | IOException e1) {
                if (e1.getMessage() != null && e1.getMessage().equals(DOMParser.VALIDATION_ERROR_MESSAGE)) {
                    showMessage("Validation error");
                } else {
                    showMessage("Couldn't open XML file with DOM");
                }
            }
        } else if (source == openBinaryFileItem) {
            File file = fileChooser.getSelectedFile();
            SerializationManager manager = new SerializationManager();
            try {
                List<Student> list = manager.deserializeFromFile(file.getAbsolutePath());
                onNewStudentList(list);
            } catch (IOException | ClassNotFoundException e1) {
                showMessage("Couldn't open binary file");
            }
        } else if (source == saveAsXMLFileItem) {
            String fileName = fileChooser.getSelectedFile().getPath() + ".xml";
            SaveXmlManager.saveAsXml(getStudents(), fileName);
        } else if (source == saveAsBinaryFileItem) {
            String fileName = fileChooser.getSelectedFile().getPath();
            SerializationManager manager = new SerializationManager();
            try {
                manager.serializeToFile(getStudents(), fileName);
            } catch (IOException e1) {
                showMessage("Couldn't save to binary file");
            }
        } else if (source == saveAsTxtFileItem) {
            String fileName = fileChooser.getSelectedFile().getPath();
            String xmlFileName = Integer.toString(Math.abs(random.nextInt()));
            SaveXmlManager.saveAsXml(getStudents(), xmlFileName);
            try {
                XmlTransformer.toTxt(xmlFileName, fileName);
            } catch (TransformerException e1) {
                showMessage("Couldn't save to txt");
            }
        } else if (source == saveAsHtmlTableItem) {
            String fileName = fileChooser.getSelectedFile().getPath();
            String xmlFileName = Integer.toString(Math.abs(random.nextInt()));
            SaveXmlManager.saveAsXml(getStudents(), xmlFileName);
            try {
                XmlTransformer.toHtml(xmlFileName, fileName);
            } catch (TransformerException e1) {
                showMessage("Couldn't save to html");
            }
        }
    }

    private void showMessage(String text) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(frame, text);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(View::new);
    }
}
