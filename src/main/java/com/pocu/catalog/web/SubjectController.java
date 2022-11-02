package com.pocu.catalog.web;

import com.pocu.catalog.converter.ProjectConverter;
import com.pocu.catalog.converter.SubjectConverter;
import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.service.ProjectService;
import com.pocu.catalog.service.SubjectService;
import com.pocu.catalog.web.dto.ProjectDto;
import com.pocu.catalog.web.dto.SubjectDto;
import com.pocu.catalog.web.dto.TeacherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    private Logger logger = LoggerFactory.getLogger(SubjectController.class);

    private final SubjectService subjectService;
    private final SubjectConverter subjectConverter;

    private final ProjectConverter projectConverter;
    private final ProjectService projectService;

    @Autowired
    public SubjectController(SubjectService subjectService, SubjectConverter subjectConverter,ProjectConverter projectConverter,ProjectService projectService) {
        this.subjectService = subjectService;
        this.subjectConverter = subjectConverter;
        this.projectConverter=projectConverter;
        this.projectService=projectService;
    }

    @GetMapping(value = "")
    public List<SubjectDto> getSubjects() {
        logger.debug("Get all subjects");
        return subjectConverter.fromEntitiesToDtos(subjectService.getSubjects());
    }

    @GetMapping(value = "/{id}")
    public SubjectDto getSubject(@PathVariable Long id) {
        logger.debug("Get subject with id {}", id);
        return subjectConverter.fromEntityToDto(subjectService.getSubject(id));
    }

    @GetMapping(value = "/projects-by-subject/{id}")
    public List<ProjectDto> getProjectsBySubjectId(@PathVariable Long id) {
        logger.debug("Get projects by subject id {}", id);
        return projectConverter.fromEntitiesToDtos(subjectService.getProjectsBySubjectId(id));

    }

    @PostMapping(value = "")
    public SubjectDto addSubject(@Valid @RequestBody SubjectDto subjectDto) {
        logger.debug("Save new subject with name {}", subjectDto.getName());
        SubjectEntity subjectEntity = subjectService.saveSubject(subjectConverter.fromDtoToEntity(subjectDto));

        logger.debug("Save new subject with name {} successful - id {}", subjectDto.getName(), subjectDto.getId());
        return subjectConverter.fromEntityToDto(subjectEntity);
    }

    //@PostMapping(value="/save-project")


    @PutMapping(value = "/{id}")
    public SubjectDto updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectDto subjectDto) {
        logger.debug("Update subject with id {}", id);
        SubjectEntity updatedEntity = subjectConverter.fromDtoToEntity(subjectDto);
        return subjectConverter.fromEntityToDto(subjectService.updateEntity(id, updatedEntity));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSubject(@PathVariable Long id) {
        logger.debug("Delete subject with id {}", id);
        subjectService.deleteSubject(id);
    }

//    @PutMapping(value = "/{subjectId}/project/{projectId}")
//    public SubjectDto assignProjectToSubject(@PathVariable(name = "subjectId") Long subjectId,
//                                             @PathVariable(name = "projectId") Long projectId) {
//        logger.debug("Assign subject {} to project {}", subjectId, projectId);
//        SubjectEntity subjectEntity = subjectService.assignProjectToSubject(subjectId, projectId);
//
//        return teacherConverter.fromEntityToDto(teacherEntity);
//    }

    @PostMapping(value = "/add-project")
    public SubjectDto addProjectOnSubject(@Valid @RequestBody ProjectDto projectDto) {
        logger.debug("Save new project with name {}", projectDto.getTitle());

        ProjectEntity entity= projectService.save(projectDto);

        SubjectEntity subjectEntity = subjectService.addProjectOnSubject(projectDto.getSubjectId(),entity.getId());

        return subjectConverter.fromEntityToDto(subjectEntity);
    }



}
