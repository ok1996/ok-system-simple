package cn.iosd.demo.email.controller;

import cn.iosd.demo.email.vo.SendEmailMultiplePersonVo;
import cn.iosd.demo.email.vo.SendEmailMultiplePersonWithCcVo;
import cn.iosd.demo.email.vo.SendEmailSinglePersonVo;
import cn.iosd.demo.email.vo.SendEmailSinglePersonWithAttachmentsVo;
import cn.iosd.starer.email.service.EmailService;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
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
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Operation(summary = "发送电子邮件给单个收件人")
    @GetMapping(value = "/sendEmailSinglePerson")
    public Response<?> sendEmailSinglePerson(@ParameterObject SendEmailSinglePersonVo vo) throws MessagingException, IOException {
        emailService.sendEmail(vo.getToEmail(), vo.getSubject(), vo.getContent(), vo.getIsHtml());
        return Response.ok();
    }

    @Operation(summary = "发送电子邮件给单个收件人并增加附件")
    @GetMapping(value = "/sendEmailSinglePersonWithAttachments")
    public Response<?> sendEmailSinglePersonWithAttachments(@ParameterObject SendEmailSinglePersonWithAttachmentsVo vo) throws MessagingException, IOException {
        emailService.sendEmailWithAttachments(vo.getToEmail(), vo.getSubject(), vo.getContent(), vo.getIsHtml(), vo.getAttachments());
        return Response.ok();
    }

    @Operation(summary = "发送电子邮件给多个收件人")
    @GetMapping(value = "/sendEmailMultiplePerson")
    public Response<?> sendEmailMultiplePerson(@ParameterObject SendEmailMultiplePersonVo vo) throws MessagingException, IOException {
        emailService.sendEmail(vo.getToEmails(), vo.getSubject(), vo.getContent(), vo.getIsHtml());
        return Response.ok();
    }

    @Operation(summary = "发送电子邮件给多个收件人,并抄送")
    @GetMapping(value = "/sendEmailMultiplePersonWithCc")
    public Response<?> sendEmailMultiplePersonWithCc(@ParameterObject SendEmailMultiplePersonWithCcVo vo) throws MessagingException, IOException {
        emailService.sendEmailWithCc(vo.getToEmails(), vo.getCcEmails(), vo.getSubject(), vo.getContent(), vo.getIsHtml());
        return Response.ok();
    }

}
