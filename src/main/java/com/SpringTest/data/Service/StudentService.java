package com.SpringTest.data.Service;


import com.SpringTest.data.Entity.StudentDetails;
import com.SpringTest.data.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public StudentDetails saveStudentDetails(StudentDetails studentDetails) {
        return studentRepo.save(studentDetails);
    }

    public Optional<StudentDetails> fetchStudentDetails(int primaryKey) {
        return studentRepo.findById(primaryKey);
    }

    public StudentDetails updateStudentDetails(String name, int rollNumber) {
        StudentDetails studentDetails = studentRepo.getOne(rollNumber);
        studentDetails.setName(name);
        return studentRepo.save(studentDetails);
    }

    public StudentDetails updateStudentDetails(int age, int rollNumber) {
        StudentDetails studentDetail = studentRepo.getOne(rollNumber);
        studentDetail.setAge(age);
        return studentRepo.save(studentDetail);
    }

    public StudentDetails updateStudentDetails(int rollNumber, String phoneNumber) {
        StudentDetails studentDetail = studentRepo.getOne(rollNumber);
        studentDetail.setPhone(phoneNumber);
        return studentRepo.save(studentDetail);
    }

    public StudentDetails updateStudentDetails(StudentDetails studentDetails, int id) {
        StudentDetails studentDetail = studentRepo.getOne(id);
        studentDetail.setName(studentDetails.getName());
        studentDetail.setPhone(studentDetails.getPhone());
        studentDetail.setAge(studentDetails.getAge());
        return studentRepo.save(studentDetail);
    }

    public List<StudentDetails> fetchStudentDetails() {
        return studentRepo.findAll();
    }

    public boolean deleteStudentDetails(int id) {
        long count = studentRepo.count();
        studentRepo.deleteById(id);
        return count > studentRepo.count();
    }
}
