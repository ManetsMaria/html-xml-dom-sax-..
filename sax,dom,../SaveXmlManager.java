package main;

import main.Student;
import main.StudentContainer;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.util.List;


/**
 * Created by NotePad.by on 21.05.2017.
 */
public class SaveXmlManager {
    public static void saveAsXml(List<Student> students, String fileName) {
        File file = new File(fileName);
        try {
            JAXBContext context = JAXBContext.newInstance(StudentContainer.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new StudentContainer(students), file);
        } catch ( JAXBException exception) {
            exception.printStackTrace();
        }
    }
}
