package com.painting.dataservice.account;

import com.painting.entity.account.Role;
import com.painting.entity.account.User;
import com.painting.exception.viewexception.SystemException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.entity.account.Role;
import trapx00.tagx00.entity.account.User;
import trapx00.tagx00.exception.viewexception.SystemException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDataServiceTest {
    @Autowired
    private UserDataService userDataService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private final User user = new User("999", "999", "test@tagx00.ml", Arrays.asList(Role.REQUESTER));

    @Test
    public void saveUser() {
        try {
            userDataService.saveUser(user);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteUser() {
        try {
            userDataService.saveUser(user);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        userDataService.deleteUser("123");
    }

    @Test
    public void userExists() {
        try {
            userDataService.saveUser(user);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        assertEquals(true, userDataService.isUserExistent("123"));
    }

    @Test
    public void userNotExists() {
        try {
            userDataService.saveUser(user);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        assertEquals(false,userDataService.isUserExistent("000"));
    }

    @Test
    public void passwordRight() {
        try {
            userDataService.saveUser(user);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        assertEquals(true,userDataService.confirmPassword("123","345"));

    }

    @Test
    public void passwordWrong() {
        try {
            userDataService.saveUser(user);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        assertEquals(false,userDataService.confirmPassword("123","000"));

    }

}