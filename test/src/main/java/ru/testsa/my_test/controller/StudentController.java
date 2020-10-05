package ru.testsa.my_test.controller;

import ru.testsa.my_test.dto.request.StudentDto;
import ru.testsa.my_test.service.GroupService;
import ru.testsa.my_test.service.StudentService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "student")
public class StudentController {

    private final StudentService studentService;
    private final Configuration configuration;
    private final GroupService groupService;


    public StudentController(StudentService studentService, Configuration configuration, GroupService groupService) {
        this.studentService = studentService;
        this.configuration = configuration;
        this.groupService = groupService;
    }

    @PostMapping(path = "/add")
    public void addStudent(@RequestParam String studentName, @RequestParam Long groupId) {
        StudentDto studentDto = StudentDto.builder()
                .studentName(studentName)
                .groupId(groupId)
                .build();
        studentService.addStudent(studentDto);
    }

    @GetMapping(path = "/getStudentByGroupId/{groupId}", produces = MediaType.TEXT_HTML_VALUE)
    public void getStudentByGroupId(@PathVariable Long groupId, HttpServletResponse response) {
        Template template = null;
        ModelMap modelMap = new ModelMap();
        groupService.getGroupNameById(groupId).ifPresent(groupResponseDto -> modelMap.addAttribute("groupName", groupResponseDto.getGroupName()));
        modelMap.addAttribute("studentList", studentService.getStudentByGroupId(groupId));
        modelMap.addAttribute("groupId", groupId);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            template = configuration.getTemplate("students.ftl");
            template.process(modelMap, response.getWriter());
        } catch (IOException | TemplateException e) {

        }
    }
}
