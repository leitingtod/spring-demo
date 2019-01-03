package com.example.blog.api.configuration;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

  public String getErrorPath() {
    log.trace("Error Controller triggered ...");
    return "error";
  }

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request) {
    log.trace("/error handler triggered ...");
    return "";
  }
}
