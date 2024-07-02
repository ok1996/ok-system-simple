package cn.iosd.demo.email.vo;

import cn.iosd.starer.email.vo.EmailRequestVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ok1996
 */
@Data
public class SendEmailVo extends EmailRequestVo {
    @Schema(description = "收件人的邮件地址列表", example = "xxx@qq.com,xxx@foxmail.com")
    private List<String> toEmails;

    @Schema(description = "抄送人的邮件地址列表", example = "yyy@qq.com,yyy@foxmail.com")
    private List<String> ccEmails;

    @Schema(description = "附件文件的路径列表", example = "E:\\temp\\新建文本文档.txt")
    private List<String> attachments;

    @Schema(description = "邮件主题")
    private String subject;

    @Schema(description = "邮件内容")
    private String content;

    @Schema(description = "指示邮件内容是否为HTML格式", defaultValue = "false")
    private Boolean isHtml;

    @Schema(description = "密送人的邮件地址列表", example = "zzz@qq.com")
    private List<String> bccEmails;

    @Schema(description = "内联图片的路径和CID映射", hidden = true)
    Map<String, String> inlineImages;

}
