package tasks.manager.api.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tasks.manager.api.records.ErrorRecord;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorRecord handleException(MethodArgumentNotValidException e) {
        Map<String, String> res = new HashMap<>();

        for (FieldError error : e.getFieldErrors()) {
            res.put(error.getField(), error.getDefaultMessage());
        }

        return new ErrorRecord(res);
    }
}
