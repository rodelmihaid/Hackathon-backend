package com.pocu.catalog.repository;

import com.pocu.catalog.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByTitle(String title);

    List<ProjectEntity> findByDescription(String description);

    List<ProjectEntity> findAllByDeadlineEquals(LocalDate date);



}

//@Repository
//public interface ProjectRepository extends PagingAndSortingRepository<StudentEntity, Long> {
//}


//@Repository
//public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, Long> {
//
//    List<ProjectEntity> findByTitle(String title);
//
//    List<ProjectEntity> findByDescription(String description);
//
//    List<ProjectEntity> findByAttachment(String attachment);
//
//    void deleteByTitle(String cnp);
//}
