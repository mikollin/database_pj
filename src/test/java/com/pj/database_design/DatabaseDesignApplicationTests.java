package com.pj.database_design;

import com.pj.database_design.domain.Authority;
import com.pj.database_design.domain.User;
import com.pj.database_design.repository.AuthorityRepository;
import com.pj.database_design.repository.UserRepository;
import com.pj.database_design.service.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DatabaseDesignApplicationTests {

    @Test
    void contextLoads() {

    }

}
