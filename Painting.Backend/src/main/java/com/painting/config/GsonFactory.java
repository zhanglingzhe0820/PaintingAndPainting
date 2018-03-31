package com.painting.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.painting.config.jsonAdapter.MissionPropertiesAdapter;
import trapx00.tagx00.config.jsonAdapter.ImageJobAdapter;
import trapx00.tagx00.publicdatas.mission.image.ImageJob;
import trapx00.tagx00.vo.mission.missiontype.MissionProperties;

public class GsonFactory {
    public static Gson get() {
        return new GsonBuilder()
                .registerTypeAdapter(ImageJob.class, new ImageJobAdapter())
                .registerTypeAdapter(MissionProperties.class, new MissionPropertiesAdapter())
                .create();
    }
}
