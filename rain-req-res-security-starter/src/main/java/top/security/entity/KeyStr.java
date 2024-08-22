package top.security.entity;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyStr {
    private String publicKey;
    private String privateKey;

    public static void main(String[] args) throws Exception {
        KeyStr k1 = new KeyStr("1", "2");
        KeyStr k2 = new KeyStr(null, "4");

        Class<KeyStr> cls = KeyStr.class;

        Map<String, Object> map = BeanUtil.beanToMap(k2,false,true);
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(map.containsKey(declaredField.getName())){
                declaredField.setAccessible(true);
                declaredField.set(k1, map.get(declaredField.getName()));
            }
        }
        System.out.println(k1);

    }




}
