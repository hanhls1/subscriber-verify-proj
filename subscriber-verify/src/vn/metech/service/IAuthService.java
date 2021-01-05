package vn.metech.service;

import vn.metech.dto.response.UserLoginResponse;
import vn.metech.entity.User;
import vn.metech.exception.UnauthorizedException;
import vn.metech.exception.UserInvalidException;
import vn.metech.exception.UserNotFoundException;
import vn.metech.shared.AuthorizeRequest;

public interface IAuthService {

	UserLoginResponse login(String username, String password, String partnerCode) throws UserInvalidException;

	String generateToken(User user);

	User parseToken(String token) throws UserNotFoundException;

	String isAccepted(AuthorizeRequest authRequest) throws UserNotFoundException, UnauthorizedException;

}
