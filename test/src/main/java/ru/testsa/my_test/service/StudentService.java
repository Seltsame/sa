package ru.testsa.my_test.service;

import ru.testsa.my_test.dto.StudentResponseDto;
import ru.testsa.my_test.dto.request.StudentDto;

import java.util.List;

public interface StudentService {
    void addStudent(StudentDto studentDto);

    List<StudentResponseDto> getStudentByGroupId(Long id);
}
