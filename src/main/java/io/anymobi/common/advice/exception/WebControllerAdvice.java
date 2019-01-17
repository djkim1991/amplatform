package io.anymobi.common.advice.exception;

import io.anymobi.domain.dto.exception.ExceptionDto;
import io.anymobi.domain.dto.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice{

    @ExceptionHandler(SQLException.class)
    public String sqlException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ExceptionDto exception = new ExceptionDto("SQLException occurred", e.getCause().getMessage(), ExceptionType.SQL);
        request.setAttribute("msg", exception.toString());
        log.info(exception.toString());

        return "forward:/handling";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ExceptionDto exception = new ExceptionDto("Exception on server occurred", e.toString(), ExceptionType.SERVER);
        request.setAttribute("msg", exception.toString());
        log.info(exception.toString());

        return "forward:/handling";
    }

}
