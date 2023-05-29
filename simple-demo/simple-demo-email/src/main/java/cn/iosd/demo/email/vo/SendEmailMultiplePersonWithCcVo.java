package cn.iosd.demo.email.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ok1996
 */
@Data
public class SendEmailMultiplePersonWithCcVo {
    @Schema(description = "收件人的电子邮件地址列表",example = "testxxx@qq.com,testxxx@foxmail.com")
    private List<String> toEmails;
    @Schema(description = "抄送人的电子邮件地址列表",example = "testyyy@qq.com,testyyy@foxmail.com")
    private List<String> ccEmails;
    @Schema(description = "邮件主题")
    private String subject;
    @Schema(description = "邮件内容")
    private String content;
    @Schema(description = "指示邮件内容是否为HTML格式", defaultValue = "false")
    private Boolean isHtml;
}
