package top.rain.basic.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.rain.basic.R;
import top.rain.basic.security.entity.User;
import top.security.annotation.SecuritySupport;
import top.security.enums.Algorithm;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @PostMapping("/user")
    @SecuritySupport(encryptParam="data")
    public Object jjm(@RequestBody User user) {
        System.out.println("##########");
        System.out.println(user);
        System.out.println("##########");
        Object success = R.success(user.getUserExt());
        return success;
    }

    @PostMapping("/user1")
    @SecuritySupport(decryptParam="userExt")
    public Object jjm1(@RequestBody User user) {
        System.out.println("##########");
        System.out.println(user);
        System.out.println("##########");
        Object success = R.success(user.getUserExt());
        return success;
    }

    @PostMapping("/user2")
    @SecuritySupport(decryptParam="userExt",encryptParam="data",algorithm= Algorithm.RSA)
    public Object jjm2(@RequestBody User user) {
        System.out.println("##########");
        System.out.println(user);
        System.out.println("##########");
        Object success = R.success(user.getUserExt());
        return success;
    }

    @PostMapping("/user3")
    @SecuritySupport(decryptParam="id")
    public R<User> jjm3(@RequestBody User user) {
        System.out.println("##########");
        System.out.println(user);
        System.out.println("##########");
        R<User> success = R.success(user);
        return success;
    }


}
