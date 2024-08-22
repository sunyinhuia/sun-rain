package top.security.algorithmutil;

import top.security.annotation.SecuritySupport;
import top.security.conf.RsaConf;

/**
 * @author sunyh
 */
public class DecryptEncryptUtil {
    private DecryptEncryptUtil() {}

    public static String encrypt(String original, SecuritySupport securitySupport) {
        String algorithm = securitySupport.algorithm().name();
        if("AES".equals(algorithm)){
           return AesUtil.encrypt(original);
        }else{
            return RsaAndSignatureUtil.encrypt(original, RsaConf.publicKeyStr);
        }
    }

    public static String decrypt(String original, SecuritySupport securitySupport) {
        String algorithm = securitySupport.algorithm().name();
        if("AES".equals(algorithm)){
            return AesUtil.decrypt(original);
        }else{
            return RsaAndSignatureUtil.decrypt(original, RsaConf.privateKeyStr);
        }
    }




}
