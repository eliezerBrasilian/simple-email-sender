package simple_email_sender.brasilian;

import java.io.IOException;
import simple_email_sender.brasilian.email.EmailSender;
import simple_email_sender.brasilian.email.GmailConfig;

public class Main {
    public static void main(String[] args) throws IOException {

        var emailSender = new EmailSender(new GmailConfig());
        emailSender.fromEmail = "your email here";
        emailSender.password = "your password gere";

        emailSender.messageBodyHtml = "this is a test message";
        emailSender.personalTitle = "Email - Support";
        emailSender.subject = "some title here";
        emailSender.toEmail = "destination email here";

        emailSender.sendEmail(() -> {
            System.out.println("email sent");

        }, (errorMessage) -> {
            System.out.println(errorMessage);
        });

    }
}
