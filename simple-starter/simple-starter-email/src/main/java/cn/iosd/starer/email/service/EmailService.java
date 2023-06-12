package cn.iosd.starer.email.service;

import cn.iosd.starer.email.properties.EmailProperties;
import cn.iosd.starer.email.vo.EmailConfigVo;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author ok1996
 */
@Service
public class EmailService {
    @Autowired
    private EmailProperties properties;

    /**
     * 创建邮件会话对象。
     *
     * @return 邮件会话对象
     */
    private Session createSession(EmailConfigVo config) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", config.getSmtpAuthEnable());
        props.put("mail.smtp.starttls.enable", config.getSmtpStarttlsEnable());
        props.put("mail.smtp.host", config.getSmtpHost());
        props.put("mail.smtp.port", config.getSmtpPort());
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        };
        return Session.getInstance(props, authenticator);
    }

    /**
     * 创建邮件消息对象。
     *
     * @param session      邮件会话对象
     * @param toEmails     收件人的电子邮件地址列表
     * @param ccEmails     抄送人的电子邮件地址列表
     * @param bccEmails    密送人的电子邮件地址列表
     * @param subject      邮件主题
     * @param content      邮件内容
     * @param isHtml       指示邮件内容是否为HTML格式
     * @param attachments  附件文件的路径列表
     * @param inlineImages 内联图片的路径和CID映射
     * @param fromEmail    发件人邮箱地址
     * @return 邮件消息对象
     * @throws MessagingException 创建邮件消息时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    private Message createMessage(Session session, List<String> toEmails, List<String> ccEmails, List<String> bccEmails, String subject, String content, boolean isHtml, List<String> attachments, Map<String, String> inlineImages, String fromEmail) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));

        for (String toEmail : toEmails) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }
        if (ccEmails != null) {
            for (String ccEmail : ccEmails) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
            }
        }
        if (bccEmails != null) {
            for (String bccEmail : bccEmails) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccEmail));
            }
        }

        message.setSubject(subject);

        if (attachments == null && inlineImages == null) {
            message.setContent(content, isHtml ? "text/html" : "text/plain");
        } else {
            Multipart multipart = new MimeMultipart();

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, isHtml ? "text/html" : "text/plain");
            multipart.addBodyPart(messageBodyPart);
            if (attachments != null) {
                for (String attachment : attachments) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    attachmentBodyPart.attachFile(new File(attachment));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }
            if (inlineImages != null) {
                for (Map.Entry<String, String> entry : inlineImages.entrySet()) {
                    String imagePath = entry.getKey();
                    String cid = entry.getValue();

                    MimeBodyPart imageBodyPart = new MimeBodyPart();
                    imageBodyPart.attachFile(new File(imagePath));
                    imageBodyPart.setContentID("<" + cid + ">");
                    imageBodyPart.setDisposition(MimeBodyPart.INLINE);
                    multipart.addBodyPart(imageBodyPart);
                }
            }
            message.setContent(multipart);
        }
        return message;
    }

    /**
     * 发送电子邮件方法-发件人邮件配置参数使用工程配置项
     *
     * @param toEmails     收件人的电子邮件地址列表
     * @param ccEmails     抄送人的电子邮件地址列表
     * @param bccEmails    密送人的电子邮件地址列表
     * @param subject      邮件主题
     * @param content      邮件内容
     * @param isHtml       指示邮件内容是否为HTML格式
     * @param attachments  附件文件的路径列表
     * @param inlineImages 内联图片的路径和CID映射
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmail(List<String> toEmails, List<String> ccEmails, List<String> bccEmails, String subject, String content, boolean isHtml, List<String> attachments, Map<String, String> inlineImages)
            throws MessagingException, IOException {
        sendEmail(toEmails, ccEmails, bccEmails, subject, content, isHtml, attachments, inlineImages, properties.getConfig());
    }

    /**
     * 发送电子邮件方法
     *
     * @param toEmails      收件人的电子邮件地址列表
     * @param ccEmails      抄送人的电子邮件地址列表
     * @param bccEmails     密送人的电子邮件地址列表
     * @param subject       邮件主题
     * @param content       邮件内容
     * @param isHtml        指示邮件内容是否为HTML格式
     * @param attachments   附件文件的路径列表
     * @param inlineImages  内联图片的路径和CID映射
     * @param emailConfigVo 发件人邮件配置参数
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmail(List<String> toEmails, List<String> ccEmails, List<String> bccEmails, String subject, String content, boolean isHtml, List<String> attachments, Map<String, String> inlineImages, EmailConfigVo emailConfigVo)
            throws MessagingException, IOException {
        Session session = createSession(emailConfigVo);
        Message message = createMessage(session, toEmails, ccEmails, bccEmails, subject, content, isHtml, attachments, inlineImages, emailConfigVo.getFromEmail());
        Transport.send(message);
    }

}
