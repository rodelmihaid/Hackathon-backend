package com.pocu.catalog.repository;

import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findUserByEmail(String email);

    Optional<User> findById(Long id);

    //    List<User> findAllByAuthorities(String userType);
    List<User> findUsersByAuthorities(String userType);


}
