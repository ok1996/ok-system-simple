package cn.iosd.starer.email.service;

import cn.iosd.starer.email.properties.EmailProperties;
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
import java.util.ArrayList;
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
     * 发送电子邮件给单个收件人。
     *
     * @param toEmail 收件人的电子邮件地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param isHtml  指示邮件内容是否为HTML格式
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmail(String toEmail, String subject, String content, boolean isHtml) throws MessagingException, IOException {
        List<String> toEmails = new ArrayList<>();
        toEmails.add(toEmail);
        sendEmailCore(toEmails, null, null, subject, content, isHtml, null, null);
    }

    /**
     * 发送电子邮件给多个收件人。
     *
     * @param toEmails 收件人的电子邮件地址列表
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param isHtml   指示邮件内容是否为HTML格式
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmail(List<String> toEmails, String subject, String content, boolean isHtml) throws MessagingException, IOException {
        sendEmailCore(toEmails, null, null, subject, content, isHtml, null, null);
    }

    /**
     * 发送电子邮件给单个收件人，并附加文件作为附件。
     *
     * @param toEmail     收件人的电子邮件地址
     * @param subject     邮件主题
     * @param content     邮件内容
     * @param isHtml      指示邮件内容是否为HTML格式
     * @param attachments 附件文件的路径列表
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmailWithAttachments(String toEmail, String subject, String content, boolean isHtml, List<String> attachments) throws MessagingException, IOException {
        List<String> toEmails = new ArrayList<>();
        toEmails.add(toEmail);
        sendEmailCore(toEmails, null, null, subject, content, isHtml, attachments, null);
    }

    /**
     * 发送电子邮件给多个收件人，并抄送给指定的邮箱。
     *
     * @param toEmails 收件人的电子邮件地址列表
     * @param ccEmails 抄送人的电子邮件地址列表
     * @param subject  邮件主题
     * @param content  邮件内容
     * @param isHtml   指示邮件内容是否为HTML格式
     * @throws MessagingException 发送邮件时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    public void sendEmailWithCc(List<String> toEmails, List<String> ccEmails, String subject, String content, boolean isHtml) throws MessagingException, IOException {
        sendEmailCore(toEmails, ccEmails, null, subject, content, isHtml, null, null);
    }

    /**
     * 发送电子邮件核心方法
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
    public void sendEmailCore(List<String> toEmails, List<String> ccEmails, List<String> bccEmails, String subject, String content, boolean isHtml, List<String> attachments, Map<String, String> inlineImages) throws MessagingException, IOException {
        Session session = createSession();
        Message message = createMessage(session, toEmails, ccEmails, bccEmails, subject, content, isHtml, attachments, inlineImages);
        Transport.send(message);
    }

    /**
     * 创建邮件会话对象。
     *
     * @return 邮件会话对象
     */
    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", properties.getSmtpAuthEnable());
        props.put("mail.smtp.starttls.enable", properties.getSmtpStarttlsEnable());
        props.put("mail.smtp.host", properties.getSmtpHost());
        props.put("mail.smtp.port", properties.getSmtpPort());

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getUsername(), properties.getPassword());
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
     * @return 邮件消息对象
     * @throws MessagingException 创建邮件消息时可能抛出的异常
     * @throws IOException        读取附件文件时可能抛出的异常
     */
    private Message createMessage(Session session, List<String> toEmails, List<String> ccEmails, List<String> bccEmails, String subject, String content, boolean isHtml, List<String> attachments, Map<String, String> inlineImages) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(properties.getFromEmail()));

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
}
