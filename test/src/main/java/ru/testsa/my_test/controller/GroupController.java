package ru.testsa.my_test.controller;

import ru.testsa.my_test.dto.request.GroupDto;
import ru.testsa.my_test.service.GroupService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController()
@RequestMapping(path = "/group")
@Slf4j
public class GroupController {


    private final GroupService groupService;
    private final Configuration configuration;

    public GroupController(GroupService groupService, Configuration configuration) {
        this.groupService = groupService;
        this.configuration = configuration;
    }

    @PostMapping(path = "/add", produces = MediaType.TEXT_HTML_VALUE)
    public void addGroup(@RequestParam String groupName, HttpServletResponse response) throws IOException {
        GroupDto groupDto = GroupDto.builder()
                .groupName(groupName)
                .build();
        groupService.addGroup(groupDto);
        response.sendRedirect("/group/getGroups");
    }

    @GetMapping(path = "/getGroups", produces = MediaType.TEXT_HTML_VALUE)
    public void getGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, HttpServletResponse response) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "creationDate"));
        Template template = null;
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("groupList", groupService.getGroups(pageable).getContent());
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            template = configuration.getTemplate("groups.ftl");
            template.process(modelMap, response.getWriter());
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
        }
    }
}
