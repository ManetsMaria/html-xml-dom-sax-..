package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DOMParser {
    public static final String VALIDATION_ERROR_MESSAGE = "Couldn't pass validation";
    private List<Student> students;

    public void parse(File inputFile) throws ParserConfigurationException, IOException, SAXException {
        Document doc = xmlToDocument(inputFile);
        doc.getDocumentElement().normalize();
        students = parseStudents(doc);
    }

    public List<Student> getStudents() {
        return students;
    }

    private static Document xmlToDocument(File file) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("schema.xsd"));
        dbFactory.setSchema(schema);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        dBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                throw new SAXException(VALIDATION_ERROR_MESSAGE);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                throw new SAXException("Invalid xml structure");
            }
        });
        return dBuilder.parse(file);
    }

    private static List<Student> parseStudents(Document doc) {
        NodeList studentNodeList = doc.getElementsByTagName("student");
        List<Student> students = new ArrayList<>(studentNodeList.getLength());
        for (int i = 0; i < studentNodeList.getLength(); i++) {
            Node studentNode = studentNodeList.item(i);
            students.add(parseStudentNode(studentNode));
        }
        return students;
    }

    private static Student parseStudentNode(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE)
            throw new RuntimeException("Wrong xml format");
        Element element = (Element) node;
        String surname = element.getAttribute("surname");
        int age = Integer.parseInt(element.getAttribute("age"));
        String university = element.getElementsByTagName("university")
                .item(0).getTextContent();
        String recordBook = element.getElementsByTagName("recordBook")
                .item(0).getTextContent();
        return new Student(age, surname, university, recordBook);
    }

    private boolean isValid(File file) {
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source xmlFile = new StreamSource(file);
        try {
            Schema schema = schemaFactory.newSchema(new File("schema.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            return true;
        } catch (SAXException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
