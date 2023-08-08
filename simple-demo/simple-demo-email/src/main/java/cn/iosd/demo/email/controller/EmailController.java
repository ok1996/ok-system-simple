package cn.iosd.demo.email.controller;

import cn.iosd.demo.email.vo.SendEmailVo;
import cn.iosd.starer.email.service.EmailService;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.mail.MessagingException;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author ok1996
 */
@Tag(name = "邮件发送测试")
@RestController
@RequestMapping("/simple-demo-email/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Operation(summary = "发送电子邮件")
    @GetMapping(value = "/sendEmail")
    public Response<?> sendEmail(@ParameterObject SendEmailVo vo) throws MessagingException, IOException {
        emailService.sendEmail(vo.getToEmails(), vo.getCcEmails(), vo.getBccEmails(), vo.getSubject(), vo.getContent(), vo.getIsHtml(), vo.getAttachments(), vo.getInlineImages());
        return Response.ok();
    }

}
