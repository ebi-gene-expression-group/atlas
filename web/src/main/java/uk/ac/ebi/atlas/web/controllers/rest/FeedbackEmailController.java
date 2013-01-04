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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.commons.mail.EmailMessage;
import uk.ac.ebi.atlas.utils.MailSender;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.mail.MessagingException;

@Controller
@Scope("request")
public class FeedbackEmailController {

    private static final Logger logger = Logger.getLogger(FeedbackEmailController.class);

    private static final String ATLAS_FEEDBACK_SUBJECT = "Atlas Feedback";
    private static final String ERROR_MESSAGE = "Sorry, an error occurred while sending feedback.";
    private static final String SUCCESS_MESSAGE = "Thank you for your feedback.";

    private EmailMessage emailMessage;

    private MailSender mailSender;

    private ApplicationProperties applicationProperties;


    @Inject
    public FeedbackEmailController(MailSender mailSender, EmailMessage emailMessage, ApplicationProperties applicationProperties) {
        this.mailSender = mailSender;
        this.emailMessage = emailMessage;
        this.applicationProperties = applicationProperties;
    }

    @RequestMapping(value = "/email", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String sendFeedbackMail(@RequestBody MultiValueMap<String, String> body) {

        // capture input data
        emailMessage.setBody(body.get("feedback").get(0));
        emailMessage.setSender(body.get("email").get(0));
        if (StringUtils.isEmpty(emailMessage.getSender())){
            emailMessage.setSender(applicationProperties.getFeedbackEmailAddress());
        }
        emailMessage.setSubject(ATLAS_FEEDBACK_SUBJECT);
        emailMessage.setRecipient(applicationProperties.getFeedbackEmailAddress());

        Gson gson = new Gson();

        logger.info("<sendFeedbackMail> " + emailMessage);

        try {
            mailSender.send(emailMessage);
            return gson.toJson(SUCCESS_MESSAGE);
        } catch (Exception e) {
            logger.fatal(e.getMessage(), e);
            return gson.toJson(ERROR_MESSAGE);
        }

    }

}