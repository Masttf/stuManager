package fun.masttf.stuManager.repository;

import fun.masttf.stuManager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Student findById(long id);
    Student findByName(String name);
    @Query(value = "select * from student order by gpa desc", nativeQuery = true)
    List<Student> sortByGpa();
}
