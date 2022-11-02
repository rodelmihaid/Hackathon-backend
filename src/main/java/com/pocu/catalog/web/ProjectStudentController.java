package com.pocu.catalog.web;

import com.pocu.catalog.converter.ProjectStudentConverter;
import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.entity.ProjectStudentEntity;
import com.pocu.catalog.entity.User;
import com.pocu.catalog.repository.ProjectRepository;
import com.pocu.catalog.repository.UserRepository;
import com.pocu.catalog.service.ProjectStudentService;
import com.pocu.catalog.web.dto.ProjectStudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/project-student")
public class ProjectStudentController {
    private final ProjectStudentService projectStudentService;
    private final ProjectStudentConverter projectStudentConverter;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectStudentController(ProjectStudentService projectStudentService, ProjectStudentConverter projectStudentConverter, UserRepository userRepository, ProjectRepository projectRepository) {
        this.projectStudentService = projectStudentService;
        this.projectStudentConverter = projectStudentConverter;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    private Logger logger = (Logger) LoggerFactory.getLogger(ProjectStudentController.class);

    @PostMapping(value = "")
    public ProjectStudentDto saveProjectStudent(@Valid @RequestBody ProjectStudentDto projectStudentDto) {
        logger.debug("Save projectstudent {}", projectStudentDto.toString());
        User studentEntity = userRepository.findUserByEmail(projectStudentDto.getUserEmail()).get(0);
        ProjectEntity projectEntity = projectRepository.findById(projectStudentDto.getProjectId()).get();
        ProjectStudentEntity projectStudentEntity = projectStudentConverter.fromDtoToEntity(projectStudentDto);
        projectStudentEntity.setStudent(studentEntity);
        projectStudentEntity.setProject(projectEntity);
        ProjectStudentEntity savedProjectStudentEntity = projectStudentService.saveProjectStudent(projectStudentEntity);
        logger.debug("projectStudentEntity saved with id {}", projectStudentEntity.getId());
        return projectStudentConverter.fromEntityToDto(projectStudentEntity);
    }

    @GetMapping(value = "")
    public List<ProjectStudentDto> getProjectStudents() {
        logger.debug("Get all projectStudent");
        return projectStudentConverter.fromEntitiesToDtos(projectStudentService.getProjectStudents());
    }

    @GetMapping(value = "/project/{id}")
    public List<ProjectStudentDto> getProjectStudentsByProjects(@PathVariable Long id) {
        logger.debug("Get all projectStudent by project id");
        List<ProjectStudentEntity> projectStudentEntities = projectStudentService.getProjectStudentsByProjectId(id);
        return projectStudentConverter.fromEntitiesToDtos(projectStudentEntities);
    }

    @GetMapping(value = "/project/{id}/user/{userEmail}")
    public ProjectStudentDto getProjectStudentsByProjects(@PathVariable(name = "id") Long id, @PathVariable(name = "userEmail") String userEmail) {
        logger.debug("Get all projectStudent by project id");
        ProjectStudentEntity projectStudentEntities = projectStudentService.getProjectStudentsByProjectIdAndUserEmail(id, userEmail);
        if (projectStudentEntities == null) {
            return null;
        }
        return projectStudentConverter.fromEntityToDto(projectStudentEntities);
    }

}
