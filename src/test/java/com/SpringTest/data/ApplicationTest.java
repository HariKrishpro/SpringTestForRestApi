package com.SpringTest.data;


import com.SpringTest.data.Controller.StudentController;
import com.SpringTest.data.Entity.StudentDetails;
import com.SpringTest.data.Repository.StudentRepo;
import com.SpringTest.data.Response.EndResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTest {

    private final StudentDetails studentDetails = StudentDetails.builder()
            .age(20)
            .name("Harikrish")
            .phone("6383405119")
            .rollNumber(2)
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Autowired
    private StudentController studentController;
    @Autowired
    private WebApplicationContext applicationContext;
    @MockBean
    private StudentRepo studentRepo;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void testFetchStudentDetails() throws Exception {
        when(studentRepo.findById(2)).thenReturn(Optional.of(studentDetails));
        MvcResult mockResult = mockMvc.perform(MockMvcRequestBuilders.get("/fetchStudent/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(studentRepo).findById(2);
        log.info(mockResult.getResponse().getContentAsString());
    }

    @Test
    public void testFetchStudentDetailsAll() throws Exception {
        when(studentRepo.findAll()).thenReturn(Arrays.asList(studentDetails));
        MvcResult mvcResult = mockMvc.perform(
                get("/fetchStudentAll")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testSaveStudentDetails() throws Exception {
        when(studentRepo.save(Mockito.any(StudentDetails.class)))
                .thenReturn(studentDetails);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/saveStudent")
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(studentDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(studentRepo).save(Mockito.any(StudentDetails.class));
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateStudentDetailsWithName() throws Exception {
        StudentDetails updated = StudentDetails.builder()
                .age(20)
                .name("Hariharan")
                .phone("6383405119")
                .rollNumber(2)
                .build();
        when(studentRepo.getOne(2)).thenReturn(studentDetails);
        when(studentRepo.save(updated)).thenReturn(updated);
        MvcResult mvcResult = mockMvc.perform(put("/updateStudent/2")
                .queryParam("name", updated.getName())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateStudentDetailsWithAge() throws Exception {
        StudentDetails updated = StudentDetails.builder()
                .age(22)
                .name("Harikrish")
                .phone("6383405119")
                .rollNumber(2)
                .build();
        when(studentRepo.getOne(2)).thenReturn(studentDetails);
        when(studentRepo.save(updated)).thenReturn(updated);
        MvcResult mvcResult = mockMvc.perform(
                put("/updateStudent/2")
                        .queryParam("age", "22")
                        .accept(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk()).andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateStudentDetailsWithPhone() throws Exception {
        StudentDetails updated = StudentDetails.builder()
                .age(20)
                .name("Harikrish")
                .phone("6383200746")
                .rollNumber(2)
                .build();
        when(studentRepo.getOne(2)).thenReturn(studentDetails);
        when(studentRepo.save(updated)).thenReturn(updated);
        MvcResult mvcResult = mockMvc.perform(put("/updateStudent/2")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("phoneNumber", updated.getPhone())
        ).andExpect(status().isOk()).andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
        EndResponse endResponse = (EndResponse) fromJson(mvcResult.getResponse().getContentAsString(), EndResponse.class);

        Assert.assertTrue(endResponse.getOutPut().toString()
                .equals(updated.toString()));
        verify(studentRepo).getOne(2);
        verify(studentRepo).save(updated);
    }

    @Test
    public void testUpdateStudentDetailsWithStudentDetails() throws Exception {
        when(studentRepo.getOne(2)).thenReturn(studentDetails);
        StudentDetails updated = StudentDetails.builder()
                .age(21)
                .name("Harikrishnan")
                .phone("6383200746")
                .rollNumber(2)
                .build();
        when(studentRepo.save(updated)).thenReturn(updated);
        MvcResult mvcResult = mockMvc.perform(
                put("/updateStudent/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(updated))
        ).andExpect(status().isOk()).andReturn();

        EndResponse endResponse = (EndResponse) fromJson(mvcResult.getResponse().getContentAsString(), EndResponse.class);
        Assert.assertTrue(endResponse.getOutPut().toString()
                .equals(updated.toString()));
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteStudentDetails() throws Exception {
        when(studentRepo.count())
                .thenReturn(2L,1L,2L,2L);

        doNothing().when(studentRepo).deleteById(studentDetails.getRollNumber());
        MvcResult mvcResult = mockMvc.perform(
                delete("/deleteStudent/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        MvcResult mvcResult1 = mockMvc.perform(
                delete("/deleteStudent/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        EndResponse positive = (EndResponse) fromJson(mvcResult.getResponse().getContentAsString(),EndResponse.class);
        EndResponse negative = (EndResponse) fromJson(mvcResult1.getResponse().getContentAsString(),EndResponse.class);
        Assert.assertTrue(positive.getOutPut().toString()
                .equals("Hey, Your deletion is successfully Done!!"));
        Assert.assertTrue(negative.getOutPut().toString()
                .equals("Sorry, Deletion is not Successful ,It may be due to unPresence of Data"));

        log.info(positive.getOutPut().toString());
        log.info(negative.getOutPut().toString());
    }


    private Object fromJson(String json, Class c) throws JsonProcessingException {
        Object endResponse = objectMapper.readValue(json, c);
        return endResponse;

    }

    private String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
















