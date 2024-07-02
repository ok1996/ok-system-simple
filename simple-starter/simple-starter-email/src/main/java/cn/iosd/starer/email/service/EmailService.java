package cn.iosd.starer.email.service;

import cn.iosd.starer.email.properties.EmailProperties;
import cn.iosd.starer.email.vo.EmailConfigVo;
import cn.iosd.starer.email.vo.EmailRequestVo;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
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

    private Session createSession(EmailConfigVo config) {
        Properties props = buildEmailProperties(config);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        };
        return Session.getInstance(props, authenticator);
    }

    private Properties buildEmailProperties(EmailConfigVo config) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", config.getSmtpAuthEnable());
        props.put("mail.smtp.starttls.enable", config.getSmtpStarttlsEnable());
        props.put("mail.smtp.host", config.getSmtpHost());
        props.put("mail.smtp.port", config.getSmtpPort());
        return props;
    }

    private void addRecipients(Message message, List<String> emails, Message.RecipientType type) throws MessagingException {
        if (emails != null) {
            for (String email : emails) {
                message.addRecipient(type, new InternetAddress(email));
            }
        }
    }

    private Multipart createEmailBody(String content, boolean isHtml, List<String> attachments, Map<String, String> inlineImages) throws MessagingException, IOException {
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
                MimeBodyPart imageBodyPart = new MimeBodyPart();
                imageBodyPart.attachFile(new File(entry.getKey()));
                imageBodyPart.setContentID("<" + entry.getValue() + ">");
                imageBodyPart.setDisposition(MimeBodyPart.INLINE);
                multipart.addBodyPart(imageBodyPart);
            }
        }
        return multipart;
    }

    private Message createMessage(Session session, EmailRequestVo vo, String fromEmail) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));

        addRecipients(message, vo.getToEmails(), Message.RecipientType.TO);
        addRecipients(message, vo.getCcEmails(), Message.RecipientType.CC);
        addRecipients(message, vo.getBccEmails(), Message.RecipientType.BCC);
        message.setSubject(vo.getSubject());

        if (vo.getAttachments() == null && vo.getInlineImages() == null) {
            message.setContent(vo.getContent(), vo.isHtml() ? "text/html" : "text/plain");
        } else {
            Multipart multipart = createEmailBody(vo.getContent(), vo.isHtml(), vo.getAttachments(), vo.getInlineImages());
            message.setContent(multipart);
        }

        return message;
    }

    public void sendEmail(EmailRequestVo vo) throws MessagingException, IOException {
        sendEmail(vo, properties.getConfig());
    }

    /**
     * 发送电子邮件方法
     *
     * @param vo            发送电子邮件内容参数
     * @param emailConfigVo 发件人邮件配置参数
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmail(EmailRequestVo vo, EmailConfigVo emailConfigVo)
            throws MessagingException, IOException {
        Session session = createSession(emailConfigVo);
        Message message = createMessage(session, vo, emailConfigVo.getFromEmail());
        Transport.send(message);
    }
}
