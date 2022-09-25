package com.digitalbook.service;

import com.digitalbook.entity.ERole;
import com.digitalbook.entity.User;

/**
 * 
 * @author supriya
 * This is UserService interface which used for defining user details methods
 *
 */

public interface UserService {

	User getUser(int userId, ERole roleUser);
	
}
