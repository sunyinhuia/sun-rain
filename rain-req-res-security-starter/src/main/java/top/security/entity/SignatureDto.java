package top.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignatureDto {

    private String nonceStr;

    private String timestamp;

    private String data;

    private String signature;

}
