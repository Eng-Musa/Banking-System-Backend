package engmusa.Email;

public interface EmailService {
    String sendConfirmationEmail(EmailDetails emailDetails);
    String sendSuccessfulCreationEmail(EmailDetails emailDetails);
}
