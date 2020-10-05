package ru.testsa.my_test.service;

import ru.testsa.my_test.dto.request.GroupDto;
import ru.testsa.my_test.dto.GroupResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GroupService {
    /**<b>Метод для добавления группы</b>
     * @param groupDto
     */
    void addGroup(GroupDto groupDto);

    Page<GroupResponseDto> getGroups(Pageable pageable);

    Optional<GroupResponseDto> getGroupNameById(Long id);
}

