package io.anymobi.common.advice;

import io.anymobi.domain.dto.exception.ExceptionDto;
import io.anymobi.domain.dto.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Package : io.anymobi.common.advice
 * Developer Team : Anymobi System Development Division
 * Date : 2019-02-16
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class GlobalWebControllerAdvice {

    @ExceptionHandler(SQLException.class)
    public String sqlException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ExceptionDto exception = new ExceptionDto("SQLException occurred", e.getCause().getMessage(), ExceptionType.SQL);
        request.setAttribute("msg", exception.toString());

        return exception.toString();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(HttpServletRequest request, Exception e) {
        ExceptionDto exception = new ExceptionDto("Exception on server occurred", e.toString(), ExceptionType.SERVER);
        request.setAttribute("msg", exception.toString());

        return exception.toString();
    }
}
