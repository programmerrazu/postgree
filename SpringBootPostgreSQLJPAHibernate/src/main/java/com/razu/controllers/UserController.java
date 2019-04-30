package com.razu.controllers;

import com.razu.entity.Users;
import com.razu.service.UserService;
import com.razu.utils.ErrorUtils;
import com.razu.utils.MessgageUtils;
import com.razu.utils.ResponseTagName;
import static com.razu.utils.UrlUtils.USERS;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = USERS)
class UserController implements ResponseTagName, MessgageUtils {

    @Autowired
    private UserService userService;

    @GetMapping(path = USER_BY_ID)
    ResponseEntity<?> getUserById(@RequestParam(name = "id", required = true) Long id) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Optional<Users> user = userService.findUserById(id);
        if (user.isPresent()) {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_200);
            serviceResponse.put(MESSAGE, BY_UID);
            serviceResponse.put(USERES, user);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, USER_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @GetMapping(path = USER_BY_UN)
    ResponseEntity<?> getUserByName(@RequestParam(name = "firstName", required = true) String firstName) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Users user = userService.findUserByUserName(firstName);
        if (user != null) {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_200);
            serviceResponse.put(MESSAGE, BY_UNAME);
            serviceResponse.put(USERES, user);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, USER_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @GetMapping(path = ALL_USER)
    ResponseEntity<?> getAllUser() {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
        Iterable<Users> userInfoList = userService.findAllUser();
        if (userInfoList.spliterator().getExactSizeIfKnown() > 0) {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_200);
            serviceResponse.put(MESSAGE, USER_AVAIL);
            serviceResponse.put(USERES, userInfoList);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, USER_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }

    @PostMapping(path = REGISTRATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> userCreate(@Valid @RequestBody Users userInfo, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(ErrorUtils.userError(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
            Users regUser = userService.save(userInfo);
            if (regUser != null) {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_200);
                serviceResponse.put(MESSAGE, REG_SUCC);
                serviceResponse.put(USERES, regUser);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, REG_FAILED);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        }
    }

    @PutMapping(path = USER_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> userUpdate(@Valid @RequestBody Users userInfo, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(ErrorUtils.userError(result), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();
            Optional<Users> user = userService.findUserById(userInfo.getId());

            if (user.isPresent()) {

                Users newuser = new Users();

                newuser.setFirstName(userInfo.getFirstName());
                newuser.setLastName(userInfo.getLastName());
                newuser.setEmail(userInfo.getEmail());

                Users editUser = userService.update(userInfo);

                if (editUser != null) {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_200);
                    serviceResponse.put(MESSAGE, UPDATE_SUCC);
                    serviceResponse.put(USERES, editUser);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                } else {
                    serviceResponse.put(STATUS, Boolean.TRUE);
                    serviceResponse.put(STATUS_CODE, CODE_201);
                    serviceResponse.put(MESSAGE, UPDATE_FAILED);
                    return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
                }
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, UN_NOT_AVAIL);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        }
    }

    @DeleteMapping(path = USER_DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> userDelete(@RequestBody Users userInfo) {
        LinkedHashMap<String, Object> serviceResponse = new LinkedHashMap<String, Object>();

        Optional<Users> user = userService.findUserById(userInfo.getId());

        // Users existUser = userService.findUserByUserName(userInfo.getFirstName());
        if (user.isPresent()) {
            Boolean deleteUser = userService.delete(user.get());
            if (deleteUser) {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_200);
                serviceResponse.put(MESSAGE, DELETE_SUCC);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            } else {
                serviceResponse.put(STATUS, Boolean.TRUE);
                serviceResponse.put(STATUS_CODE, CODE_201);
                serviceResponse.put(MESSAGE, DELETE_FAILED);
                return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
            }
        } else {
            serviceResponse.put(STATUS, Boolean.TRUE);
            serviceResponse.put(STATUS_CODE, CODE_201);
            serviceResponse.put(MESSAGE, UN_NOT_AVAIL);
            return new ResponseEntity<>(serviceResponse, new HttpHeaders(), HttpStatus.OK);
        }
    }
}
