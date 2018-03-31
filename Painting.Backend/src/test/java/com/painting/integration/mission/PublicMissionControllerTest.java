package com.painting.integration.mission;

import com.painting.response.mission.MissionPublicResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.response.mission.MissionPublicResponse;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PublicMissionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMissions() {
        String url = getRoute("mission");
        ResponseEntity<MissionPublicResponse> response = testRestTemplate.getForEntity(url,MissionPublicResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    private String getRoute(String route) {
        return "http://localhost:" + port + "/" + route;
    }
}