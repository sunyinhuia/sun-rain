package top.security.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author sunyh
 */
public class SecurityUtil {

    /**
     * 处理json对象参数
     * @param jsonObject
     * @param param 参数名
     * @param paramValue 参数值
     */
    public static void handleJSONObjectParam(JSONObject jsonObject, String param, String paramValue) {
        if(isValidJson(paramValue)){
            jsonObject.put(param, JSON.parseObject(paramValue));
        }else if(isValidArray(paramValue)){
            jsonObject.put(param, JSON.parseArray(paramValue));
        }else{
            jsonObject.put(param, paramValue);
        }
    }

    public static boolean isValidJson(String str) {
        try {
            JSON.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidArray(String str) {
        try {
            JSON.parseArray(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
