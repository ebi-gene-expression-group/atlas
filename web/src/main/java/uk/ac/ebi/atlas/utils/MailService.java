/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.mail.EmailMessage;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Named("mailSender")
@Scope("singleton")
public class MailService {

    @Value("#{configuration['mail.smtp.host']}")
    private String smtpHost;

    @Value("#{configuration['mail.smtp.port']}")
    private String smtpPort;

    @Value("#{configuration['mail.smtp.auth']}")
    private boolean smtpAuthRequired;

    @Value("#{configuration['mail.smtp.user']}")
    private String smtpUser;

    @Value("#{configuration['mail.smtp.password']}")
    private String smtpPassword;

    public void send(EmailMessage mailMessage) throws MessagingException {

        Properties smtpProperties = new Properties();
        smtpProperties.put("mail.smtp.host", smtpHost);
        smtpProperties.put("mail.smtp.port", smtpPort);

        Session session = Session.getDefaultInstance(smtpProperties);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(mailMessage.sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.recipient));
        message.setSubject(mailMessage.subject);
        message.setText(mailMessage.body);

        Transport transport = session.getTransport("smtp");
        if (smtpAuthRequired) {
            transport.connect(smtpUser, smtpPassword);
        } else {
            transport.connect();
        }

        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }
}
