package fun.masttf.stuManager.service;

import fun.masttf.stuManager.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getStudentList();
    public Student findStudentById(long id);
    public Student updateStudentGPA(long id, double gpa);

    Student findStudentByName(String name);

    public Student add(Student student);
    public void deleteById(long id);
    public Student update(Student student);
    public List<Student> getTopStudents(int topN);
}
