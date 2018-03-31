package com.painting.integration.account;

import com.painting.dataservice.account.UserDataService;
import com.painting.entity.account.User;
import com.painting.exception.viewexception.SystemException;
import com.painting.response.Response;
import com.painting.response.user.UserLoginResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import trapx00.tagx00.dataservice.account.UserDataService;
import trapx00.tagx00.entity.account.User;
import trapx00.tagx00.exception.viewexception.SystemException;
import trapx00.tagx00.response.Response;
import trapx00.tagx00.response.user.UserLoginResponse;

import static org.junit.Assert.*;

// integration test

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    UserDataService userDataService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${jwt.route.authentication.login}")
    private String loginRoute;

    @Value("${jwt.route.authentication.register}")
    private String registerRoute;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private String getRoute(String route) {
        return "http://localhost:" + port + "/" + route;
    }

    @Test
    public void trial() {
        RestTemplate restTemplate = testRestTemplate.getRestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(getAuthenticatedHeaders());
        ResponseEntity<String> response = restTemplate.exchange(getRoute("try"), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123", response.getBody());
    }

    @Test
    public void registerShouldSucceedAndReturnToken() {
        LinkedMultiValueMap multiValueMap = new LinkedMultiValueMap();
        multiValueMap.add("username","李四");//该用户已经存在，换名字可pass
        multiValueMap.add("password","456");
        multiValueMap.add("email","123");
        multiValueMap.add("role","ROLE_WORKER");
        String url = getRoute("account/register");
        HttpEntity entity = new HttpEntity(multiValueMap,new HttpHeaders());
        ResponseEntity<Response> response = testRestTemplate.exchange(url,HttpMethod.POST,entity,Response.class);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        userDataService.deleteUser("李四");//未实现，无法删除
    }

    @Test
    public void loginShouldSuccess() {
        try {
            User test = new User("123","123","",null);
            userDataService.saveUser(test);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("username","123");
        map.add("password","123");
        String url = getRoute("account/login");
        HttpEntity entity = new HttpEntity(map);
        ResponseEntity<Response> response = testRestTemplate.exchange(url,HttpMethod.GET,entity,Response.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        userDataService.deleteUser("123");
    }

    public HttpHeaders getAuthenticatedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+login().getBody().getToken());
        return headers;
    }



    private ResponseEntity<UserLoginResponse> login() {
        String url = getRoute(loginRoute) + "?username=test&password=test";
        return testRestTemplate.getForEntity(url, UserLoginResponse.class);
    }


}