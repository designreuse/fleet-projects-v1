package com.comodin.fleet.util;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.ParameterException;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Validation util.
 */
public class ValidationUtil {
    private static final Logger log = Logger.getLogger(ValidationUtil.class);

    /**
     * 先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
     *
     * @param bindingResult the binding result
     * @return string
     */
    public static String WebRequestParametersValidation(BindingResult bindingResult) {

        String resultMessage = "";

//        if (bindingResult.hasErrors()) {
//            List<ObjectError> allErrors = bindingResult.getAllErrors();
//            for (ObjectError error : allErrors) {
//                System.out.println(error.getDefaultMessage());
//            }
//            log.info(JSON.toJSONString(allErrors));
//        }

        if (bindingResult.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                map.put(fieldError.getField().toString(), fieldError.getDefaultMessage());
            }
            resultMessage = JSON.toJSONString(map);
            log.error("web request parameters valid data format error JSON: " + resultMessage);
            throw new ParameterException(resultMessage);
        }

        return resultMessage;
    }
}
