package b_yousefi.bookshop.usersws.service;

import b_yousefi.bookshop.usersws.data.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by: b.yousefi
 * Date: 8/9/2020
 */
public interface UserService extends UserDetailsService {
    User createUser(User user);

    User getUserDetailByUserName(String email);

    User getUserByUserId(String userId);

    User updateUser(User user);
}