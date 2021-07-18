package com.SpringTest.data.Controller;


import com.SpringTest.data.Entity.StudentDetails;
import com.SpringTest.data.Response.EndResponse;
import com.SpringTest.data.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private EndResponse endResponse;

    @PostMapping("/saveStudent")
    @ResponseBody
    public EndResponse saveStudentDetails(@RequestBody StudentDetails student) {
        System.out.print(student);
        endResponse.okStatus(studentService.saveStudentDetails(student));
        return endResponse;
    }

    @GetMapping("/fetchStudent/{id}")
    @ResponseBody
    public EndResponse fetchStudentDetails(@PathVariable int id) {
        endResponse.okStatus(studentService.fetchStudentDetails(id).orElse(null));
        return endResponse;
    }

    @PutMapping("/updateStudent/{id}")
    public EndResponse updateStudentDetails(@PathVariable int id,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer age,
                                            @RequestParam(required = false) String phoneNumber,
                                            @RequestBody(required = false) StudentDetails student) {

        StudentDetails studentDetails;
        if (student != null) {
            studentDetails = studentService.updateStudentDetails(student, id);
        } else if (name != null)
            studentDetails = studentService.updateStudentDetails(name, id);
        else if (age != null)
            studentDetails = studentService.updateStudentDetails(age, id);
        else
            studentDetails = studentService.updateStudentDetails(id, phoneNumber);

        endResponse.okStatus(studentDetails);
        return endResponse;
    }


    @GetMapping("/fetchStudentAll")
    public EndResponse fetchStudentAll() {
        endResponse.okStatus(studentService.fetchStudentDetails());
        return endResponse;
    }


    @DeleteMapping("/deleteStudent/{id}")
    public EndResponse deleteStudentDetails(@PathVariable int id) {
        if (studentService.deleteStudentDetails(id)) {
            endResponse.okStatus("Hey, Your deletion is successfully Done!!");
            return endResponse;
        }
        endResponse.forbiddenStatus("Sorry, Deletion is not Successful ,It may be due to unPresence of Data");
        return endResponse;
    }

}
