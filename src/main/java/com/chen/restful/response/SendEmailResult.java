package com.chen.restful.response;

/**
 * @author chen
 * @date 2017/11/7
 */
public class SendEmailResult extends SuccessResult{

    private String sucEmails;

    private String failEmails;

    public static SendEmailResult INS(String sucEmails, String failEmails) {
        return new SendEmailResult(sucEmails, failEmails);
    }

    public SendEmailResult(String sucEmails, String failEmails) {
        this.sucEmails = sucEmails;
        this.failEmails = failEmails;
    }

    public String getSucEmails() {
        return sucEmails;
    }

    public void setSucEmails(String sucEmails) {
        this.sucEmails = sucEmails;
    }

    public String getFailEmails() {
        return failEmails;
    }

    public void setFailEmails(String failEmails) {
        this.failEmails = failEmails;
    }
}
