package ru.testsa.my_test.repository;

import ru.testsa.my_test.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> getAllByGroupName(String groupName);

    List<Student> findAllByGroupId(Long id);
}
