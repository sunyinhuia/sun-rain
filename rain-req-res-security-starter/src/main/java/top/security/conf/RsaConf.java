package top.security.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RsaConf implements InitializingBean {
    @Autowired
    private GlobalProperties globalProperties;

    public static String publicKeyStr;

    public static String privateKeyStr;


    @Override
    public void afterPropertiesSet(){
        log.info("=======>初始化 rsa 加密算法<=======");
        RsaConf.publicKeyStr = globalProperties.getRsaPublicKey();
        RsaConf.privateKeyStr = globalProperties.getRsaPrivateKey();

    }



}
