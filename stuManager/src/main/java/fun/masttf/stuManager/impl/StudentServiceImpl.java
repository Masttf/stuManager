package fun.masttf.stuManager.impl;

import fun.masttf.stuManager.entity.Student;
import fun.masttf.stuManager.repository.StudentRepository;
import fun.masttf.stuManager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    @Override
    public Student findStudentById(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student findStudentByName(String name) {
        return studentRepository.findByName(name);
    }
    @Override
    public Student add(Student student){
        studentRepository.save(student);
        return studentRepository.findById(student.getId());
    }
    @Override
    @Transactional
    public Student update(Student student){
        studentRepository.save(student);
        return studentRepository.findById(student.getId());
    }
    @Override
    public void deleteById(long id){
        studentRepository.deleteById(id);
    }
    @Override
    @Transactional
    public Student updateStudentGPA(long id, double gpa) {
        Student student = findStudentById(id);
        student.setGpa(gpa);
        studentRepository.save(student);

        return student;
    }

    public List<Student> getTopStudents(int topN) {
        List<Student> s = studentRepository.sortByGpa();
        if(s.size() < topN){
            return s;
        }else {
            return s.subList(0, topN);
        }
    }
}
