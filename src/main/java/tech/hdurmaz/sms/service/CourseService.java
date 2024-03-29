package tech.hdurmaz.sms.service;

import tech.hdurmaz.sms.dto.CourseDTO;
import tech.hdurmaz.sms.entity.Course;
import tech.hdurmaz.sms.exception.CourseIsAlreadyExistException;
import tech.hdurmaz.sms.exception.CourseIsNotExistException;
import tech.hdurmaz.sms.mappers.CourseMapper;
import tech.hdurmaz.sms.repository.CourseRepository;
import tech.hdurmaz.sms.utils.ErrorMessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired(required = false)
    private CourseMapper courseMapper;

    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    /**
     * @param id
     * @return list of courses saved database
     */
    public Course findById(long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseIsNotExistException(ErrorMessageConstants.COURSE_IS_NOT_EXIST));
    }

    /**
     * gets the course dto from controller
     * use mapper to map the course from course dto
     * saving the course using repository
     *
     * @param courseDTO
     * @return
     */
    @Transactional
    public Optional<Course> save(CourseDTO courseDTO) {
        if (this.isCourseExistOnDatabase(courseDTO.getCourseCode())) {
            throw new CourseIsAlreadyExistException(ErrorMessageConstants.COURSE_IS_ALREADY_EXIST);
        } else {
            Course course = new Course();
            courseMapper.updateCourseFromDto(courseDTO, course);
            return Optional.of(courseRepository.save(course));
        }
    }

    /**
     * this method for saving courses on bootstrap
     *
     * @param course
     * @return
     */
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    /**
     * @param id delete course by id
     */
    public void deleteById(long id) {
        try {
            courseRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CourseIsNotExistException(ErrorMessageConstants.COURSE_IS_NOT_EXIST);
        }
    }

    /**
     * @param courseDTO method for update course saved in database
     */
    @Transactional
    public void update(CourseDTO courseDTO) {
        if (this.isCourseExistOnDatabase(courseDTO.getCourseCode())) {
            Course course = courseRepository.findById(courseDTO.getId())
                    .orElseThrow(() -> new CourseIsNotExistException(ErrorMessageConstants.COURSE_IS_NOT_EXIST));
            courseMapper.updateCourseFromDto(courseDTO, course);
            courseRepository.save(course);
        } else {
            throw new CourseIsNotExistException(ErrorMessageConstants.COURSE_IS_NOT_EXIST);
        }
    }

    /**
     * this method for controlling the course code in database
     *
     * @param courseCode
     * @return if course is exist return true else false
     */
    private boolean isCourseExistOnDatabase(String courseCode) {
        Course course = courseRepository.findCourseByCourseCode(courseCode);
        return course != null;
    }
}





