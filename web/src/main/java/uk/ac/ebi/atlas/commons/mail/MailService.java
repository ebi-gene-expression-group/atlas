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

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named("mailSender")
@Scope("singleton")
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
