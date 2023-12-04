package com.adoption.controller.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.adoption.dto.ExceptionRespDTO;
import com.adoption.enums.ReturnCodeEnum;
import com.adoption.exception.DataNotFoundException;
import com.adoption.exception.DatabaseInteractFailedException;
import com.adoption.exception.DeletionFailedException;
import com.adoption.exception.InsertionFailedException;
import com.adoption.exception.UpdateFailedException;

@ControllerAdvice
public class WebExceptionHandler {
  
  @ResponseBody
  @ExceptionHandler(DatabaseInteractFailedException.class)
  public ExceptionRespDTO handleDatabaseInteractFailedException() {
    ExceptionRespDTO response  = new ExceptionRespDTO();
    response.setStatusCode(ReturnCodeEnum.DATABASE_INTERACT_FAIL.getCode());
    response.setStatus(ReturnCodeEnum.DATABASE_INTERACT_FAIL.getDesc());
    return  response;
  }
  
  @ResponseBody
  @ExceptionHandler( DataNotFoundException.class)
  public ExceptionRespDTO handleDataNotFoundException() {
    ExceptionRespDTO response  = new ExceptionRespDTO();
    response.setStatusCode(ReturnCodeEnum.DATA_NOT_FOUND.getCode());
    response.setStatus(ReturnCodeEnum.DATA_NOT_FOUND.getDesc());
    return  response;
  }
  
  @ResponseBody
  @ExceptionHandler(DeletionFailedException.class)
  public ExceptionRespDTO handleDeletionFailedException() {
    ExceptionRespDTO response  = new ExceptionRespDTO();
    response.setStatusCode(ReturnCodeEnum.DELETE_FAIL.getCode());
    response.setStatus(ReturnCodeEnum.DELETE_FAIL.getDesc());
    return  response;
  }
  
  @ResponseBody
  @ExceptionHandler(InsertionFailedException.class)
  public ExceptionRespDTO handleInsertionFailedException() {
    ExceptionRespDTO response  = new ExceptionRespDTO();
    response.setStatusCode(ReturnCodeEnum.INSERT_FAIL.getCode());
    response.setStatus(ReturnCodeEnum.INSERT_FAIL.getDesc());
    return  response;
  }
  
  //使用Valid驗證
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ExceptionRespDTO handleMissingFiedException() {
    ExceptionRespDTO response  = new ExceptionRespDTO();
    response.setStatusCode(ReturnCodeEnum.ERROR_INPUT.getCode());
    response.setStatus(ReturnCodeEnum.ERROR_INPUT.getDesc());
    return  response;
  }
  
  @ResponseBody
  @ExceptionHandler(UpdateFailedException.class)
  public ExceptionRespDTO handleUpdateFailedException() {
    ExceptionRespDTO response  = new ExceptionRespDTO();
    response.setStatusCode(ReturnCodeEnum.UPDATE_FAIL.getCode());
    response.setStatus(ReturnCodeEnum.UPDATE_FAIL.getDesc());
    return  response;
  }
}
