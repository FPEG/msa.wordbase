package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.common.dto.*;
import net.fpeg.msa.wordbase.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseDto handleRecordExistException(UserException ex) {
        return new BaseDto(ex.getMessage());
    }

}
