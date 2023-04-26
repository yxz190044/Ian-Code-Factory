
package com.companyname.projectname.controller;

import com.companyname.projectname.model.User;
import com.companyname.projectname.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("password1");
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setPassword("password2");
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList);
        List<User> result = userController.getAllUsers(mock(Request.class), mock(Response.class));
        assertEquals(userList, result);
        verify(userService, times(1)).getAllUsers();
    }
    
    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setPassword("password1");
        when(userService.getUserById(1)).thenReturn(user);
        User result = userController.getUserById(mock(Request.class), mock(Response.class));
        assertEquals(user, result);
        verify(userService, times(1)).getUserById(1);
    }
    
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password1");
        when(userService.createUser(user)).thenReturn(user);
        User result = userController.createUser(mock(Request.class), mock(Response.class));
        assertEquals(user, result);
        verify(userService, times(1)).createUser(user);
    }
    
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setPassword("password1");
        when(userService.getUserById(1)).thenReturn(user);
        user.setUsername("user2");
        when(userService.updateUser(user)).thenReturn(user);
        User result = userController.updateUser(mock(Request.class), mock(Response.class));
        assertEquals(user, result);
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(1)).updateUser(user);
    }
    
    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setPassword("password1");
        when(userService.getUserById(1)).thenReturn(user);
        userController.deleteUser(mock(Request.class), mock(Response.class));
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(1)).deleteUser(user);
    }
    
}
