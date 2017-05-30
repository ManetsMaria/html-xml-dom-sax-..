package main;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


@XmlRootElement(name="student")
public class Student implements Comparable<Student>, Serializable {
    @XmlAttribute
    public String surname;

    @XmlAttribute
    public int age;

    public String university;

    public String recordBook;

    public Student() {}

    public Student(int age, String surname, String university, String recordBook) {
        this.age = age;
        this.surname = surname;
        this.university = university;
        this.recordBook = recordBook;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (age != student.age) return false;
        if (surname != null ? !surname.equals(student.surname) : student.surname != null) return false;
        if (university != null ? !university.equals(student.university) : student.university != null) return false;
        return recordBook != null ? recordBook.equals(student.recordBook) : student.recordBook == null;
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (university != null ? university.hashCode() : 0);
        result = 31 * result + (recordBook != null ? recordBook.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Student o) {
        if (surname.equals(o.surname)) {
            return recordBook.compareTo(o.recordBook);
        } else {
            return surname.compareTo(o.surname);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", surname='" + surname + '\'' +
                ", university='" + university + '\'' +
                ", recordBook='" + recordBook + '\'' +
                '}';
    }
}
