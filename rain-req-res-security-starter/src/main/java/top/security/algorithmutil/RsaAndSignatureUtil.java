package top.security.algorithmutil;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.date.DateUtil;
import top.security.entity.KeyStr;
import top.security.entity.SignatureDto;
import top.security.enums.Algorithm;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author sun_yinhui
 * @version 1.0.0
 * @ClassName PayCertUtil.java
 * @Description
 * @createTime 2022年01月27日 14:50:00
 */
public class RsaAndSignatureUtil {

    /**
     * 获取密钥对
     * @return
     * @throws Exception
     */
    public static KeyStr getKeyPairStr() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Algorithm.RSA.name());
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return KeyStr.builder()
                .privateKey(Base64Encoder.encode(keyPair.getPrivate().getEncoded()))
                .publicKey(Base64Encoder.encode(keyPair.getPublic().getEncoded()))
                .build();
    }

    /**
     * RSA加密
     * @param data 待加密数据
     * @param publicKeyStr 公钥
     * @return
     */
    public static String encrypt(String data,String publicKeyStr) {
        try {
            Cipher encryptCipher = Cipher.getInstance(Algorithm.RSA.name());
            encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyStr));
            byte[] ciphertext = encryptCipher.doFinal(data.getBytes());
            return Base64Encoder.encode(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException("RSA加密失败");
        }
    }

    /**
     * RSA解密
     * @param data 待解密数据
     * @param privateKeyStr 私钥
     * @return
     */
    public static String decrypt(String data,String privateKeyStr){
        try {
            Cipher decryptCipher = Cipher.getInstance(Algorithm.RSA.name());
            decryptCipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyStr));
            byte[] plaintextBytes = decryptCipher.doFinal(Base64Decoder.decode(data));
            return new String(plaintextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("RSA解密失败" );
        }
    }

    public static String getSignature(String data,String privateKeyStr) {
        try{
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(getPrivateKey(privateKeyStr));
            sig.update(data.getBytes());
            byte[] signature = sig.sign();
            return Base64Encoder.encode(signature);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("签名失败");
        }
    }

    public static boolean verifySignature(String data,String signatureData,String publicKeyStr){
        try{
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(getPublicKey(publicKeyStr));
            sig.update(data.getBytes());
            return sig.verify(Base64Decoder.decode(signatureData));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("签名验证失败");
        }
    }


    /**
     * 获取私钥
     * @return
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.name());
        byte[] decodedKey = Base64Decoder.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     * @return
     */
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm.RSA.name());
        byte[] decodedKey = Base64Decoder.decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }


    public static void main(String[] args) throws Exception {
//        KeyStr keyPairStr = getKeyPairStr();
//        System.out.println("privateKey:" + keyPairStr.getPrivateKey());
//        System.out.println("publicKey:" + keyPairStr.getPublicKey());

        String data = "123456+=/*&^%$@#.";
//        String encrypt = encrypt(data, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxKUqVmUqLGNNkyUorhaDfx+wbY+Yj3NgAtHRkTxaCmEocWuifLSke9LKNsUxdl9ZB2qyAsYmNteho/+mZyu5fDR7QLyGjOH6/tYK6YNvJZkqLrzwqSgDdNR9ODq4R/5WBCdKUT+k2BW9J0NFAX2mqONngVzKUDIl+KGC9Tprxbgz1n2ls+EAqLcME1m9BgAyOpd9SfuH1y0JhSS7/Kzx2WNUt3fNqvbZcbq6ZdxHtwptirJK5l6Bnuyo/yFerSP85T/JNG8R1jBzcttZYDlpZzVuMLt8DJODcpl67MwauSTt2bila1N4aRDFbK7t9jYrK0twKyYnKjkZlm6US74IkwIDAQAB");
//        System.out.println("encrypt:" + encrypt);
//        String decrypt = decrypt(encrypt, "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDEpSpWZSosY02TJSiuFoN/H7Btj5iPc2AC0dGRPFoKYShxa6J8tKR70so2xTF2X1kHarICxiY216Gj/6ZnK7l8NHtAvIaM4fr+1grpg28lmSouvPCpKAN01H04OrhH/lYEJ0pRP6TYFb0nQ0UBfaao42eBXMpQMiX4oYL1OmvFuDPWfaWz4QCotwwTWb0GADI6l31J+4fXLQmFJLv8rPHZY1S3d82q9tlxurpl3Ee3Cm2KskrmXoGe7Kj/IV6tI/zlP8k0bxHWMHNy21lgOWlnNW4wu3wMk4NymXrszBq5JO3ZuKVrU3hpEMVsru32NisrS3ArJicqORmWbpRLvgiTAgMBAAECggEAWLDfyAinvU5qghfL4bzQwSjTQ4708ZpJbuwgV59/QtgwS/uOVS2KVDitsIc++FMnzeMO95ZUTP7zbgrTjAvaBE2NW5TMIMWZlY+Ha2u22R/cOSlJaBneeTaPZamCTSJGvGfWKpAIctdXMVqzSGlaHik2mU93BjWgD6QU8zLOweU3ntDwJS8QnOPG+Ohm6zD1tWGXTDLfxV+3FrqIFsy0vMhrFh+9pWkvEDEBf7gvaicgdmcE8aA6fGp9MknvwoN19nPv/zkWY0mAW2reBPGve9OYi/N67NCcV7K0xY6j7nVjrVyuk6NFc9UuhXJ9MDnw3oTacjmF4dSEfu4sfoNkhQKBgQD5hsV8Cva1sqvlul1cdMPa+qLuLxY+7FbZ6EQPGqOWiMRCqRdJPqzFpRkHgPK3ZI2ncNmWV45g2jCwhzvBoO9fJLf36Hs6QjuCQkDndf9Hfc67g3Em9f/BzLns6Cqy383YG3Gi8uzA8jvmZF4m8gKQFZim0p2nwH1y7GiAt4O27QKBgQDJvy7qXrq4ia9dKhsno56JqU114eA21fW/CTblI/2BC7b60r6gnwjPUc2+9gdgm2Ol20kSXa//9LGQTFukbnKCwdOBMQ9deZF+TQNZb9XYcBNmSc/xh6OgP89LXMNybn5Y8ubNQXIMa9ttv4sLyRqCpPRhGpdrWOSZy2+De8VNfwKBgQCkK/7dCR2fGGou4LoybYS7G7J9Qa/CQJ84CbBVu7IsBdoS/cubXkgwVdqs7DNFZDwrEX2ng/3iTUxBqQgw8mDr2W3NB0GkaBVdM5geca0+7VBKYUXeW3YlnTwq2enV/JgjepL+ohQRMo8km56ywwHaqMrh36roxyYCqfZr0dIwVQKBgBJuH7gTbVVcZqPJK+/kF6CV0di6CMSZqTiJe/VU41y9mDqzs+xyetiNrZELYsMljuXg8Xz3CBQG442+HAUafeWKFWtQ8wlkCLGUEYFDITMDkhIfHhr/fCgwjBxjXgt/IpW3ovQPTX7q/4G4JNivx3c14IlaZ4jL9x5Bom9NiEm7AoGBAImfZmHFjC7Rj8fruan8ikI79IgClbDvs7wkbKJpu3vpCJbh44Jqqa9cMj/GvHnz/wa2n8TkmzXSpSYaJ4Qa4YeU9lh4fAK/5cuCE5Izu19KZu5Msz5tyLrCS03426iPnlQ7W7s9fMJsvZW1iNw/gZ1yswxn0AHljJIyHqVnphG1");
//        System.out.println(  decrypt);
//        System.out.println(  data);

        String pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxKUqVmUqLGNNkyUorhaDfx+wbY+Yj3NgAtHRkTxaCmEocWuifLSke9LKNsUxdl9ZB2qyAsYmNteho/+mZyu5fDR7QLyGjOH6/tYK6YNvJZkqLrzwqSgDdNR9ODq4R/5WBCdKUT+k2BW9J0NFAX2mqONngVzKUDIl+KGC9Tprxbgz1n2ls+EAqLcME1m9BgAyOpd9SfuH1y0JhSS7/Kzx2WNUt3fNqvbZcbq6ZdxHtwptirJK5l6Bnuyo/yFerSP85T/JNG8R1jBzcttZYDlpZzVuMLt8DJODcpl67MwauSTt2bila1N4aRDFbK7t9jYrK0twKyYnKjkZlm6US74IkwIDAQAB";

        String pri = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDEpSpWZSosY02TJSiuFoN/H7Btj5iPc2AC0dGRPFoKYShxa6J8tKR70so2xTF2X1kHarICxiY216Gj/6ZnK7l8NHtAvIaM4fr+1grpg28lmSouvPCpKAN01H04OrhH/lYEJ0pRP6TYFb0nQ0UBfaao42eBXMpQMiX4oYL1OmvFuDPWfaWz4QCotwwTWb0GADI6l31J+4fXLQmFJLv8rPHZY1S3d82q9tlxurpl3Ee3Cm2KskrmXoGe7Kj/IV6tI/zlP8k0bxHWMHNy21lgOWlnNW4wu3wMk4NymXrszBq5JO3ZuKVrU3hpEMVsru32NisrS3ArJicqORmWbpRLvgiTAgMBAAECggEAWLDfyAinvU5qghfL4bzQwSjTQ4708ZpJbuwgV59/QtgwS/uOVS2KVDitsIc++FMnzeMO95ZUTP7zbgrTjAvaBE2NW5TMIMWZlY+Ha2u22R/cOSlJaBneeTaPZamCTSJGvGfWKpAIctdXMVqzSGlaHik2mU93BjWgD6QU8zLOweU3ntDwJS8QnOPG+Ohm6zD1tWGXTDLfxV+3FrqIFsy0vMhrFh+9pWkvEDEBf7gvaicgdmcE8aA6fGp9MknvwoN19nPv/zkWY0mAW2reBPGve9OYi/N67NCcV7K0xY6j7nVjrVyuk6NFc9UuhXJ9MDnw3oTacjmF4dSEfu4sfoNkhQKBgQD5hsV8Cva1sqvlul1cdMPa+qLuLxY+7FbZ6EQPGqOWiMRCqRdJPqzFpRkHgPK3ZI2ncNmWV45g2jCwhzvBoO9fJLf36Hs6QjuCQkDndf9Hfc67g3Em9f/BzLns6Cqy383YG3Gi8uzA8jvmZF4m8gKQFZim0p2nwH1y7GiAt4O27QKBgQDJvy7qXrq4ia9dKhsno56JqU114eA21fW/CTblI/2BC7b60r6gnwjPUc2+9gdgm2Ol20kSXa//9LGQTFukbnKCwdOBMQ9deZF+TQNZb9XYcBNmSc/xh6OgP89LXMNybn5Y8ubNQXIMa9ttv4sLyRqCpPRhGpdrWOSZy2+De8VNfwKBgQCkK/7dCR2fGGou4LoybYS7G7J9Qa/CQJ84CbBVu7IsBdoS/cubXkgwVdqs7DNFZDwrEX2ng/3iTUxBqQgw8mDr2W3NB0GkaBVdM5geca0+7VBKYUXeW3YlnTwq2enV/JgjepL+ohQRMo8km56ywwHaqMrh36roxyYCqfZr0dIwVQKBgBJuH7gTbVVcZqPJK+/kF6CV0di6CMSZqTiJe/VU41y9mDqzs+xyetiNrZELYsMljuXg8Xz3CBQG442+HAUafeWKFWtQ8wlkCLGUEYFDITMDkhIfHhr/fCgwjBxjXgt/IpW3ovQPTX7q/4G4JNivx3c14IlaZ4jL9x5Bom9NiEm7AoGBAImfZmHFjC7Rj8fruan8ikI79IgClbDvs7wkbKJpu3vpCJbh44Jqqa9cMj/GvHnz/wa2n8TkmzXSpSYaJ4Qa4YeU9lh4fAK/5cuCE5Izu19KZu5Msz5tyLrCS03426iPnlQ7W7s9fMJsvZW1iNw/gZ1yswxn0AHljJIyHqVnphG1";

        String signature = getSignature(data, pri);
        System.out.println(signature);

        boolean b = verifySignature(data, signature,pub);
        System.out.println(b);

        long currentTime = System.currentTimeMillis();
        SignatureDto dto = SignatureDto.builder()
                .timestamp(String.valueOf(currentTime))
                .data("jjkjhjkl")
                .build();


    }



}
