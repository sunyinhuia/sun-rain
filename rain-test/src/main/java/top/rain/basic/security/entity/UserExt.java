package top.rain.basic.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExt {
    String name;
    Integer age;
    String phone;
    String address;
    String email;
    Ext1 ext1;

}
