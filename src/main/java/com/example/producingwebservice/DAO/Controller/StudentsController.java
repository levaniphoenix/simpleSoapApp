package com.example.producingwebservice.DAO.Controller;

import com.example.producingwebservice.DAO.StudentRepository;
import com.example.producingwebservice.Models.Student;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentsController {

    @Autowired
    private StudentRepository studentRepo;


    @RequestMapping("/students/all")
    public List<Student> getStudents(){
        return studentRepo.findAll();
    }

    @RequestMapping("/students/add")
    public Student addStudent(){
        Faker faker = new Faker();
        Student student = new Student();
        student.setName(faker.funnyName().name());
        student.setEmail(faker.harryPotter().house().toString());
        return studentRepo.save(student);
    }
}
