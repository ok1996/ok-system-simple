package cn.iosd.starer.email.vo;

import java.util.List;
import java.util.Map;

/**
 * 发送电子邮件请求参数
 *
 * @author ok1996
 */
public class EmailRequestVo {
    /**
     * 收件人的电子邮件地址列表
     */
    private List<String> toEmails;

    /**
     * 抄送人的电子邮件地址列表
     */
    private List<String> ccEmails;

    /**
     * 密送人的电子邮件地址列表
     */
    private List<String> bccEmails;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 指示邮件内容是否为HTML格式
     */
    private boolean isHtml;

    /**
     * 附件文件的路径列表
     */
    private List<String> attachments;

    /**
     * 内联图片的路径和CID映射
     */
    private Map<String, String> inlineImages;

    public List<String> getToEmails() {
        return toEmails;
    }

    public void setToEmails(List<String> toEmails) {
        this.toEmails = toEmails;
    }

    public List<String> getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(List<String> ccEmails) {
        this.ccEmails = ccEmails;
    }

    public List<String> getBccEmails() {
        return bccEmails;
    }

    public void setBccEmails(List<String> bccEmails) {
        this.bccEmails = bccEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getInlineImages() {
        return inlineImages;
    }

    public void setInlineImages(Map<String, String> inlineImages) {
        this.inlineImages = inlineImages;
    }

}
