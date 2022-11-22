package com.example.producingwebservice.DAO;

import com.example.producingwebservice.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}