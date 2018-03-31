package com.painting.util;

import com.painting.entity.account.User;
import trapx00.tagx00.vo.user.UserSaveVo;

public class Convertor {
    /**
     * convert userSaveVo to user
     *
     * @param userSaveVo the userSaveVo
     * @return the user
     */
    public static User userSaveVoToUser(UserSaveVo userSaveVo) {
        return new User(userSaveVo.getUsername(), userSaveVo.getPassword(), userSaveVo.getEmail(), userSaveVo.getRoles());
    }
}
