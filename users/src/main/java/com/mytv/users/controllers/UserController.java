package com.mytv.users.controllers;

import com.mytv.users.model.GetUserRequest;
import com.mytv.users.model.User;
import com.mytv.users.model.UserDTO;
import com.mytv.users.services.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@OpenAPIDefinition(servers = {  @Server(url = "http://cluster01:30782/"),@Server(url = "http://localhost:8080")
        })
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * endpoint to get users by fname,lname,email,service
     */
    @GetMapping("/users")
    public List<UserDTO> get(@ParameterObject GetUserRequest getUserRequest,
                             @ParameterObject Pageable pageable){
        return userService.findUser(getUserRequest,pageable);
    }
}
