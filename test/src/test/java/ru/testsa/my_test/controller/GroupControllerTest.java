package ru.testsa.my_test.controller;

import ru.testsa.my_test.TestApplication;
import ru.testsa.my_test.dto.request.GroupDto;
import ru.testsa.my_test.model.Group;
import ru.testsa.my_test.repository.GroupRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * <b>Мокаем добавление групп в бд для базы данных</b>
     */
    @BeforeEach
    void setUp() {
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Group group = Group.builder()
                    .name(String.valueOf(i))
                    .creationDate(LocalDateTime.now())
                    .build();
            groupList.add(group);
        }
        groupRepository.saveAll(groupList);
    }

    @Test
    void getGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/group/getGroups")
                .param("page", "1")
                .param("size", "2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(mvcResult -> {
                    MockHttpServletResponse response = mvcResult.getResponse();
                    String contentAsString = response.getContentAsString();
                    List<GroupDto> groupDtoList = objectMapper.readValue(contentAsString, new TypeReference<>() {
                    });
                    Assertions.assertEquals(1, groupDtoList.size());
                });

    }

    @Test
    void addGroup() throws Exception {
        //given
        Group buildToGroup = Group.builder()
                .name("AddGroup")
                .build();
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/group/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildToGroup)));
        //then
        List<Group> all = StreamSupport
                .stream(groupRepository
                        .findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assertions.assertEquals(4, all.size());
        Assertions.assertTrue(all.stream()
                .anyMatch(group -> group.getName().equals(buildToGroup.getName())));
    }
}