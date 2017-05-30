package main;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NotePad.by on 21.05.2017.
 */
public class SAXParser {
//    private List<Student> students = new ArrayList<>();
    private int sumOfAge;
    private int numberOfStudents;
    private int numberOfBsustudents;

    public void parse(File inputFile) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
        StudentHandler studentHandler = new StudentHandler(this);
        saxParser.parse(inputFile, studentHandler);
    }

//    public List<Student> getStudents() {
//        return students;
//    }


    public int getNumberOfBsustudents() {
        return numberOfBsustudents;
    }

    public int getAverageStudentAge() {
        return sumOfAge / numberOfStudents;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void onNewStudentParsed(Student student) {
//        students.add(student);
        makeStudentRecalc(student);
    }

    private void makeStudentRecalc(Student student) {
        sumOfAge += student.age;
        numberOfStudents++;
        if (student.university.equalsIgnoreCase("bsu"))
            numberOfBsustudents++;
    }
}
