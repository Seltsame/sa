package ru.testsa.my_test.controller;

import ru.testsa.my_test.TestApplication;
import ru.testsa.my_test.dto.request.StudentDto;
import ru.testsa.my_test.model.Group;
import ru.testsa.my_test.model.Student;
import ru.testsa.my_test.repository.GroupRepository;
import ru.testsa.my_test.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    private ObjectMapper objectMapper;
    Group group;

    @BeforeEach
    void setUp() {
        group = Group.builder()
                .name("test")
                .build();
        group = groupRepository.save(group);

    }

    @Test
    void addStudent() throws Exception {
        StudentDto test = StudentDto.builder()
                .studentName("test")
                .groupId(group.getId())
                .build();
        mockMvc.perform(post("/student/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(test)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        List<Student> all = StreamSupport.stream(studentRepository
                .findAll()
                .spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(1, all.size());
        Student student = all.get(0);
        Assertions.assertEquals(group.getId(), student.getGroup().getId());
        Assertions.assertEquals(test.getStudentName(), student.getName());
    }
}