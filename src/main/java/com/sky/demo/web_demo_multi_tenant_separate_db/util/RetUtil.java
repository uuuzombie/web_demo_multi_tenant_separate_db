package com.sky.demo.web_demo_multi_tenant_separate_db.util;

import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetData;
import com.sky.demo.web_demo_multi_tenant_separate_db.base.RetStatus;

/**
 * Created by rg on 2015/6/17.
 */
public class RetUtil {

    public static <T> RetData<T> buildSuccessRet(T data) {
        RetData<T> retData = new RetData<T>();
        retData.setCode(RetStatus.SUCCESS.getCode());
        retData.setMessage("success");
        retData.setData(data);

        return retData;
    }

    public static <T> RetData<T> buildErrorRet(RetStatus retStatus) {
        RetData<T> retData = new RetData<T>();
        retData.setCode(retStatus.getCode());
        retData.setMessage(retStatus.getDesc());
        retData.setData(null);

        return retData;
    }

    public static <T> RetData<T> buildErrorRet(RetStatus retStatus, String msg) {
        RetData<T> retData = new RetData<T>();
        retData.setCode(retStatus.getCode());
        retData.setMessage(msg);
        retData.setData(null);

        return retData;
    }

}
