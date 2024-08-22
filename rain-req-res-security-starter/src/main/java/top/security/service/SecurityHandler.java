package top.security.service;

import top.security.annotation.SecuritySupport;

public interface SecurityHandler{

    String encrypt(String original, SecuritySupport securitySupport);

    String decrypt(String original,SecuritySupport securitySupport);


}
