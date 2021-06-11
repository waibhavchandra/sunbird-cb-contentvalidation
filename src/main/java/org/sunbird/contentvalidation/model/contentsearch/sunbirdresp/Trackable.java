package org.sunbird.contentvalidation.model.contentsearch.sunbirdresp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trackable {
    private String enabled;
    private String autoBatch;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getAutoBatch() {
        return autoBatch;
    }

    public void setAutoBatch(String autoBatch) {
        this.autoBatch = autoBatch;
    }
}
