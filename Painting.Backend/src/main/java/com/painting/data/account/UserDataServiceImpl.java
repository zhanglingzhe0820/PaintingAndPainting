package com.painting.data.account;

import com.painting.dataservice.account.UserDataService;
import com.painting.entity.account.User;
import com.painting.exception.viewexception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import trapx00.tagx00.data.dao.user.UserDao;

@Service
public class UserDataServiceImpl implements UserDataService {
    private final UserDao userDao;
    private final JavaMailSender mailSender;

    @Value("${email.sender}")
    private String senderEmail;
    @Value("${email.subject}")
    private String subject;
    @Value("${email.content1}")
    private String content1;
    @Value("${email.content2}")
    private String content2;

    @Autowired
    public UserDataServiceImpl(UserDao userDao, JavaMailSender mailSender) {
        this.userDao = userDao;
        this.mailSender = mailSender;
    }


    /**
     * find whether the user exists
     *
     * @param username the username
     * @return whether the user exists
     */
    @Override
    public boolean isUserExistent(String username) {
        return userDao.findUserByUsername(username) != null;
    }

    /**
     * save the user
     *
     * @param user the user to be saved
     */
    @Override
    public void saveUser(User user) throws SystemException {
        if (userDao.save(user) == null) {
            throw new SystemException();
        }
    }

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    @Override
    public boolean confirmPassword(String username, String password) {
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Removes a user. No exception is thrown if username doesn't exist.
     *
     * @param username username
     */
    @Override
    public void deleteUser(String username) {

    }

    /**
     * send email to an user
     *
     * @param email the email address
     */
    @Override
    public void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String content = content1 + generateSecurityCode() + content2;
        message.setFrom(senderEmail);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    private String generateSecurityCode() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append((int) Math.floor(Math.random() * 10));
        }
        return new String(result);
    }
}
