package com.painting.data.account;

import com.painting.dataservice.account.UserDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.dataservice.account.UserDataService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDataServiceImplTest {
    @Autowired
    private UserDataService userDataService;

    @Test
    public void isUserExistent() {
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void confirmPassword() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void sendEmail() {
        userDataService.sendEmail("445073309@qq.com");
    }
}