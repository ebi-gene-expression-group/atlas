package uk.ac.ebi.atlas.commons.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

@Named("mailSender")
public class MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Value("#{configuration['mail.smtp.host']}")
    private String smtpHost;

    @Value("#{configuration['mail.smtp.port']}")
    private int smtpPort;

    @Value("#{configuration['mail.smtp.auth']}")
    private boolean smtpAuthRequired;

    @Value("#{configuration['mail.smtp.user']}")
    private String smtpUser;

    @Value("#{configuration['mail.smtp.password']}")
    private String smtpPassword;

    public void send(EmailMessage mailMessage) throws EmailException {

        SimpleEmail email = new SimpleEmail();

        email.setHostName(smtpHost);
        email.setSmtpPort(smtpPort);

        email.setFrom(mailMessage.getSender());
        email.addTo(mailMessage.getRecipient());
        email.setSubject(mailMessage.getSubject());
        email.setMsg(mailMessage.getBody());

        if (smtpAuthRequired) {
            LOGGER.debug("<send> mail authentication required");
            email.setAuthentication(smtpUser, smtpPassword);
        }
        email.send();
        LOGGER.debug("<send> email sent to host {}:{} : {}", smtpHost, smtpPort, email);

    }
}
