package top.security.conf;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rain.security")
public class GlobalProperties {
    /**
     * 待加密参数（用于前端请求后端数据）
     */
    private String encryptParam;

    /**
     * 待解密参数（用于返回前端数据）
     */
    private String decryptParam;

    /**
     * 加解密算法
     */
    private String algorithmType = "AES";

    /*****************AES加密算法start*********************/
    //加解密key
    private String key = "0D7FB71E8EC31E97";

    private String iv = "TRYTOCN394402133";

    private Padding padding = Padding.ZeroPadding;

    private Mode mode = Mode.CBC;
    /*****************AES加密算法end*********************/


    /******************RSA加密算法start*********************/
    public String rsaPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxKUqVmUqLGNNkyUorhaDfx+wbY+Yj3NgAtHRkTxaCmEocWuifLSke9LKNsUxdl9ZB2qyAsYmNteho/+mZyu5fDR7QLyGjOH6/tYK6YNvJZkqLrzwqSgDdNR9ODq4R/5WBCdKUT+k2BW9J0NFAX2mqONngVzKUDIl+KGC9Tprxbgz1n2ls+EAqLcME1m9BgAyOpd9SfuH1y0JhSS7/Kzx2WNUt3fNqvbZcbq6ZdxHtwptirJK5l6Bnuyo/yFerSP85T/JNG8R1jBzcttZYDlpZzVuMLt8DJODcpl67MwauSTt2bila1N4aRDFbK7t9jYrK0twKyYnKjkZlm6US74IkwIDAQAB";

    public String rsaPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDEpSpWZSosY02TJSiuFoN/H7Btj5iPc2AC0dGRPFoKYShxa6J8tKR70so2xTF2X1kHarICxiY216Gj/6ZnK7l8NHtAvIaM4fr+1grpg28lmSouvPCpKAN01H04OrhH/lYEJ0pRP6TYFb0nQ0UBfaao42eBXMpQMiX4oYL1OmvFuDPWfaWz4QCotwwTWb0GADI6l31J+4fXLQmFJLv8rPHZY1S3d82q9tlxurpl3Ee3Cm2KskrmXoGe7Kj/IV6tI/zlP8k0bxHWMHNy21lgOWlnNW4wu3wMk4NymXrszBq5JO3ZuKVrU3hpEMVsru32NisrS3ArJicqORmWbpRLvgiTAgMBAAECggEAWLDfyAinvU5qghfL4bzQwSjTQ4708ZpJbuwgV59/QtgwS/uOVS2KVDitsIc++FMnzeMO95ZUTP7zbgrTjAvaBE2NW5TMIMWZlY+Ha2u22R/cOSlJaBneeTaPZamCTSJGvGfWKpAIctdXMVqzSGlaHik2mU93BjWgD6QU8zLOweU3ntDwJS8QnOPG+Ohm6zD1tWGXTDLfxV+3FrqIFsy0vMhrFh+9pWkvEDEBf7gvaicgdmcE8aA6fGp9MknvwoN19nPv/zkWY0mAW2reBPGve9OYi/N67NCcV7K0xY6j7nVjrVyuk6NFc9UuhXJ9MDnw3oTacjmF4dSEfu4sfoNkhQKBgQD5hsV8Cva1sqvlul1cdMPa+qLuLxY+7FbZ6EQPGqOWiMRCqRdJPqzFpRkHgPK3ZI2ncNmWV45g2jCwhzvBoO9fJLf36Hs6QjuCQkDndf9Hfc67g3Em9f/BzLns6Cqy383YG3Gi8uzA8jvmZF4m8gKQFZim0p2nwH1y7GiAt4O27QKBgQDJvy7qXrq4ia9dKhsno56JqU114eA21fW/CTblI/2BC7b60r6gnwjPUc2+9gdgm2Ol20kSXa//9LGQTFukbnKCwdOBMQ9deZF+TQNZb9XYcBNmSc/xh6OgP89LXMNybn5Y8ubNQXIMa9ttv4sLyRqCpPRhGpdrWOSZy2+De8VNfwKBgQCkK/7dCR2fGGou4LoybYS7G7J9Qa/CQJ84CbBVu7IsBdoS/cubXkgwVdqs7DNFZDwrEX2ng/3iTUxBqQgw8mDr2W3NB0GkaBVdM5geca0+7VBKYUXeW3YlnTwq2enV/JgjepL+ohQRMo8km56ywwHaqMrh36roxyYCqfZr0dIwVQKBgBJuH7gTbVVcZqPJK+/kF6CV0di6CMSZqTiJe/VU41y9mDqzs+xyetiNrZELYsMljuXg8Xz3CBQG442+HAUafeWKFWtQ8wlkCLGUEYFDITMDkhIfHhr/fCgwjBxjXgt/IpW3ovQPTX7q/4G4JNivx3c14IlaZ4jL9x5Bom9NiEm7AoGBAImfZmHFjC7Rj8fruan8ikI79IgClbDvs7wkbKJpu3vpCJbh44Jqqa9cMj/GvHnz/wa2n8TkmzXSpSYaJ4Qa4YeU9lh4fAK/5cuCE5Izu19KZu5Msz5tyLrCS03426iPnlQ7W7s9fMJsvZW1iNw/gZ1yswxn0AHljJIyHqVnphG1";
    /*****************RSA加密算法end*********************/



}
