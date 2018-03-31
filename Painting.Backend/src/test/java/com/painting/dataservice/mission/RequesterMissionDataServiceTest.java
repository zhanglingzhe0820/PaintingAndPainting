package com.painting.dataservice.mission;

import com.painting.entity.mission.Mission;
import com.painting.exception.viewexception.SystemException;
import com.painting.publicdatas.instance.MissionInstanceState;
import com.painting.publicdatas.mission.MissionState;
import com.painting.publicdatas.mission.MissionType;
import com.painting.vo.mission.image.ImageMissionType;
import com.painting.vo.mission.instance.MissionInstanceItemVo;
import com.painting.vo.mission.missiontype.MissionProperties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.entity.mission.Mission;
import trapx00.tagx00.exception.viewexception.SystemException;
import trapx00.tagx00.publicdatas.instance.MissionInstanceState;
import trapx00.tagx00.publicdatas.mission.MissionState;
import trapx00.tagx00.publicdatas.mission.MissionType;
import trapx00.tagx00.vo.mission.image.ImageMissionType;
import trapx00.tagx00.vo.mission.instance.MissionInstanceItemVo;
import trapx00.tagx00.vo.mission.missiontype.MissionProperties;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RequesterMissionDataServiceTest {
    @Autowired
    private RequesterMissionDataService requesterMissionDataService;

    private MissionProperties missionProperties;
    private MissionInstanceItemVo missionInstanceItem;
    private Mission mission;

    @Before
    public void setUp() throws Exception {
        missionProperties = new MissionProperties(MissionType.IMAGE);
        ArrayList<String> topics = new ArrayList<>();
        topics.add("风景画");
        topics.add("灾难画");
        ArrayList<String> allowedTags = new ArrayList<>();
        allowedTags.add("风景画");
        allowedTags.add("灾难画");
        ArrayList<ImageMissionType> imageMissionTypes = new ArrayList<>();
        imageMissionTypes.add(ImageMissionType.PART);
        imageMissionTypes.add(ImageMissionType.DISTRICT);
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://desk-fd.zol-img.com.cn/t_s960x600c5/g5/M00/0E/00/ChMkJlnJ4TOIAyeVAJqtjV-XTiAAAgzDAE7v40Amq2l708.jpg");
        urls.add("http://pic1.16xx8.com/allimg/170801/1-1FP116442T62.jpg");
        urls.add("http://pic1.16xx8.com/allimg/170801/1-1FP116442T62.jpg");
        mission = new Mission("123",
                "123123", topics, false, allowedTags,
                MissionType.IMAGE, MissionState.ACTIVE, new Date(), new Date(),
                "http://pic1.16xx8.com/allimg/170801/1-1FP116442T62.jpg", "凌尊", urls, imageMissionTypes);
        missionInstanceItem = new MissionInstanceItemVo(0, "张三", MissionInstanceState.SUBMITTED, new Date(), new Date(), 100, 100);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveMission() {
        try {
            requesterMissionDataService.saveMission(mission);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMissionByUsername() {
        try {
            requesterMissionDataService.saveMission(mission);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("123", requesterMissionDataService.getMissionByMissionId(1).getTitle());
    }

    @Test
    public void getInstanceById() {
        try {
            requesterMissionDataService.saveMission(mission);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(null, requesterMissionDataService.getInstanceByinstanceId(0));
    }

    @Test
    public void getInstanceByUsernameAndMissionId() {
        try {
            requesterMissionDataService.saveMission(mission);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(null, requesterMissionDataService.getInstanceBymissionId(1));
    }
}