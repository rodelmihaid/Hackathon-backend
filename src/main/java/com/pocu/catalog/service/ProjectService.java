package com.pocu.catalog.service;

import com.pocu.catalog.converter.ProjectConverter;
import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.entity.ProjectStudentEntity;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.exception.ProjectNotFoundException;
import com.pocu.catalog.repository.ProjectRepository;
import com.pocu.catalog.web.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service                //this class is a Service bean
public class ProjectService {

    private final static String PROJECT_NOT_FOUND_CODE = "PROJECT_NOT_FOUND";
    private final ProjectRepository projectRepository;
    private final SubjectService subjectService;
    private final ProjectConverter projectConverter;


    @Autowired
    public ProjectService(ProjectRepository projectRepository, SubjectService subjectService,ProjectConverter projectConverter) {
        this.projectRepository = projectRepository;
        this.subjectService = subjectService;
        this.projectConverter=projectConverter;
    }

    public List<ProjectEntity> getAllProjects() {
        List<ProjectEntity> projectList = new ArrayList<>();
        projectRepository.findAll().iterator().forEachRemaining(projectList::add);
        return projectList;
    }


    public void saveProject(ProjectEntity projectEntity, Long subjectId) {
        SubjectEntity subjectEntity = subjectService.getSubject(subjectId);
        subjectEntity.addProject(projectEntity);
        subjectService.saveSubject(subjectEntity);
    }

    public ProjectEntity save(ProjectDto projectDto)
    {
        return projectRepository.save(projectConverter.fromDtoToEntity(projectDto));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public void deleteAllProjects() {
        projectRepository.deleteAll();
    }

    public ProjectEntity getProject(Long id) {
        Optional<ProjectEntity> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return projectOptional.get();
        } else {
            throw new ProjectNotFoundException("Project with id " + id + "not found", PROJECT_NOT_FOUND_CODE);
        }
    }

    public ProjectEntity updateProject(Long id, ProjectEntity projectToUpdate) {
        ProjectEntity storedProject = getProject(id);
        projectToUpdate.setId(id);

        return projectRepository.save(projectToUpdate);

    }

//-----------------------------------------------------------------

    public ProjectEntity assignProjectToSubject(Long projectId, Long subjectId) {
        SubjectEntity subjectEntity = subjectService.getSubject(subjectId);
        ProjectEntity projectEntity = getProject(projectId);

        projectEntity.setSubject(subjectEntity);

        return projectRepository.save(projectEntity);
    }

    public List<ProjectEntity> getProjectsBySubjectId(Long id) {
        List<ProjectEntity> projectEntities = subjectService.getAllProjectsFromSubjects(id);
        return projectEntities;

    }


}
//--------------------------------------------------------------


//@Service
//public class ProjectService {
////    private SubjectRepository subjectRepository;
////    private static final String STUDENT_NOT_FOUND_CODE = "STUDENT_NOT_FOUND";
////    @Autowired
////    public ProjectService(SubjectRepository subjectRepository) {
////        this.subjectRepository = subjectRepository;
////    }

