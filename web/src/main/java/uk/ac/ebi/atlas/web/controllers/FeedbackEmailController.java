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

package uk.ac.ebi.atlas.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@Controller
public class FeedbackEmailController {

    private static final Logger logger = Logger.getLogger(FeedbackEmailController.class);


    class EmailMessage {
        public String sender;
        public String addressee;
        public String subject;
        public String body;

        public EmailMessage
                (
                        String sender,
                        String addressee,
                        String subject,
                        String body
                ) {
            this.sender = sender;
            this.addressee = addressee;
            this.subject = subject;
            this.body = body;
        }

        public void send() {
            Properties mailServerProperties = new Properties();
            mailServerProperties.put
                    (
                            "mail.smtp.host",
                            "smtp.ebi.ac.uk"
                    );
            mailServerProperties.put
                    (
                            "mail.smtp.port",
                            "25"
                    );

            Session session = Session.getDefaultInstance
                    (
                            mailServerProperties
                    );

            MimeMessage messageToSend = new MimeMessage(session);

            try {
                messageToSend.setFrom
                        (
                                new InternetAddress(this.sender)
                        );
                messageToSend.addRecipient
                        (
                                Message.RecipientType.TO,
                                new InternetAddress(this.addressee)
                        );
                messageToSend.setSubject(this.subject);
                messageToSend.setText(this.body);

                Transport.send(messageToSend);
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }

    }

    private ApplicationProperties properties;


    @Inject
    public FeedbackEmailController(ApplicationProperties properties) {
        this.properties = properties;
    }

    @RequestMapping(value = "/email", method = RequestMethod.PUT)
    public void put(@RequestBody MultiValueMap<String, String> body, HttpServletResponse response) throws IOException {

        // capture input data
        String feedback = body.get("feedback").get(0);
        logger.info("<put> feedback = " + feedback);
        String email = body.get("email").get(0);
        logger.info("<put> email = " + email);
        boolean sendemail = Boolean.parseBoolean(body.get("sendemail").get(0));
        logger.info("<put> mail sent = " + sendemail);

        // compose email
        EmailMessage message;
        if (email.length() == 0)
            message = new EmailMessage(properties.getFeedbackEmail(), properties.getFeedbackEmail(), "Atlas Feedback", feedback);
        else
            message = new EmailMessage(email, properties.getFeedbackEmail(), "Atlas Feedback", feedback);
        if (sendemail)
            message.send();
    }
}