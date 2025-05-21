package com.project.test.controller;

import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.entity.UserEntity;
import com.project.test.service.UserService;
import com.project.test.util.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private BaseHelper helper;
	
	@GetMapping("/find")
    public ResponseEntity<GlobalResponse> findUser() throws Exception {
        return service.findAllUser();
    }
	
	@GetMapping("/find/{uuid}")
    public ResponseEntity<GlobalResponse> findUserByUuid(@PathVariable UUID uuid) throws Exception {
        return service.findUserByUuid(uuid);
    }
	
	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlobalResponse> saveUser(HttpServletRequest request, @RequestBody UserEntity param) throws Exception {
        return service.save(param, helper.getJwtFromRequest(request));
    }
	
	@PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GlobalResponse> editUser(HttpServletRequest request, @RequestBody UserEntity param) throws Exception {
        return service.update(param, helper.getJwtFromRequest(request));
    }

}
