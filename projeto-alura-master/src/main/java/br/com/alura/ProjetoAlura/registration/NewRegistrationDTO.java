package br.com.alura.ProjetoAlura.registration;

import br.com.alura.ProjetoAlura.course.Course;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static br.com.alura.ProjetoAlura.course.Status.ACTIVE;

public class NewRegistrationDTO {

    @NotBlank
    @NotNull
    @Email
    private String studentEmail;

    @NotBlank
    @NotNull
    private String courseCode;

    public NewRegistrationDTO() {}

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Registration toModel() {
        return new Registration(studentEmail, courseCode);
    }

}
