package com.project.dco_common.exception;

import com.project.dco_common.api.AppResponseEntity;
import com.project.dco_common.api.HttpStatusResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerApi extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ Exception.class })
    public AppResponseEntity<?> exceptionHandle(Exception ex) {
        HttpStatusResponse status = new HttpStatusResponse("500", 500, "Failed");
        return AppResponseEntity.withError(status);
    }
}
