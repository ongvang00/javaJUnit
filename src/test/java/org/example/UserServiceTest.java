package org.example;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private User existingUser;

    @Before
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        existingUser = Mockito.mock(User.class);

        when(existingUser.getUsername()).thenReturn("ExistingUser");
        when(existingUser.getPassword()).thenReturn("password");
        when(existingUser.getEmail()).thenReturn("existinguser@example.com");
    }

    @Test
    public void testRegisterUser_Successful() {
        User newUser = new User("JohnDoe", "password", "johndoe@example.com");
        when(userService.registerUser(newUser)).thenReturn(true);

        boolean registrationSuccessful = userService.registerUser(newUser);
        assertTrue(registrationSuccessful);
    }

    @Test
    public void testRegisterUser_UsernameTaken() {
        User newUser = new User("ExistingUser", "password", "johndoe@example.com");
        when(userService.registerUser(newUser)).thenReturn(false);

        boolean registrationSuccessful = userService.registerUser(newUser);
        assertFalse(registrationSuccessful);
    }

    @Test
    public void testLoginUser_Successful() {
        when(userService.loginUser("ExistingUser", "password")).thenReturn(existingUser);

        User loggedInUser = userService.loginUser("ExistingUser", "password");
        assertNotNull(loggedInUser);
    }

    @Test
    public void testLoginUser_IncorrectPassword() {
        when(userService.loginUser("ExistingUser", "incorrectPassword")).thenReturn(null);

        User loggedInUser = userService.loginUser("ExistingUser", "incorrectPassword");
        assertNull(loggedInUser);
    }
}
