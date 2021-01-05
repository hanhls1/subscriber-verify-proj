package vn.metech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import vn.metech.util.StringUtils;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private int exp;
    private String secretKey;
    private String issuer;

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getSecretKey() {
        if (StringUtils.isEmpty(secretKey)) {
            secretKey = "dm4ubWV0ZWNoLnN1YnNjcmliZXItdmVyaWZ5";
        }

        return StringUtils.fromBase64(secretKey);
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
