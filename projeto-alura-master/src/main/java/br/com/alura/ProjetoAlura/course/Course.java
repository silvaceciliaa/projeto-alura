package br.com.alura.ProjetoAlura.course;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_course;

    private String name;

    private String code;

    private String instructorEmail;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime inactivatedAt;

    @Deprecated
    public Course() {}

    public Course(String name, String code, String instructorEmail, Status status, String description){
        this.name = name;
        this.code = code;
        this.instructorEmail = instructorEmail;
        this.status = status;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getInstructorEmail(){
        return instructorEmail;
    }

    public String getCode(){
        return code;
    }

    public Status getStatus(){
        return status;
    }

    public String getDescription(){
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getInactivatedAt(){
        return inactivatedAt;
    }
}
