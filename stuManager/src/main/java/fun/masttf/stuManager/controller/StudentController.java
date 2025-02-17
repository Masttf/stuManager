package fun.masttf.stuManager.controller;

import fun.masttf.stuManager.entity.Student;
import fun.masttf.stuManager.result.ExceptionMsg;
import fun.masttf.stuManager.result.ResponseData;
import fun.masttf.stuManager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseData getStudentList() {
        List<Student> list = new ArrayList<Student>(studentService.getStudentList());
        return new ResponseData(ExceptionMsg.SUCCESS,list);
    }

    //增
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseData add(@RequestBody Student student) {
        return new ResponseData(ExceptionMsg.SUCCESS, studentService.add(student));
    }


    //删
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseData delete(@PathVariable("id") Long id) {
        Student s = studentService.findStudentById(id);
        if (s != null) {
            studentService.deleteById(id);
            return new ResponseData(ExceptionMsg.SUCCESS);
        }else{
            return new ResponseData(ExceptionMsg.FAILED, "not find student");
        }
    }

    //改
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseData update(@PathVariable("id") Long id, @RequestBody Student student) {
        Student s = studentService.findStudentById(id);
        if (s != null) {
            student.setId(id);
            return new ResponseData(ExceptionMsg.SUCCESS,studentService.update(student));
        }else{
            return new ResponseData(ExceptionMsg.FAILED, "not find student");
        }
    }

    //查
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData findStudent(@PathVariable("id") Long id){
        Student student = studentService.findStudentById(id);
        if (student != null) {
            return new ResponseData(ExceptionMsg.SUCCESS, student);
        }
        return new ResponseData(ExceptionMsg.FAILED, "not find student");
    }

    @RequestMapping (value = "/updateGPA/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseData updateGPA(@PathVariable("id") long id, @RequestParam double gpa) {
        Student s = studentService.findStudentById(id);
        if (s != null) {
            return new ResponseData(ExceptionMsg.SUCCESS, studentService.updateStudentGPA(id, gpa));
        }else{
            return new ResponseData(ExceptionMsg.FAILED, "not find student");
        }
    }

    // 获取绩点排名前N名
    @RequestMapping (value = "/topGPA/{topN}", method = RequestMethod.GET)
    public ResponseData getTopStudents(@PathVariable("topN") int topN) {
        List<Student> topStudents = studentService.getTopStudents(topN);
        return new ResponseData(ExceptionMsg.SUCCESS, topStudents);
    }
}
