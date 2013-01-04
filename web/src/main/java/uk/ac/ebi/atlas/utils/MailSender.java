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

@Named("mailSender")
@Scope("singleton")
public class MailSender {

    private ApplicationProperties properties;

    @Inject
    public MailSender(ApplicationProperties properties) {
        this.properties = properties;
    }

    public void send(EmailMessage mailMessage) throws MessagingException {
        Session session = Session.getDefaultInstance(properties.getMailServerProperties());

        MimeMessage messageToSend = new MimeMessage(session);

        messageToSend.setFrom(new InternetAddress(mailMessage.sender));
        messageToSend.addRecipient(Message.RecipientType.TO, new InternetAddress(mailMessage.recipient));
        messageToSend.setSubject(mailMessage.subject);
        messageToSend.setText(mailMessage.body);

        Transport.send(messageToSend);

    }
}
