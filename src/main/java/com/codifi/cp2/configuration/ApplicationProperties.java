package com.codifi.cp2.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "appconfig")
@Configuration
public class ApplicationProperties {

    public Urls urls = new Urls();
    public Commons commons = new Commons();

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Commons getCommons() {
        return commons;
    }

    public void setCommons(Commons commons) {
        this.commons = commons;
    }

    public static class Urls {
        private String requestNo;

        public String getRequestNo() {
            return requestNo;
        }

        public void setRequestNo(String requestNo) {
            this.requestNo = requestNo;
        }

    }

    public static class Commons {
        private String schemeName;

        public String getSchemeName() {
            return schemeName;
        }

        public void setSchemeName(String schemeName) {
            this.schemeName = schemeName;
        }

    }

}
