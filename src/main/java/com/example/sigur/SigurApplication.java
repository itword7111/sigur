package com.example.sigur;

import com.example.sigur.entity.Department;
import com.example.sigur.repository.DepartmentRepository;
import com.example.sigur.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SigurApplication {
    @Autowired
    DepartmentRepository departmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(SigurApplication.class, args);
    }

    @PostConstruct
    private void init() {
        //int numberOfDepartments = departmentRepository.getNumberOfDepartments();
//        for(int i =0;i<10;i++){
//            departmentRepository.addDepartment(new Department());
//        }
        Long departmentsCount=departmentRepository.getNumberOfDepartments();
        while (departmentsCount<10){
            departmentsCount++;
            departmentRepository.addDepartment(new Department("department"+departmentsCount));
        }
    }
}
