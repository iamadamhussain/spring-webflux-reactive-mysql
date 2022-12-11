package com.adam.reactivedemo.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.adam.reactivedemo.model.Students;
import com.adam.reactivedemo.repository.CourseWorkRepository;
import com.adam.reactivedemo.repository.StudentsRepository;

import reactor.core.publisher.Mono;
@Component
public class StudentHandler {
	@Autowired
    private  StudentsRepository studentsRepository;
	@Autowired
    private  CourseWorkRepository courseWorkRepository;
	
	
	
	static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getAllStudents(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentsRepository.findAll(), Students.class);

    }

    public Mono<ServerResponse> getOneStudent(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<Students> studentMono = studentsRepository.findById(Long.valueOf(id));

        return studentMono.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(item)))
                .switchIfEmpty(notFound);

    }

    public Mono<ServerResponse> saveStudent(ServerRequest serverRequest) {

        Mono<Students> studentTobeInserted = serverRequest.bodyToMono(Students.class);
       
        return studentTobeInserted.flatMap(student ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentsRepository.save(student), Students.class));

    }

    public Mono<ServerResponse> deleteStudent(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<Void> deleteStudent = studentsRepository.deleteById(Long.valueOf(id));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deleteStudent, Void.class);
    }

    public Mono<ServerResponse> updateStudent(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");

        Mono<Object> updatedStudent = serverRequest.bodyToMono(Students.class)
                .flatMap((student) -> {

                    Mono<Object> studentMono = studentsRepository.findById(Long.valueOf(id))

                            .flatMap(currentstudent-> {
                                currentstudent.setName(student.getName());
                              
                                return studentsRepository.save(currentstudent);

                            });
                    return studentMono;
                });

        return updatedStudent.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(item)))
                .switchIfEmpty(notFound);


    }

   

    public Mono<ServerResponse> studentsEx(ServerRequest serverRequest){

        throw new RuntimeException("RuntimeException Occurred");
    }

}
