package main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="students")
public class StudentContainer {
    private List<Student> students;

    public StudentContainer() {
    }

    public StudentContainer(List<Student> students) {
        this.students = students;
    }

    @XmlElement(name="student")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
