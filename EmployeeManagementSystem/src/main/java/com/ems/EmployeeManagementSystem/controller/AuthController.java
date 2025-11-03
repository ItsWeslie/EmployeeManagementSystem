package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.dto.AuthRequest;
import com.ems.EmployeeManagementSystem.dto.AuthResponse;
import com.ems.EmployeeManagementSystem.model.UserAuth;
import com.ems.EmployeeManagementSystem.repository.AuthRepo;
import com.ems.EmployeeManagementSystem.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthController {


    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final AuthRepo authRepo;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody AuthRequest authRequest) {

            String email = authRequest.getEmail();
            String password = authRequest.getPassword();

            try {
                Authentication auth = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

                if (auth.isAuthenticated()) {
                    UserAuth user = authRepo.findUserAuthByEmail(email);
                    String role = user.getRole().toString();
                    String token = jwtService.generateToken(email);

                    return ResponseEntity.ok(new AuthResponse(token,role,email));
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password");
            }
        catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found. Kindly sign up.");
        } catch (org.springframework.security.authentication.BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid password or email. Please try again.");
        }
    }
}
