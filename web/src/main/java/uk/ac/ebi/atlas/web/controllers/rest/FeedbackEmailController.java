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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Controller
@Scope("request")
public class FeedbackEmailController {

    private static final Logger logger = Logger.getLogger(FeedbackEmailController.class);

    public class EmailMessage {
        public String sender;
        public String addressee;
        public String subject;
        public String body;

        public EmailMessage(String sender, String addressee, String subject, String body) {
            this.sender = sender;
            this.addressee = addressee;
            this.subject = subject;
            this.body = body;
        }

        public void send() throws MessagingException {

            Session session = Session.getDefaultInstance(properties.getMailServerProperties());

            MimeMessage messageToSend = new MimeMessage(session);

            messageToSend.setFrom(new InternetAddress(this.sender));
            messageToSend.addRecipient(Message.RecipientType.TO, new InternetAddress(this.addressee));
            messageToSend.setSubject(this.subject);
            messageToSend.setText(this.body);

            Transport.send(messageToSend);
        }

    }

    private ApplicationProperties properties;

    @Inject
    public FeedbackEmailController(ApplicationProperties properties) {
        this.properties = properties;
    }

    @RequestMapping(value = "/email", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String put(@RequestBody MultiValueMap<String, String> body) {

        // capture input data
        String feedback = body.get("feedback").get(0);
        logger.info("<put> feedback = " + feedback);
        String email = body.get("email").get(0);
        logger.info("<put> email = " + email);
        boolean sendemail = Boolean.parseBoolean(body.get("sendemail").get(0));
        logger.info("<put> mail sent = " + sendemail);
        // sendemail = false;

        Gson gson = new Gson();

        // compose email
        EmailMessage message;
        if (email.trim().length() == 0)
            message = new EmailMessage(properties.getFeedbackEmail(), properties.getFeedbackEmail(), "Atlas Feedback", feedback);
        else
            message = new EmailMessage(email, properties.getFeedbackEmail(), "Atlas Feedback", feedback);
        if (sendemail) {
            try {
                message.send();
            } catch (MessagingException me) {
                logger.warn(me);
                return gson.toJson("Sorry, an error occurred while sending feedback.");
            }
        }
        return gson.toJson("Thank you for your feedback.");
    }
}