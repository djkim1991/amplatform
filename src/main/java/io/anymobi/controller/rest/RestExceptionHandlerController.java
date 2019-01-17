package io.anymobi.controller.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@RestController
public class RestExceptionHandlerController {

    @RequestMapping(value = "/handling", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<String> handleCustomException(HttpServletRequest request) {
        String msg =  (String) request.getAttribute("msg");
        return new ResponseEntity<String>(msg, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/exceptionOnJsonRequest")
    public List<Map<String, Object>> exceptionOnJsonRequest() throws Exception{
        throw new Exception("Json Exception");
    }

    @GetMapping("/sqlExceptionOnJsonRequest")
    public List<Map<String, Object>> sqlExceptionOnJsonRequest() throws SQLException {
        throw new SQLException("SQLExceptin", new SQLException("SQL_101"));
    }
}