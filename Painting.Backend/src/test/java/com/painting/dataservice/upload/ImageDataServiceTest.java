package com.painting.dataservice.upload;

import com.painting.exception.viewexception.SystemException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.dataservice.upload.ImageDataService;
import trapx00.tagx00.exception.viewexception.SystemException;
import trapx00.tagx00.publicdatas.mission.image.ImageJob;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageDataServiceTest {
    @Autowired
    private ImageDataService imageDataService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void uploadImage() {
        try {
            imageDataService.uploadImage("0000",null);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteImage() {
        try {
            imageDataService.uploadImage("0000",null);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        imageDataService.deleteImage("0000");
    }
}