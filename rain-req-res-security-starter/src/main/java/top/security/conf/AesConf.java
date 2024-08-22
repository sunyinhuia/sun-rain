package top.security.conf;

import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import top.security.algorithmutil.AesUtil;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
@Slf4j
@Configuration
public class AesConf implements InitializingBean {
    @Autowired
    private GlobalProperties globalProperties;

    @Override
    public void afterPropertiesSet(){
        log.info("=======>初始化 AES 加密算法<=======");
        AES aes = new AES(globalProperties.getMode(), globalProperties.getPadding(),
                new SecretKeySpec(globalProperties.getKey().getBytes(), "AES"),
                new IvParameterSpec( globalProperties.getIv().getBytes()));
        AesUtil.setAes(aes);
    }



}
