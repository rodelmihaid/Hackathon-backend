package com.pocu.catalog.web;

import com.pocu.catalog.converter.ProjectConverter;
import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.entity.ProjectStudentEntity;
import com.pocu.catalog.service.ProjectService;
import com.pocu.catalog.web.dto.ProjectDto;
import com.pocu.catalog.web.dto.ProjectStudentDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ProjectController.class);


    private final ProjectService projectService;
    private final ProjectConverter projectConverter;

    //private Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    public ProjectController(ProjectService projectService, ProjectConverter projectConverter) {
        this.projectService = projectService;
        this.projectConverter = projectConverter;
    }

    @GetMapping(value = "")
    public List<ProjectDto> getProjects(){
        logger.debug("Get all students");
        return projectConverter.fromEntitiesToDtos(projectService.getAllProjects());
    }


    @GetMapping(value = "/{id}")
    public ProjectDto getProject(@PathVariable(name = "id") Long id) {
        logger.debug("Get project by id {}", id);

        return projectConverter.fromEntityToDto(projectService.getProject(id));
    }



    @PostMapping(value = "")
    public void saveProject(@Valid @RequestBody ProjectDto projectDto) {
        logger.debug("Save project {}", projectDto.toString());
        projectService.saveProject(projectConverter.fromDtoToEntity(projectDto), projectDto.getSubjectId());

    }

//    @PostMapping(value = "/{id}/enrollment/{status}/{projectId}")
//    public ProjectDto enrollProjectToSubject(@PathVariable(name = "id") Long id,
//                                             @PathVariable(name = "projectId") Long projectId,
//                                             @PathVariable(name = "status") Boolean status) {
//        logger.debug("Enroll project {} to subject {} status {}", id, projectId, status);
//
//        ProjectEntity projectEntity = projectService.enroll(id, projectId, status);
//
//        return projectConverter.fromEntityToDto(projectEntity);
//    }


    @PutMapping(value = "/{id}")
    public ProjectDto updateProject(@PathVariable(name = "id") Long id,
                                    @Valid @RequestBody ProjectDto projectDto) {
        logger.debug("Updating project with id {}", id);
        ProjectEntity projectEntity = projectService.updateProject(id, projectConverter.fromDtoToEntity(projectDto));

        return projectConverter.fromEntityToDto(projectEntity);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable(name = "id") Long id) {
        logger.debug("Delete project with id {}", id);

        projectService.deleteProject(id);
    }

    @GetMapping(value = "/subject/{id}")
    public List<ProjectDto> getProjectBySubjectId(@PathVariable Long id) {
        logger.debug("Get all project by subject id");
        List <ProjectEntity> projectStudentEntities = projectService.getProjectsBySubjectId(id);
        return projectConverter.fromEntitiesToDtos(projectStudentEntities);
    }

}
