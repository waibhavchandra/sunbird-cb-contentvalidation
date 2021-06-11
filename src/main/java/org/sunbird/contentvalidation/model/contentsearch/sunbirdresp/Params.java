package org.sunbird.contentvalidation.model.contentsearch.sunbirdresp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Params {
    private String resmsgid;
    private Object msgid;
    private Object err;
    private String status;
    private Object errmsg;

    public String getResmsgid() {
        return resmsgid;
    }

    public void setResmsgid(String resmsgid) {
        this.resmsgid = resmsgid;
    }

    public Object getMsgid() {
        return msgid;
    }

    public void setMsgid(Object msgid) {
        this.msgid = msgid;
    }

    public Object getErr() {
        return err;
    }

    public void setErr(Object err) {
        this.err = err;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(Object errmsg) {
        this.errmsg = errmsg;
    }
}
