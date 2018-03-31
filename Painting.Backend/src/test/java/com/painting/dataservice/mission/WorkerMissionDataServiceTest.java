package com.painting.dataservice.mission;

import com.painting.exception.viewexception.MissionAlreadyAcceptedException;
import com.painting.exception.viewexception.SystemException;
import com.painting.publicdatas.instance.MissionInstanceState;
import com.painting.vo.mission.instance.InstanceDetailVo;
import com.painting.vo.mission.instance.InstanceVo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trapx00.tagx00.exception.viewexception.MissionAlreadyAcceptedException;
import trapx00.tagx00.exception.viewexception.SystemException;
import trapx00.tagx00.publicdatas.instance.MissionInstanceState;
import trapx00.tagx00.vo.mission.instance.InstanceDetailVo;
import trapx00.tagx00.vo.mission.instance.InstanceVo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerMissionDataServiceTest {
    @Autowired
    private WorkerMissionDataService workerMissionDataService;
    private InstanceDetailVo missionInstanceItem = new InstanceDetailVo(new InstanceVo(1, "张三",
            MissionInstanceState.SUBMITTED, 1, new Date(), new Date(), false, 100));

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveInstance() throws SystemException {
        int result = 0;
        try {
            result = workerMissionDataService.saveInstance(missionInstanceItem);
        } catch (MissionAlreadyAcceptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    @Test
    public void getMissionByUsername() throws SystemException {
        try {
            workerMissionDataService.saveInstance(missionInstanceItem);
        } catch (MissionAlreadyAcceptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, workerMissionDataService.getInstanceByWorkerUsername("张三")[0].getMissionId());
    }

    @Test
    public void getInstanceByUsernameAndMissionId() throws SystemException {
        try {
            workerMissionDataService.saveInstance(missionInstanceItem);
        } catch (MissionAlreadyAcceptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("张三", workerMissionDataService.getInstanceByUsernameAndMissionId("张三", 1).getInstance().getWorkerUsername());
    }
}