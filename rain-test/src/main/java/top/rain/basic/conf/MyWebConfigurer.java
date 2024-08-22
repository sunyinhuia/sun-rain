package top.rain.basic.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry corsRegistry){
    /**
    * 所有请求都允许跨域，使用这种配置就不需要
    * 在 interceptor 中配置 header 了
    */
    corsRegistry.addMapping("/**")
       .allowCredentials(false)
        .allowedOrigins("*")
       .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")
       .allowedHeaders("*")
       .maxAge(3600);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor(){}).addPathPatterns("/**");
  }
}