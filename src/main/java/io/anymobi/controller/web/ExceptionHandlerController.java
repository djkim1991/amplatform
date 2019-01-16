package io.anymobi.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;


@Controller
public class ExceptionHandlerController {

    @RequestMapping(value = "/handling")
    public String sampleTest(HttpServletRequest request, Model model) {
        String msg = (String) request.getAttribute("msg");
        model.addAttribute("msg", msg);
        return "/error";
    }

    @GetMapping("/exceptionOnHtmlRequst")
    public String exceptionOnHtmlRequst() throws NullPointerException {
        throw new NullPointerException();
    }

    @GetMapping("/sqlExceptionOnHtmlRequest")
    public String sqlExceptionOnHtmlRequest() throws SQLException {
        throw new SQLException("SQLExceptin", new SQLException("SQL_101"));
    }

}