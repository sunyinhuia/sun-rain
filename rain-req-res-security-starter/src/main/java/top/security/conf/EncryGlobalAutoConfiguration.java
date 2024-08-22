package top.security.conf;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "top.security")
@EnableConfigurationProperties(GlobalProperties.class)
public class EncryGlobalAutoConfiguration {
}
