package com.czxy.xuecheng.common.exception;

import com.czxy.xuecheng.common.model.response.ResultCode;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类型
 * @version 1.0
 **/
public class CustomException extends RuntimeException {

    //错误代码
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public CustomException(){
        System.out.println("自定义默认构造");
    }
    public ResultCode getResultCode(){
        return resultCode;
    }


}
