package com.razu.utils;

import static com.razu.utils.ResponseCode.CODE_201;
import static com.razu.utils.ResponseTagName.MESSAGE;
import static com.razu.utils.ResponseTagName.STATUS;
import static com.razu.utils.ResponseTagName.STATUS_CODE;
import java.util.LinkedHashMap;
import org.springframework.validation.BindingResult;

public class ErrorUtils {

    public static LinkedHashMap userError(BindingResult result) {
        LinkedHashMap serviceResponse = new LinkedHashMap<String, Object>();
        serviceResponse.put(STATUS, Boolean.TRUE);
        serviceResponse.put(STATUS_CODE, CODE_201);
        serviceResponse.put(MESSAGE, result.getAllErrors().get(0).getDefaultMessage());
        return serviceResponse;
    }
}
