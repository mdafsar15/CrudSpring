package com.genx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genx.model.User;
import com.genx.repository.RoleRepository;
import com.genx.repository.UserRepository;
import com.genx.request.LoginForm;
import com.genx.request.SignUpForm;
import com.genx.response.Response;
import com.genx.response.SignupResponse;
import com.genx.service.UserDetailsServiceImpl;
import com.genx.util.JwtProvider;

import static com.genx.util.SecurityConstants.EXPIRATION_TIME;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    
    JwtProvider j=new JwtProvider();
  
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new Response(jwt,"Bearer",EXPIRATION_TIME));
        
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> registerUser(@RequestBody SignUpForm signUpRequest) {
    	SignupResponse response = userDetailsServiceImpl.registerUser(signUpRequest);
		return new ResponseEntity<SignupResponse>(response, HttpStatus.OK);

    }
    
    @GetMapping(value = "/getAllNotes")
	public List<User> getAllUser() {

		List<User> users = userDetailsServiceImpl.getAllUser();
		return users;
	}
}