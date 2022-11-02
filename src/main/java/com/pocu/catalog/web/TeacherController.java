package com.pocu.catalog.web;

import com.pocu.catalog.converter.SubjectConverter;
import com.pocu.catalog.converter.TeacherBasicDetailsConverter;
import com.pocu.catalog.converter.TeacherConverter;
import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.service.TeacherService;
import com.pocu.catalog.web.dto.TeacherBasicDetailsDto;
import com.pocu.catalog.web.dto.TeacherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController     //this means that this class will become a Bean (class managed by Spring) and will contain endpoints
                    //an endpoint is a method that handles HTTP requests and returns HTTP response
@RequestMapping(value = "/teacher")         //this will be the base of all the endpoints from this controller
public class TeacherController {

    private Logger logger = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final TeacherConverter teacherConverter;
    private final SubjectConverter subjectConverter;
    private final TeacherBasicDetailsConverter teacherBasicDetailsConverter;

    @Value("${test.value}")         //retrieves a value from application.yml file
    private String testValue;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherConverter teacherConverter,
                             TeacherBasicDetailsConverter teacherBasicDetailsConverter, SubjectConverter subjectConverter) {
        this.teacherService = teacherService;
        this.teacherConverter = teacherConverter;
        this.teacherBasicDetailsConverter = teacherBasicDetailsConverter;
        this.subjectConverter=subjectConverter;
    }

    @GetMapping(value = "/test")
    public String test() {
        return testValue;
    }

    @GetMapping(value = "/{id}")
    public TeacherDto getTeacher(@PathVariable(name = "id") Long id) {
        logger.debug("Get teacher with id {}", id);

        return teacherConverter.fromEntityToDto(teacherService.getTeacher(id));
    }



    @GetMapping(value = "/{id}/basic")
    public TeacherBasicDetailsDto getTeacherBasicDetails(@PathVariable(name = "id") Long id) {
        logger.debug("Get teacher basic details with id {}", id);

        return teacherBasicDetailsConverter.fromEntityToDto(teacherService.getTeacher(id));
    }

    @GetMapping(value = "/maxSalary/{maxSalary}")
    public Long countTeachersWithMaxSalary(@PathVariable Long maxSalary) {
        return teacherService.countTeachersWithSmallSalary(maxSalary);
    }

/*    @GetMapping(value = "")
    public List<TeacherDto> getAll(@RequestParam(name = "firstName", required = false) String firstName,
                                   @RequestParam(name = "lastName", required = false) String lastName,
                                   @RequestParam(name = "minSalary", required = false) Long minSalary,
                                   @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(name = "size", required = false, defaultValue = "20") Integer size) {
        List<TeacherEntity> teacherEntities;

        if (firstName != null || lastName != null) {
            teacherEntities = teacherService.getAllTeachersByName(firstName, lastName);
        } else if (minSalary != null) {
            teacherEntities = teacherService.getAllTeachersBySalary(minSalary, page, size);
        } else {
            teacherEntities = teacherService.getAllTeachers(page, size);
        }

        return teacherConverter.fromEntitiesToDtos(teacherEntities);
    }*/

    @GetMapping(value = "")
    public List<TeacherDto> getTeachers() {
        List<TeacherEntity> teacherEntities = teacherService.getAllTeachers();
        return teacherConverter.fromEntitiesToDtos(teacherEntities);
    }

    @PostMapping(value = "")
    public TeacherDto saveTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        logger.debug("Save new teacher {}", teacherDto);
        TeacherEntity teacherEntity = teacherConverter.fromDtoToEntity(teacherDto);

        teacherEntity = teacherService.saveTeacher(teacherEntity);

        logger.debug("Teacher saved - id {}", teacherEntity.getId());
        return teacherConverter.fromEntityToDto(teacherEntity);
    }

    @PutMapping(value = "/{id}")
    public TeacherDto updateTeacher(@PathVariable(name = "id") Long id,
                                    @Valid @RequestBody TeacherDto teacherDto) {
        TeacherEntity teacherEntity = teacherConverter.fromDtoToEntity(teacherDto);
        teacherEntity = teacherService.updateTeacher(id, teacherEntity);

        return teacherConverter.fromEntityToDto(teacherEntity);
    }

    @PutMapping(value = "/{teacherId}/subject/{subjectId}")
    public TeacherDto assignSubjectToTeacher(@PathVariable(name = "teacherId") Long teacherId,
                                             @PathVariable(name = "subjectId") Long subjectId) {
        logger.debug("Assign teacher {} to subject {}", teacherId, subjectId);
        TeacherEntity teacherEntity = teacherService.assignSubjectToTeacher(teacherId, subjectId);

        return teacherConverter.fromEntityToDto(teacherEntity);
    }

/*

    @DeleteMapping(value = "")
    public void deleteTeacher(@RequestParam(name = "id", required = false) Long id,
                              @RequestParam(name = "cnp", required = false) String cnp) {

        if (id != null) {
            logger.debug("Delete teacher with id {}", id);
            teacherService.deleteTeacher(id);
        } else if (cnp != null) {
            logger.debug("Delete teacher with cnp {}", cnp);
            teacherService.deleteTeacherByCnp(cnp);
        } else {
            logger.debug("Delete all teachers");
            teacherService.deleteAllTeachers();
        }
    }
*/

    @DeleteMapping(value = "")
    public void deleteTeacher(@RequestParam(name = "id", required = true) Long id) {
        logger.debug("Delete teacher with id {}", id);
        teacherService.deleteTeacher(id);
    }

}
