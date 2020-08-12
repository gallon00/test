package com.czxy.xuecheng;

import com.czxy.xuecheng.common.exception.CustomException;
import com.czxy.xuecheng.common.exception.ExceptionCast;
import com.czxy.xuecheng.common.model.response.CommonCode;

/**
 * Created by liangtong.
 */
public class TestException {
    public static void main(String[] args) {
        //throw new CustomException(CommonCode.FAIL);
        ExceptionCast.cast(CommonCode.FAIL);
    }
}
