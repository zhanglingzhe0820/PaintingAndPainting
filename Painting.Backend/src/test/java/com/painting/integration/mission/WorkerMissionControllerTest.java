package com.painting.integration.mission;

import com.painting.response.Response;
import com.painting.response.mission.MissionQueryDetailResponse;
import com.painting.response.mission.MissionQueryResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.response.Response;
import trapx00.tagx00.response.SuccessResponse;
import trapx00.tagx00.response.WrongResponse;
import trapx00.tagx00.response.mission.MissionQueryDetailResponse;
import trapx00.tagx00.response.mission.MissionQueryResponse;
import trapx00.tagx00.response.upload.UploadMissionImageResponse;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkerMissionControllerTest {

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

    private String getRoute(String route) {
        return "http://localhost:" + port + "/" + route;
    }

    @Test
    public void queryOnesAllMissionsButNoRequester() {
        String url = getRoute("mission/worker");
        ResponseEntity<MissionQueryResponse> response = testRestTemplate.getForEntity(url, MissionQueryResponse.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

    @Test
    public void abortButNoRequester() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<Response> response = testRestTemplate.getForEntity(url, Response.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

    @Test
    public void getInstanceInformationButNoRequester() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<MissionQueryDetailResponse> response = testRestTemplate.getForEntity(url, MissionQueryDetailResponse.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

    @Test
    public void saveProgressButNoRequester() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<Response> response = testRestTemplate.getForEntity(url, Response.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

    @Test
    public void submitButNoRequester() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<Response> response = testRestTemplate.getForEntity(url, Response.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

   /* @Test
    public void queryOnesAllMissionsAndSucceed() {
        String url = getRoute("mission/worker");
        ResponseEntity<MissionQueryResponse> response = testRestTemplate.getForEntity(url, MissionQueryResponse.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
    }

    @Test
    public void abortAndSucceed() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<SuccessResponse> response = testRestTemplate.getForEntity(url, SuccessResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void getInstanceInformationAndSucceed() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<MissionQueryDetailResponse> response = testRestTemplate.getForEntity(url, MissionQueryDetailResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void saveProgressAndSucceed() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<SuccessResponse> response = testRestTemplate.getForEntity(url, SuccessResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void submitAndSucceed() {
        String url = getRoute("mission/worker/0");
        ResponseEntity<SuccessResponse> response = testRestTemplate.getForEntity(url, SuccessResponse.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
    */
}