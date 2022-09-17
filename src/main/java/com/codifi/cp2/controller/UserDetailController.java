package com.codifi.cp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import com.codifi.cp2.entity.UserInformationEntity;
import com.codifi.cp2.service.UserDetailService;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "Users", description = "User Details")
public class UserDetailController {

    @Autowired
    UserDetailService userDetailsService;

    /**
     * method to login
     * 
     * @author Pradeep Ravichandran
     * @param userInformationEntity
     * @return
     */
    @PostMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> login(@RequestBody UserInformationEntity userInformationEntity) {
        return userDetailsService.login(userInformationEntity);
    }

    /**
     * Method To get All User List
     * 
     * @author Pradeep Ravichandran
     * @return List of User Details
     */
    @GetMapping(value = "/getAllUserList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllUserList() {
        return userDetailsService.getAllUserList();
    }

    /**
     * Method To Save user Information
     * 
     * @author Sandeep Govindaraj
     * @return
     */
    @RequestMapping(value = "/saveUserDetails", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> saveUserDetails(@RequestBody UserInformationEntity userDetailsEntity) {
        return userDetailsService.saveUserDetails(userDetailsEntity);
    }
}