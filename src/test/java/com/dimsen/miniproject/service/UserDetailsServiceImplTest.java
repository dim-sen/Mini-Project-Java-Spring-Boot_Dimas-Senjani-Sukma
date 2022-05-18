package com.dimsen.miniproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.dao.ApplicationDao;
import com.dimsen.miniproject.domain.dao.JobInformationDao;
import com.dimsen.miniproject.domain.dao.ProfileTypeDao;
import com.dimsen.miniproject.domain.dao.UserDao;
import com.dimsen.miniproject.domain.dao.UserDetailsDao;
import com.dimsen.miniproject.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        ProfileTypeDao profileTypeDao = new ProfileTypeDao();
        profileTypeDao.setUserId(1L);
        profileTypeDao.setName("Name");
        profileTypeDao.setUser(new UserDao());

        UserDao userDao = new UserDao();
        userDao.setId(1L);
        userDao.setPassword("iloveyou");
        userDao.setProfileType(profileTypeDao);
        userDao.setRole(AppConstant.UserRole.ROLE_COMPANY);
        userDao.setUsername("dim");

        Optional<UserDao> ofResult = Optional.of(userDao);

        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);

        UserDetails actualLoadUserByUsernameResult = this.userDetailsServiceImpl.loadUserByUsername("dim");
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();

        assertEquals(1L, ((UserDetailsDao) actualLoadUserByUsernameResult).getId().longValue());
        assertEquals("dim", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
    }

}

