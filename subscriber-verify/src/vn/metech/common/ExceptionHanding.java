package vn.metech.common;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.metech.dto.response.ActionResult;
import vn.metech.exception.aio.ApiException;
import vn.metech.exception.aio.BaseException;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@ControllerAdvice
public class ExceptionHanding {

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ActionResult validationException(MethodArgumentNotValidException ex) {
        List<ValidationField> errors = new ArrayList<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError obj : objectErrors) {
            if (obj instanceof FieldError) {
                errors.add(new ValidationField((FieldError) obj));
            }
        }

        return ActionResult.of(null, null, StatusCode.PARAM_INVALID, null, errors, null);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(ApiException.class)
    public ActionResult apiException(ApiException ex) {
        ex.printStackTrace();
        return ActionResult.of(
                ex.getCommand(), ex.getServiceType(),
                ex.getStatusCode(), ex.getRequestId(), null, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public ActionResult baseException(BaseException ex) {
        ex.printStackTrace();
        return ActionResult.of(null, null, ex.getStatusCode(), null, null, ex.getMessage());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ActionResult exception(Exception ex) {
        ex.printStackTrace();
        return ActionResult.of(null, null, StatusCode.UNDEFINDED, null, null, StatusCode.UNDEFINDED.description);
    }
}
