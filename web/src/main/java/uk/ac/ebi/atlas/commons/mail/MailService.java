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

package uk.ac.ebi.atlas.commons.mail;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(MailService.class);

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

        email.setFrom(mailMessage.sender);
        email.addTo(mailMessage.recipient);
        email.setSubject(mailMessage.subject);
        email.setMsg(mailMessage.body);

        if (smtpAuthRequired) {
            LOGGER.debug("<send> mail authentication required");
            email.setAuthentication(smtpUser, smtpPassword);
        }
        email.send();
        LOGGER.debug("<send> email sent to host " + smtpHost + ":" + smtpPort + " : " + email);

    }
}
