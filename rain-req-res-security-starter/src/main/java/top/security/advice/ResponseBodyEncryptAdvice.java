package top.security.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.security.conf.GlobalProperties;
import top.security.service.SecurityHandler;
import top.security.annotation.SecuritySupport;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
@ConditionalOnProperty(name = "rain.security.repadvice", havingValue = "true",matchIfMissing = true)
public class ResponseBodyEncryptAdvice implements ResponseBodyAdvice {
    private final ApplicationContext applicationContext;
    private final GlobalProperties globalProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(SecuritySupport.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        SecuritySupport securitySupport = returnType.getMethodAnnotation(SecuritySupport.class);
        if(securitySupport == null){
            return body;
        }
        log.info("进入ResponseBodyEncryptAdvice处理:{}",returnType.getMethod());
        SecurityHandler securityHandler = applicationContext.getBean(securitySupport.securityHandler(),SecurityHandler.class);
        if(securityHandler == null){
            return body;
        }
        String parm = securitySupport.encryptParam();
        if(StringUtils.isBlank(parm)){
            parm = globalProperties.getEncryptParam();
        }
        if(StringUtils.isBlank(parm)){
            return body;
        }
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(body));
        String data = jsonObject.getString(parm);
        if(StringUtils.isNotBlank(data)){
            String encrypt = securityHandler.encrypt(data,securitySupport);
            jsonObject.put(parm, encrypt);
        }
        return jsonObject;
    }
}
