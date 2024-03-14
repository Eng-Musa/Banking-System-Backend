package engmusa.Email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailDetailsImp implements EmailService {
    private final JavaMailSender mailSender;

    public EmailDetailsImp(JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    @Override
    public String sendConfirmationEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("wd213230@gmail.com");
            mailMessage.setTo(emailDetails.getEmail());
            mailMessage.setSubject("Customer");

            StringBuilder emailText = new StringBuilder();
            emailText.append("Dear ").append(emailDetails.getName().toUpperCase())
                    .append(", \n\n");
            emailText.append("Thank you for registering to our system.\n");
            emailText.append(emailDetails.getToken()).append("\n");
            emailText.append("Confirmation token expires in 10 minutes, kindly verify your account. \n\n");
            emailText.append("~Musa");
            mailMessage.setText(emailText.toString());

            mailSender.send(mailMessage);
            System.out.println(mailMessage);
            return "Your registration was successful";
        } catch (Exception e) {
            System.out.println("Failed to send mail " + e.getMessage());
            return "Failed to send mail " + e.getMessage();

        }
    }

    @Override
    public String sendSuccessfulCreationEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("wd213230@gmail.com");
            mailMessage.setTo(emailDetails.getEmail());
            mailMessage.setSubject("Customer");

            StringBuilder emailText = new StringBuilder();
            emailText.append("Dear ").append(emailDetails.getName().toUpperCase())
                    .append(", \n\n");
            emailText.append("Your sign up request was successfully confirmed.\n");
            emailText.append("Account Number: ");
            emailText.append(emailDetails.getAccountNumber()).append("\n");
            emailText.append("Proceed to login page by clicking link below, enjoy our services. \n\n");
            emailText.append("~Musa");
            mailMessage.setText(emailText.toString());

            mailSender.send(mailMessage);
            System.out.println(mailMessage);
            return "Your registration was successful";
        } catch (Exception e) {
            System.out.println("Failed to send mail " + e.getMessage());
            return "Failed to send mail " + e.getMessage();

        }
    }
}
