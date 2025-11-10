package com.ems.EmployeeManagementSystem.service.authService;

import com.ems.EmployeeManagementSystem.model.UserAuth;
import com.ems.EmployeeManagementSystem.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpUserDetailsService implements UserDetailsService {


    @Autowired
    private AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserAuth user = authRepo.findUserAuthByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new EmpUserPrincipal(user);
    }
}
