package vn.metech.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.metech.constant.StatusCode;

import java.util.ArrayList;
import java.util.List;

@RestController
@ControllerAdvice
public class BaseExceptionHanding extends Exception {

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public ActionResult baseErrorHandler(BaseException ex) {
        return new ExceptionResult(ex);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ActionResult errorHandler(Exception ex) {
        return new ExceptionResult(ex);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ActionResult msgNotReadHandler(HttpMessageNotReadableException ex) {
        return new ExceptionResult(ex);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ActionResult validationException(MethodArgumentNotValidException ex) {
        List<ValidationField> errors = new ArrayList<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError obj : objectErrors) {
            if (obj instanceof FieldError) {
                errors.add(new ValidationField((FieldError) obj));
            }
        }

        return new ActionResult(StatusCode.INPUT_ERROR, HttpStatus.OK, errors);
    }
}
