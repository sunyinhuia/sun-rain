package top.security.advice;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import top.security.service.SecurityHandler;
import top.security.annotation.SecuritySupport;
import top.security.conf.GlobalProperties;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
@ConditionalOnProperty(name = "rain.security.reqadvice", havingValue = "true",matchIfMissing = true)
public class RequestBodyDecryptAdvice implements RequestBodyAdvice {
    private final GlobalProperties globalProperties;
    private final ApplicationContext applicationContext;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(SecuritySupport.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        SecuritySupport securitySupport = parameter.getMethodAnnotation(SecuritySupport.class);
        if(securitySupport == null){
            return inputMessage;
        }
        String handler = securitySupport.securityHandler();
        InputStream body = inputMessage.getBody();
        String parm = securitySupport.decryptParam();
        if(StringUtils.isBlank(parm)){
            parm = globalProperties.getDecryptParam();
        }
        if(StringUtils.isBlank(parm)){
            return new MappingJacksonInputMessage(body, inputMessage.getHeaders());
        }
        byte[] bytes = IoUtil.readBytes(body);
        String value =new String(bytes, StandardCharsets.UTF_8);
        log.info("请求体 : {}", value);

        JSONObject jsonObject = JSON.parseObject(value);
        String plainText = jsonObject.getString(parm);
        if (jsonObject.containsKey(parm) && StringUtils.isNotBlank(handler)&& StringUtils.isNotBlank(plainText)){
            SecurityHandler securityHandler = applicationContext.getBean(handler, SecurityHandler.class);
            log.info("解密参数: {}, 解密前值: {}",parm, plainText);
            plainText = securityHandler.decrypt(plainText,securitySupport);
            log.info("解密参数: {}, 解密后值: {}",parm,plainText);
            SecurityUtil.handleJSONObjectParam(jsonObject,parm,plainText);
        }
        return new MappingJacksonInputMessage(IoUtil.toStream(jsonObject.toJSONString(), Charset.defaultCharset()), inputMessage.getHeaders());
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return null;
    }
}
