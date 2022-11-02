package com.pocu.catalog.repository;

import com.pocu.catalog.entity.TeacherEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends PagingAndSortingRepository<TeacherEntity, Long> {

    List<TeacherEntity> findByFirstName(String firstName);

    List<TeacherEntity> findByLastName(String lastName);

    List<TeacherEntity> findByFirstNameAndLastName(String firstName, String lastName);

    List<TeacherEntity> findBySalaryGreaterThan(Long salary, Pageable pageable);

    Long countAllBySalaryLessThan(Long salary);

    void deleteByCnp(String cnp);
}
