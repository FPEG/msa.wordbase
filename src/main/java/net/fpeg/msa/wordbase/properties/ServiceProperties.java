package net.fpeg.msa.wordbase.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//读取配置项
@Component
@ConfigurationProperties(prefix = "ohinc.services")
public class ServiceProperties {
    private String defaultTagValue = "默认";

    public String getDefaultTagValue() {
        return defaultTagValue;
    }

    public void setDefaultTagValue(String defaultTagValue) {
        this.defaultTagValue = defaultTagValue;
    }
}
