package simple_email_sender.brasilian.email;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

import java.util.function.Consumer;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * A service class responsible for sending emails using JavaMail API.
 * Supports both plain text and HTML email content with configurable email
 * providers.
 */
public class EmailSender {

    private final EmailConfig emailConfig;

    // Public fields (consider making these private with getters/setters)
    public String fromEmail;
    public String password;
    public String toEmail;
    public String personalTitle;
    public String subject;
    public String messageBodyHtml;
    public String messageBody;

    /**
     * Constructs an EmailSender with the specified email configuration.
     * 
     * @param emailConfig The email provider configuration (e.g., GmailConfig,
     *                    OutlookConfig)
     *                    containing server properties like host, port, and TLS
     *                    settings
     */
    public EmailSender(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    /**
     * Sends an email asynchronously with callbacks for success and error handling.
     * 
     * @param success A Runnable callback that executes when email is sent
     *                successfully
     * @param error   A Consumer<String> callback that receives error messages if
     *                sending fails
     * 
     * @implNote This method:
     *           <ul>
     *           <li>Automatically handles MIME message creation</li>
     *           <li>Supports both text/plain and text/html content types</li>
     *           <li>Includes debug logging through JavaMail</li>
     *           <li>Uses Authenticator for secure credential handling</li>
     *           </ul>
     * 
     * @example Typical usage:
     *          {@code
     * sender.sendEmail(
     *     () -> System.out.println("Email sent successfully"),
     *     error -> System.err.println("Error: " + error)
     * );
     * }
     */
    public void sendEmail(Runnable success, Consumer<String> error) {
        try {
            // Create authenticated session
            Session session = Session.getInstance(emailConfig.getServerProperties(),
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(fromEmail, password);
                        }
                    });

            session.setDebug(true); // Enable SMTP debugging

            // Configure message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, personalTitle));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Set content based on available body type
            if (messageBodyHtml != null) {
                message.setContent(messageBodyHtml, "text/html");
            } else {
                message.setText(messageBody);
            }

            Transport.send(message);
            success.run();
        } catch (Exception e) {
            error.accept("Failed to send email: " + e.getMessage());
        }
    }
}