package cn.iosd.demo.email.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ok1996
 */
@Data
public class SendEmailSinglePersonWithAttachmentsVo {
    @Schema(description = "收件人的电子邮件地址")
    private String toEmail;
    @Schema(description = "附件文件的路径列表",example = "E:\\temp\\新建文本文档.txt")
    private List<String> attachments;
    @Schema(description = "邮件主题")
    private String subject;
    @Schema(description = "邮件内容")
    private String content;
    @Schema(description = "指示邮件内容是否为HTML格式", defaultValue = "false")
    private Boolean isHtml;
}
