package vn.metech.exception;

import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public String getStatusCode() {
        return StatusCode.User.USER_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return HttpStatus.OK;
    }
}
