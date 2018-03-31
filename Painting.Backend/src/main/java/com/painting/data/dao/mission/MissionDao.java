package com.painting.data.dao.mission;

import org.springframework.stereotype.Service;
import trapx00.tagx00.entity.mission.Mission;

import java.util.ArrayList;

@Service
public interface MissionDao {

    Mission saveMission(Mission mission);

    Mission findMissionByMissionId(int missionId);

    ArrayList<Mission> findMissionByRequesterUsername(String requesterUsername);

    ArrayList<Mission> findAll();
}
