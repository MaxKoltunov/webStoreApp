package com.web.webStoreApp.mainApi.service;

import com.web.webStoreApp.TestDatabase;
import com.web.webStoreApp.mainApi.dto.UserDTO;
import com.web.webStoreApp.mainApi.entity.LevelsOfLoyalty;
import com.web.webStoreApp.mainApi.entity.Role;
import com.web.webStoreApp.mainApi.entity.User;
import com.web.webStoreApp.mainApi.exceptions.ObjectNotFoundException;
import com.web.webStoreApp.mainApi.exceptions.UserAlreadyExistsExcpetion;
import com.web.webStoreApp.mainApi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest extends TestDatabase {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        // Given
        User user = new User();
        user.setPhoneNumber("+1234567890");

        User existingUser = new User();
        existingUser.setPhoneNumber("+1234567890");
        existingUser.setExisting(true);

        when(userRepository.findByPhoneNumber(user.getPhoneNumber()))
                .thenReturn(Optional.of(existingUser));

        // When
        assertThrows(UserAlreadyExistsExcpetion.class, () -> userService.create(user));

        // Then
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testCreateUser_UserNotExistingAnymore() {
        // Given
        User user = new User();
        user.setPhoneNumber("+1234567890");

        User existingUser = new User();
        existingUser.setPhoneNumber("+1234567890");
        existingUser.setExisting(false);

        when(userRepository.findByPhoneNumber(user.getPhoneNumber()))
                .thenReturn(Optional.of(existingUser));

        // When
        userService.create(user);

        // Then
        verify(userRepository, times(1)).deleteUser(user.getPhoneNumber());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangeExistingInUser_UserExists() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("+1234567890");

        when(userRepository.findByPhoneNumber(userDTO.getPhoneNumber()))
                .thenReturn(Optional.of(new User()));

        // When
        userService.changeExistingInUser(userDTO);

        // Then
        verify(userRepository, times(1)).changeExistingInUser(userDTO.getPhoneNumber());
    }

    @Test
    void testChangeExistingInUser_UserNotFound() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("1234567890");

        when(userRepository.findByPhoneNumber(userDTO.getPhoneNumber()))
                .thenReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> userService.changeExistingInUser(userDTO));

        // Then
        verify(userRepository, times(0)).changeExistingInUser(anyString());
    }

    @Test
    void testChangeLevel_UserExists() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("1234567890");
        userDTO.setLevelName("gold");

        User user = new User();
        when(userRepository.findByPhoneNumber(userDTO.getPhoneNumber()))
                .thenReturn(Optional.of(user));

        // When
        userService.changeLevel(userDTO);

        // Then
        assertEquals(LevelsOfLoyalty.gold, user.getLevelOfLoyalty());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangeLevel_UserNotFound() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setPhoneNumber("+1234567890");

        when(userRepository.findByPhoneNumber(userDTO.getPhoneNumber()))
                .thenReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> userService.changeLevel(userDTO));

        // Then
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testGetByUsername_UserExists() {
        // Given
        String username = "+1234567890";
        User user = new User();
        user.setPhoneNumber(username);

        when(userRepository.findByPhoneNumber(username))
                .thenReturn(Optional.of(user));

        // When
        User result = userService.getByUsername(username);

        // Then
        assertEquals(user, result);
    }

    @Test
    void testGetByUsername_UserNotFound() {
        // Given
        String username = "+1234567890";

        // When
        when(userRepository.findByPhoneNumber(username))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername(username));
    }

    @Test
    void testGetCurrentUser() {
        // Given
        String username = "+1234567890";
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        User user = new User();
        user.setPhoneNumber(username);

        when(userRepository.findByPhoneNumber(username))
                .thenReturn(Optional.of(user));

        // When
        User result = userService.getCurrentUser();

        // Then
        assertEquals(user, result);
    }

    @Test
    void testGetAdmin() {
        // Given
        String username = "+1234567890";
        User user = new User();
        user.setPhoneNumber(username);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByPhoneNumber(username)).thenReturn(Optional.of(user));

        // When
        userService.getAdmin();

        // Then
        assertEquals(Role.ROLE_ADMIN, user.getRole());
        verify(userRepository, times(1)).save(user);
    }
}
