package com.ynov.authentication.servicesImplem;

import com.ynov.authentication.models.Role;
import com.ynov.authentication.models.User;
import com.ynov.authentication.repositories.RoleRepo;
import com.ynov.authentication.repositories.UserRepo;
import com.ynov.authentication.security.JwtService;
import com.ynov.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserImplem implements UserService {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    JwtService jwtService;

    @Override
    public User registerStudent(User student) {
        Optional<Role> role = roleRepo.findByRoleName(Role.RoleName.STUDENT.name());
        if(role.isEmpty()) return null;
        String encodedPassword = bCryptPasswordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        student.getRoles().add(role.get());
        return userRepo.save(student);
    }

    @Override
    public Map<String, Object> login(String email, String password) throws NoSuchAlgorithmException {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty() || !bCryptPasswordEncoder.matches(password, user.get().getPassword()))
            return null;
        Map<String, Object> result = new HashMap<>();
        result.put("jwt", jwtService.generateToken(user.get()));
        result.put("email", email);
        List<String> roles = new ArrayList<>();
        user.get().getRoles().forEach(role -> roles.add(role.getRoleName()));
        result.put("roles", roles);
        return result;
    }
}
