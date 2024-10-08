package br.com.alura.ProjetoAlura.registration;

import br.com.alura.ProjetoAlura.course.Course;
import br.com.alura.ProjetoAlura.course.CourseRepository;
import br.com.alura.ProjetoAlura.course.Status;
import br.com.alura.ProjetoAlura.user.Role;
import br.com.alura.ProjetoAlura.user.User;
import br.com.alura.ProjetoAlura.user.UserRepository;
import br.com.alura.ProjetoAlura.util.ErrorItemDTO;
import br.com.alura.ProjetoAlura.util.SuccessResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public RegistrationController(RegistrationRepository registrationRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/registration/new")
    public ResponseEntity createRegistration(@Valid @RequestBody NewRegistrationDTO newRegistration) {
        if(registrationRepository.existsByCourseCodeAndStudentEmail(newRegistration.getCourseCode(), newRegistration.getStudentEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("email", "Estudante já matriculado no curso"));
        }

        Optional<User> studentEmail = userRepository.findByEmail(newRegistration.getStudentEmail());

        if (studentEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("email", "Instrutor não encontrado com o email fornecido"));
        }

        if (studentEmail.get().getRole() != Role.STUDENT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("email", "O email fornecido não pertence a um aluno"));
        }

        Optional<Course> courseCode = courseRepository.findByCode(newRegistration.getCourseCode());

        if (courseCode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("code", "Curso não encontrado com o código fornecido"));
        }

        if(courseCode.get().getStatus() != Status.ACTIVE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("code", "Curso não está recebendo matrículas no momento"));
        }

        Registration registration = newRegistration.toModel();

        registrationRepository.save(registration);

        SuccessResponseDTO successResponse = new SuccessResponseDTO(
                "Aluno inscrito no curso com sucesso!",
                registration.getCourseCode()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @GetMapping("/registration/report")
    public ResponseEntity<List<RegistrationReportItem>> report() {
        List<RegistrationReportItem> items = new ArrayList<>();

        // TODO: Implementar a Questão 4 - Relatório de Cursos Mais Acessados aqui...

        // Dados fictícios abaixo que devem ser substituídos
        items.add(new RegistrationReportItem(
                "Java para Iniciantes",
                "java",
                "Charles",
                "charles@alura.com.br",
                10L
        ));

        items.add(new RegistrationReportItem(
                "Spring para Iniciantes",
                "spring",
                "Charles",
                "charles@alura.com.br",
                9L
        ));

        items.add(new RegistrationReportItem(
                "Maven para Avançados",
                "maven",
                "Charles",
                "charles@alura.com.br",
                9L
        ));

        return ResponseEntity.ok(items);
    }

}
