package top.security.service.impl;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.security.algorithmutil.DecryptEncryptUtil;
import top.security.annotation.SecuritySupport;
import top.security.service.SecurityHandler;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author sunyinhui
 */
@Slf4j
@Component
public class EncryAdecryImpl implements SecurityHandler {


    @Override
    public String encrypt(String original, SecuritySupport securitySupport) {
        log.info("【starter】具体加密的数据{}" , original);
        return DecryptEncryptUtil.encrypt(original,securitySupport);
    }

    @Override
    public String decrypt(String original,SecuritySupport securitySupport) {
        String decryptDara = DecryptEncryptUtil.decrypt(original,securitySupport);
        log.info("【starter】具体解密的数据{}" , decryptDara);
        return decryptDara;
    }




    public static void main(String[] args) {

        String key = "0D7FB71E8EC31E97";
        String iv = "TRYTOCN394402133";
        AES aess = new AES(Mode.CBC, Padding.ZeroPadding,
                new SecretKeySpec( key.getBytes(), "AES"),
                new IvParameterSpec( iv.getBytes()));
        String s = aess.encryptBase64("123456+=/*&^%$@#.");

        System.out.println(s);

    }
}
