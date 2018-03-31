package com.painting.dataservice.account;

import trapx00.tagx00.entity.account.User;
import trapx00.tagx00.exception.viewexception.SystemException;

public interface UserDataService {
    /**
     * find whether the user exists
     *
     * @param username the username
     * @return whether the user exists
     */
    boolean isUserExistent(String username);

    /**
     * save the user
     *
     * @param user the user to be saved
     */
    void saveUser(User user) throws SystemException;

    /**
     * confirm the password
     *
     * @param username the username
     * @param password the password
     * @return true if password is correct else false
     */
    boolean confirmPassword(String username, String password);

    /**
     * Removes a user.
     *
     * @param username username
     */
    void deleteUser(String username);

    /**
     * send email to an user
     *
     * @param email the email address
     */
    void sendEmail(String email);
}
