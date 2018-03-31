package com.painting.data.dao.user;


import com.painting.entity.account.User;
import org.springframework.stereotype.Service;

/**
 * 这里的方法签名要注意 严格按照下面的格式 这是将来用数据库使用jpa时api自带的方法签名
 */
@Service
public interface UserDao {
    User save(User user);

    User findUserByUsername(String username);
}
