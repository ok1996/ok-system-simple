package cn.iosd.demo.email.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ok1996
 */
@Data
public class SendEmailSinglePersonVo {
    @Schema(description = "收件人的电子邮件地址")
    private String toEmail;
    @Schema(description = "邮件主题")
    private String subject;
    @Schema(description = "邮件内容")
    private String content;
    @Schema(description = "指示邮件内容是否为HTML格式", defaultValue = "false")
    private Boolean isHtml;
}
