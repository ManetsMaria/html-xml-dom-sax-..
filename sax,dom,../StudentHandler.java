package main;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NotePad.by on 21.05.2017.
 */
public class StudentHandler extends DefaultHandler {
    private SAXParser saxParser;
    private Student currentStudent;
    boolean bUniversity = false;
    boolean bRecordBook = false;

    public StudentHandler(SAXParser saxParser) {
        this.saxParser = saxParser;
    }


    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("student")) {
            currentStudent = new Student();
            currentStudent.age = Integer.parseInt(attributes.getValue("age"));
            currentStudent.surname = attributes.getValue("surname");
        } else if (qName.equals("university")) {
            bUniversity = true;
        } else if (qName.equals("recordBook")) {
            bRecordBook = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equals("student")) {
            saxParser.onNewStudentParsed(currentStudent);
        }
    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        if (bUniversity) {
            currentStudent.university = new String(ch, start, length);
            bUniversity = false;
        } else if (bRecordBook) {
            currentStudent.recordBook = new String(ch, start, length);
            bRecordBook = false;
        }
    }
}