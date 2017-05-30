package main;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.Transform;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Demo {
    static void DOMtry() {
        File inputFile = new File("input.xml");
        DOMParser parser = new DOMParser();
        try {
            parser.parse(inputFile);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        List<Student> students = parser.getStudents();
        students.forEach(System.out::println);
    }

    static void serializationTest() throws IOException, ClassNotFoundException {
        Student student = new Student(18, "Merkis",
                "BSU", "1523175");
        Student student1 = new Student(19, "Tkachev",
                "BSU", "1522222");
        Student student2 = new Student(19, "Shlyahtovich",
                "BSUIR", "1111111");
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);
        SerializationManager manager = new SerializationManager();
        manager.serializeToFile(list, "output1");
        List<Student> students = manager.deserializeFromFile("output1");
        boolean ok = students.equals(list);
        if (!ok)
            throw new RuntimeException("Test not passed");
    }

    static void saxParserTest() throws IOException, SAXException, ParserConfigurationException {
        File inputFile = new File("input.xml");
        SAXParser saxParser = new SAXParser();
        saxParser.parse(inputFile);

        System.out.printf("Average student age is %d\n", saxParser.getAverageStudentAge());
        System.out.printf("Number of students is %d\n", saxParser.getNumberOfStudents());
    }

    public static void main(String[] args) {
        try {
            serializationTest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            saxParserTest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        String xslFileName = "toHtmlTable.xsl";
        File styleSheet = new File(xslFileName);
        StreamSource styleSource = new StreamSource(styleSheet);
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer(styleSource);
            t.transform(new StreamSource(new File("another.xml")), new StreamResult("index.html"));
        } catch (TransformerConfigurationException e) {

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
