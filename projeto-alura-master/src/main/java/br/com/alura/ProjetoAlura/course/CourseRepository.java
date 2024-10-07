package br.com.alura.ProjetoAlura.course;


import br.com.alura.ProjetoAlura.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCode(String code);

}
