package com.adam.reactivedemo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.adam.reactivedemo.handler.StudentHandler;
import com.adam.reactivedemo.model.Students;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> route(StudentHandler handlerFunction){

        return RouterFunctions
                .route(GET("v1/students").and(accept(APPLICATION_JSON))
                        ,handlerFunction::getAllStudents)
                .andRoute(GET("/v1/students/{id}").and(accept(MediaType.APPLICATION_JSON)),handlerFunction::getOneStudent).
                andRoute(POST("v1/savestudent").and(accept(APPLICATION_JSON))
                        ,handlerFunction::saveStudent).

                andRoute(DELETE("v1/deletestudent/{id}").and(accept(APPLICATION_JSON))
                        ,handlerFunction::deleteStudent)
                .andRoute(PUT("v1/updateStudent/{id}").and(accept(APPLICATION_JSON))
                        ,handlerFunction::updateStudent);

    }


    @Bean
    public RouterFunction<ServerResponse> errorRoute(StudentHandler itemsHandler){
        return RouterFunctions
                .route(GET("/fun/runtimeexception").and(accept(APPLICATION_JSON))
                        ,itemsHandler::studentsEx);

    }

}
