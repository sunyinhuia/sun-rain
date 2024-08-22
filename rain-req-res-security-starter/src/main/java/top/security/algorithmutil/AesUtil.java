package top.security.algorithmutil;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    private static AES aes;

    public static void setAes(AES aes){
        AesUtil.aes = aes;
    }

    public static String encrypt(String content){
        return aes.encryptBase64(content);
    }

    public static String decrypt(String content) {
        return aes.decryptStr(content);
    }



    public static void main(String[] args) {
        String key = "0D7FB71E8EC31E97";
        String iv = "TRYTOCN394402133";
        AES aess = new AES(Mode.CBC, Padding.ZeroPadding,
                new SecretKeySpec( key.getBytes(), "AES"),
                new IvParameterSpec( iv.getBytes()));
        String s = aess.encryptBase64("1");
        System.out.println(s);

    }



}
