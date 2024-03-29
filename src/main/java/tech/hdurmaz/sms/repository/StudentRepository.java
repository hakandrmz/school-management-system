package tech.hdurmaz.sms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.hdurmaz.sms.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {

    @Query("select s.gender, count(s.gender) from Student s GROUP BY s.gender")
    List<?> getGendersWithGrouping();
    void deleteByName(String name);

    @Query("SELECT s FROM Student s WHERE s.name LIKE %?1%"+
            " OR s.address LIKE %?1%")
    List<Student> search(String word);
    List<Student> findStudentsByCourses_CourseCode(String courseCode);

}
