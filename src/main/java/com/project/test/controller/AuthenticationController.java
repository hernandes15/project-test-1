package com.project.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.dto.JwtAuthenticationResponse;
import com.project.test.model.dto.LoginRequest;
import com.project.test.model.dto.RefreshTokenRequest;
import com.project.test.model.entity.UserEntity;
import com.project.test.service.AuthenticationService;
import com.project.test.service.impl.UserServiceImpl;
import com.project.test.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Value("${app.nameHeader}")
	private String nameHeader;

	@Autowired
	private AuthenticationService service;

	@Autowired
	private UserServiceImpl userImpl;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest)
			throws AuthenticationException, Exception {
		String loginIp = HttpRequestUtil.getRealIP(nameHeader, request);
		JwtAuthenticationResponse jwtAuthenticationResponse = service.authenticate(loginRequest,
				request.getRemoteAddr(), request.getRemoteHost(), loginIp);

		return ResponseEntity.ok(jwtAuthenticationResponse);
	}
}
