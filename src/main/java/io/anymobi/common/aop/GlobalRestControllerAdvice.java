package io.anymobi.common.aop;

import io.anymobi.domain.dto.exception.ExceptionDto;
import io.anymobi.common.enums.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Package : io.anymobi.common.advice
 * Developer Team : Anymobi System Development Division
 * Date : 2019-02-16
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalRestControllerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        ExceptionDto exception = new ExceptionDto("Exception on server occurred", e.toString(), ExceptionType.SERVER);

        return new ResponseEntity(exception.toString(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
