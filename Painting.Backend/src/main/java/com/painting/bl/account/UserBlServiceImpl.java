package com.painting.bl.account;

import com.painting.blservice.account.UserBlService;
import com.painting.dataservice.account.UserDataService;
import com.painting.entity.account.User;
import com.painting.exception.viewexception.SystemException;
import com.painting.exception.viewexception.UserAlreadyExistsException;
import com.painting.exception.viewexception.WrongUsernameOrPasswordException;
import com.painting.response.user.UserLoginResponse;
import com.painting.response.user.UserRegisterResponse;
import com.painting.security.jwt.JwtRole;
import com.painting.security.jwt.JwtService;
import com.painting.security.jwt.JwtUser;
import com.painting.util.Convertor;
import com.painting.vo.user.UserSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserBlServiceImpl implements UserBlService {

    private final static long EXPIRATION = 604800;

    private final UserDataService userDataService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public UserBlServiceImpl(UserDataService userDataService, @Qualifier("jwtUserDetailsServiceImpl") UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDataService = userDataService;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * user sign up
     *
     * @param userSaveVo the user info to be saved
     * @return the register info to response
     * @throws UserAlreadyExistsException the user already exists
     * @throws SystemException            the system has error
     */
    @Override
    public UserRegisterResponse signUp(UserSaveVo userSaveVo) throws UserAlreadyExistsException, SystemException {
        if (userDataService.isUserExistent(userSaveVo.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String rawPassword = userSaveVo.getPassword();
            userSaveVo.setPassword(encoder.encode(rawPassword));

            User user = Convertor.userSaveVoToUser(userSaveVo);
            JwtUser jwtUser = jwtService.convertUserToJwtUser(user);
            userDataService.saveUser(user);
            String token = jwtService.generateToken(jwtUser, EXPIRATION);
            String email = jwtUser.getEmail();
            Collection<JwtRole> jwtRoles = jwtUser.getAuthorities();
            return new UserRegisterResponse(token, jwtRoles, email);
        }
    }

    /**
     * login
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the login info to response
     * @throws WrongUsernameOrPasswordException the username or password is error
     */
    @Override
    public UserLoginResponse login(String username, String password) throws WrongUsernameOrPasswordException {
        if (userDataService.confirmPassword(username, password)) {
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
            String token = jwtService.generateToken(jwtUser, EXPIRATION);
            String email = jwtUser.getEmail();
            Collection<JwtRole> jwtRoles = jwtUser.getAuthorities();
            return new UserLoginResponse(token, jwtRoles, email);
        } else {
            throw new WrongUsernameOrPasswordException();
        }
    }
}
