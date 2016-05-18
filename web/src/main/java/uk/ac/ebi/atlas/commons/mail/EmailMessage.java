
package uk.ac.ebi.atlas.commons.mail;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;

@Named("mailMessage")
@Scope("prototype")
public class EmailMessage {

    private String sender;
    private String recipient;
    private String body;
    private String subject;

    public EmailMessage() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        if (sender != null) {
            this.sender = sender.trim();
        }
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
