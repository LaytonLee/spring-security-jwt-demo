package com.layton.demojwt.common.handler;

import com.layton.demojwt.common.exception.ErrorHandler;
import com.layton.demojwt.common.exception.ResultCodeEnum;
import com.layton.demojwt.common.exception.UserNotExistException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    /**
     * 对服务器端出现500异常进行统一处理
     * 缺点：不明确
     * 场景：
     */
    @ExceptionHandler(Throwable.class)
    public ErrorHandler makeExcepton(Throwable e, HttpServletRequest request) {
        ErrorHandler errorHandler = ErrorHandler.fail(ResultCodeEnum.SERVER_ERROR, e);
        return errorHandler;
    }

    /**
     * 对自定义异常进行封装
     * @param userNotExistException
     * @param request
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
    public ErrorHandler updateUserException(UserNotExistException userNotExistException, HttpServletRequest request) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setStatus(userNotExistException.getCode());
        errorHandler.setMessage(userNotExistException.getMessage());

        return errorHandler;
    }

    /**
     * 对验证异常进行统一处理提取需要的部分
     *
     * @param fieldErrorList
     * @return
     */
    private List<Map<String, String>> toValidatorMsg(List<FieldError> fieldErrorList) {
        List<Map<String, String>> mapList = new ArrayList<>();
        // 循环提取
        for (FieldError fieldError : fieldErrorList) {
            Map<String, String> map = new HashMap<>();
            // 获取验证失败的属性
            map.put("field", fieldError.getField());
            // 获取验证失败的的提示信息
            map.put("msg", fieldError.getDefaultMessage());

            mapList.add(map);
        }
        return mapList;
    }

}
