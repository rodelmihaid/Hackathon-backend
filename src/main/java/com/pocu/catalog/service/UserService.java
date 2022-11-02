package com.pocu.catalog.service;

import com.pocu.catalog.constants.SecurityConstant;
import com.pocu.catalog.converter.UserConverter;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.User;
import com.pocu.catalog.enums.RoleType;
import com.pocu.catalog.enums.UserType;
import com.pocu.catalog.exception.StudentAlreadyEnrolledException;
import com.pocu.catalog.exception.UserAlreadyExistsExceptions;
import com.pocu.catalog.exception.UserDoesNotExists;
import com.pocu.catalog.mapper.UserMapper;
import com.pocu.catalog.repository.UserRepository;
import com.pocu.catalog.util.JwtUtil;
import com.pocu.catalog.web.dto.SubjectDto;
import com.pocu.catalog.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@RequiredArgsConstructor //constructor cu parametrii final
@RequiredArgsConstructor
@Service
public class UserService {

//    private final SendEmailService sendEmailService;

    private final UserRepository userRepository;
//    private final UserSubjectRepository userSubjectRepository;

    private final UserConverter userConverter;
    //    private final MessageMapper messageMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtTokenUtil;

    private final SubjectService subjectService;


    @PostConstruct
    private void createAdminUser(){
        UserDto userDto = new UserDto();
        userDto.setEmail("adminrodel@gmail.com");
        userDto.setPassword("admin123");
        userDto.setAuthorities(UserType.ADMIN.toString());
        userDto.setProfileImage("");
        registerUser(userDto);

        UserDto professorDto = new UserDto();
       professorDto.setEmail("professor@gmail.com");
        professorDto.setPassword("professor123");
        professorDto.setAuthorities(UserType.PROFESSOR.toString());
        professorDto.setProfileImage("");
        registerUser(professorDto);


    }


    public List<SubjectEntity> getSubjectsByUserId(Long userId){
        User user=userRepository.findById(userId).get();
        return user.getEnrolledSubjects();

    }
    public List<SubjectDto> getSubjectsByUserEmail(String userEmail){
        User user=userRepository.findUserByEmail(userEmail).get(0);
        UserDto userDto=userConverter.fromEntityToDto(user);
        userDto.getEnrolledSubjects().forEach(elem ->System.out.println(elem.getName()));

        return userDto.getEnrolledSubjects();


    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    public List<User> getUserByUserType(String userType) {
        List<User> users = new ArrayList<>();
        userRepository.findUsersByAuthorities(userType).iterator().forEachRemaining(users::add);
//        userRepository.findAllByAuthorities(userType).iterator().forEachRemaining(users::add);
        return users;
    }


    public UserDto registerUser(UserDto userDto) {
        if (userAlreadyExists(userDto.getEmail())) {
            throw new UserAlreadyExistsExceptions("User already exists!");
        }
        if (userDto.getAuthorities().equals(UserType.USER.toString())) {
            return parseAndCreateUser(userDto, UserType.USER);
        } else if (userDto.getAuthorities().equals(UserType.ADMIN.toString())) {
            return parseAndCreateUser(userDto, UserType.ADMIN);
        } else {
            return parseAndCreateUser(userDto, UserType.PROFESSOR);
        }

    }

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDto parseAndCreateUser(UserDto userDto, UserType userType) {
        logger.info("test {}", userDto.getProfileImage());
        User user = userConverter.fromDtoToEntity(userDto);
        user.setAuthorities(getAutoritiesByUserType(userType));

        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if (user.getProfileImage().equals("")) {
            user.setProfileImage(SecurityConstant.DEFAULT_PROFILE_IMAGE);
        }
        user.setCommentEmail(Boolean.TRUE);
        user.setTagEmail(Boolean.TRUE);
        user.setPostEmail(Boolean.TRUE);
        User savedUser = userRepository.save(user);


//        Thread thread = new Thread(() -> sendEmailService.verifyAndSendEmail(messageMapper.fromUserToMessageDto(savedUser), UserEmailOptions.REGISTER));
//        thread.start();
//        this.emailThreadService.sendEmailToRegisteredUser(user);

        return userConverter.fromEntityToDto(savedUser);
    }

    private String getAutoritiesByUserType(UserType userType) {
        switch (userType) {
            case USER:
                return RoleType.USER.toString();
            case PROFESSOR:
                return RoleType.PROFESSOR.toString();
            case ADMIN:
                return RoleType.ADMIN.toString();
        }
        return "";
    }


    public String loginUserAndReturnJWT(UserDto userDto) {
        if (!userAlreadyExists(userDto.getEmail())) {
            throw new UserDoesNotExists("Incorrect username or password");
        }
        //try catch
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return jwt;

    }

    public boolean userAlreadyExists(String userEmail) {
        List<User> users = getUsersByEmail(userEmail);
        if (users.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<User> getUsersByEmail(String userEmail) {
        List<User> users = userRepository.findUserByEmail(userEmail);
        return users;
    }

    public UserDto getUserByEmail(String userEmail) {
        List<User> users = userRepository.findUserByEmail(userEmail);
        if (users.size() == 1) {
            return userConverter.fromEntityToDto(users.get(0));
        }
        return null;

    }


    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }


    public User updateUser(Long id, User user) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException(String.valueOf(user));
        }
    }

    public User enroll(Long studentId, Long subjectId, Boolean enroll) {
        User student = getUserById(studentId);
        SubjectEntity subject = subjectService.getSubject(subjectId);

        if (enroll) {
            if (student.getEnrolledSubjects() != null && student.getEnrolledSubjects().contains(subject)) {
                throw new StudentAlreadyEnrolledException("Student already enrolled!");
            }
            student.setEnrolledSubject(subject);
        } else {
            student.removeEnrolledSubject(subject);
        }
        return userRepository.save(student);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }
    public void deleteStudent(Long id) {
        userRepository.deleteById(id);
    }

    public User updateStudent(Long id, User studentToUpdate) {
        User persistedStudent = getUserById(id);

        studentToUpdate.setId(id);
        studentToUpdate.setEnrolledSubjects(persistedStudent.getEnrolledSubjects());

        return userRepository.save(studentToUpdate);
    }


//    public List<User> getUsersBySubjectId(Long subjectId) {
//        List<UserSubject> usersSubject = new ArrayList<>();
//        List<User> users = new ArrayList<>();
//        userSubjectRepository.findAllBySubjectId(subjectId).iterator().forEachRemaining(usersSubject::add);
//        for (UserSubject userSubject : usersSubject) {
//            users.add(this.getUserById(userSubject.getUser().getId()));
//        }
//        return users;
//    }

//    public UserDto changeUserPassword(String password, String email) {
//        User user = userRepository.findUserByEmail(email).get(0);
//        user.setPassword(bCryptPasswordEncoder.encode(password));
//        userRepository.save(user);
//        return userMapper.mapUserToUserDto(user);
//    }


}

