package com.pocu.catalog.web;

import com.pocu.catalog.constants.SecurityConstant;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.User;
import com.pocu.catalog.service.UserService;
import com.pocu.catalog.web.dto.SubjectDto;
import com.pocu.catalog.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/user-type")
    public List<User> getUsersByUserType(@RequestParam String userType) {
        return userService.getUserByUserType(userType);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody UserDto userDto) {
        String jwt = userService.loginUserAndReturnJWT(userDto);
        return new ResponseEntity<>("", getJwtHeader(jwt), HttpStatus.OK);
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @GetMapping(value = "/user-subjects/{userEmail}")
    public  List<SubjectDto> getSubjectByUserId(@PathVariable(name = "userEmail") String userEmail) {
        return userService.getSubjectsByUserEmail(userEmail);
    }


    private HttpHeaders getJwtHeader(String jwt) {

        HttpHeaders headers = new HttpHeaders();

        headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwt);

        headers.add("Access-Control-Expose-Headers", SecurityConstant.JWT_TOKEN_HEADER);

        return headers;

    }


//    @GetMapping(value = "/subject")
//    public List<User> getUserBySubjectId(@RequestParam("subjectId") Long subjectId) {
//        return userService.getUsersBySubjectId(subjectId);
//    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/email")
    public UserDto getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping(value = "/{id}")
    public User updateUser(@PathVariable(name = "id") Long id,
                           @RequestBody User user) {

        return userService.updateUser(id, user);
    }

//    @PutMapping("/new-password")
//    public UserDto changeUserPassword(@RequestBody UserDto userDto) {
//        return userService.changeUserPassword(userDto.getPassword(), userDto.getEmail());
//    }
}
