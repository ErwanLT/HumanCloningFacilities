package com.erwan.human.controller;

import com.erwan.human.domaine.authentification.AuthRequest;
import com.erwan.human.domaine.authentification.AuthResponse;
import com.erwan.human.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Api(tags = "users")
@RequiredArgsConstructor
public class AuthControler {

    private final UserService userService;

    @PostMapping("/signin")
    @Operation(summary = "Authenticate a user", description = "Generation of jwt token ")
    @ApiResponses(value = {//
            @ApiResponse(responseCode = "400", description = "Something went wrong"), //
            @ApiResponse(responseCode = "422", description = "Invalid username/password supplied")})
    public AuthResponse login(@RequestBody AuthRequest request){
        return userService.signin(request.getUsername(), request.getPassword());
    }
}
