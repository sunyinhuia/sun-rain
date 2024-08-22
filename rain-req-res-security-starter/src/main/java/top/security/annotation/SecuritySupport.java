package top.security.annotation;

import top.security.enums.Algorithm;

import java.lang.annotation.*;

/**
 * @author sunyinhui
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface SecuritySupport {

    //加密解密处理器
    String securityHandler() default "encryAdecryImpl";

    //加密参数名（返回前端加密参数名）
    String encryptParam() default "";

    //解密参数名（前端请求后端待解密参数名）
    String decryptParam() default "";

    //加密算法
    Algorithm algorithm() default Algorithm.AES;
}
