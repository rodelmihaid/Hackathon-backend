package com.pocu.catalog.repository;

import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.entity.ProjectStudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStudentRepository extends CrudRepository<ProjectStudentEntity, Long> {
    List<ProjectStudentEntity> findProjectStudentEntityByProjectId(Long projectId);
  ProjectStudentEntity findProjectStudentEntityByProjectIdAndStudentId(Long projectId,Long studentId);
 List<ProjectStudentEntity> findProjectStudentEntityByProjectIdAndStudent_Authorities(Long projectId,String roleName);
}

//@Repository
//public interface ProjectStudentRepository extends PagingAndSortingRepository<ProjectStudentEntity, Long> {
//
//    List<ProjectStudentEntity> findByDescription(String description);
//
//    List<ProjectStudentEntity> findBySolution(String solution);
//
//    void deleteByDescription(String description);
//}
