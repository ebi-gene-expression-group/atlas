
package uk.ac.ebi.atlas.controllers.rest;

import uk.ac.ebi.atlas.commons.mail.MailService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.commons.mail.EmailMessage;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;

@Controller
@Scope("request")
public class FeedbackEmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackEmailController.class);

    private static final String ATLAS_FEEDBACK_SUBJECT = "Atlas Feedback";
    private static final String ERROR_MESSAGE = "Sorry, an error occurred while sending feedback.";
    private static final String SUCCESS_MESSAGE = "Thank you for your feedback.";

    private EmailMessage emailMessage;

    private MailService mailService;

    private ApplicationProperties applicationProperties;


    @Inject
    public FeedbackEmailController(MailService mailService, EmailMessage emailMessage, ApplicationProperties applicationProperties) {
        this.mailService = mailService;
        this.emailMessage = emailMessage;
        this.applicationProperties = applicationProperties;
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String sendFeedbackMail(@RequestParam("feedback") String feedback, @RequestParam("email") String email) {

        emailMessage.setBody(feedback);
        if (StringUtils.isNotEmpty(email)) {
            emailMessage.setSender(email);
        } else {
            emailMessage.setSender(applicationProperties.getFeedbackEmailAddress());
        }
        emailMessage.setSubject(ATLAS_FEEDBACK_SUBJECT);
        emailMessage.setRecipient(applicationProperties.getFeedbackEmailAddress());

        Gson gson = new Gson();

        LOGGER.info("<sendFeedbackMail> {}", emailMessage);

        try {
            mailService.send(emailMessage);
        } catch (EmailException e) {
            LOGGER.error(MarkerFactory.getMarker("FATAL"), e.getMessage(), e);
            return gson.toJson(ERROR_MESSAGE);
        }

        return gson.toJson(SUCCESS_MESSAGE);
    }

}