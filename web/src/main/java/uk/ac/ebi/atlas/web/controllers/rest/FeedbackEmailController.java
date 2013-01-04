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

    private ApplicationProperties properties;

    @Inject
    public FeedbackEmailController(ApplicationProperties properties) {
        this.properties = properties;
    }

    @RequestMapping(value = "/email", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String sendFeedback(@RequestBody MultiValueMap<String, String> body) {

        // capture input data
        String feedback = body.get("feedback").get(0);
        String email = body.get("email").get(0).trim();
        boolean sendemail = Boolean.parseBoolean(body.get("sendemail").get(0));

        Gson gson = new Gson();

        EmailMessage message = new EmailMessage(email, properties.getFeedbackEmail(), feedback);
        logger.info(message);

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

    class EmailMessage {

        private static final String ATLAS_FEEDBACK = "Atlas Feedback";

        public String sender;
        public String recipient;
        public String body;

        EmailMessage(String sender, String recipient, String body) {
            this.sender = sender;
            if (sender.isEmpty()) {
                this.sender = recipient;
            }
            this.recipient = recipient;
            this.body = body;
        }

        public void send() throws MessagingException {

            Session session = Session.getDefaultInstance(properties.getMailServerProperties());

            MimeMessage messageToSend = new MimeMessage(session);

            messageToSend.setFrom(new InternetAddress(this.sender));
            messageToSend.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipient));
            messageToSend.setSubject(ATLAS_FEEDBACK);
            messageToSend.setText(this.body);

            Transport.send(messageToSend);
        }

        @Override
        public String toString() {
            return "EmailMessage{" +
                    "sender='" + sender + '\'' +
                    ", recipient='" + recipient + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }
}