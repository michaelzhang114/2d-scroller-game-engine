package Accounts;

import Database.Account;
import Database.DBAccounts;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * This class is in charge of sending emails to a given email address that contain the password associated with a given
 * account stored in the database. The username for the account is passed into this class via the LoginScreen class.
 * With this information, this class sends a pre-formatted email from an account associated with this project containing
 * the correct password for the account.
 *
 * @author Eric Werbel
 */
public class EmailBot {

    public static final String USERNAME = "mightydux308";
    public static final String PASSWORD = "voogasalad123!";
    public static final String HOST = "smtp.gmail.com";
    public static final String PORT_NUM = "587";
    public static final String SUBJECT_FORGOT_PASSWORD = "[Mighty Dux! Your Account Password";
    public static final String BODY_TEXT = "Your password is: ";

    public static final String SENT_TITLE = "Email Sent";
    public static final String SENT_HEADER = "Email Sent Successfully";
    public static final String SENT_CONTENT = "An email has been sent with your password.";

    public static final String ADDRESS_FAIL = "addressException";
    public static final String MESSAGE_FAIL = "messageFail";

    public static final String INVALID_USER_ERROR = "invalidUsername";
    private ErrorHandler myErrorHandler = new ErrorHandler();

    private DBAccounts acctsDB = new DBAccounts();
    private MakeAlert myAlert = new MakeAlert();

    public EmailBot() {

    }

    // Code based on https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail
    /**
     * Sends an email to the email address associated with the given username containing the password associated wit
     * the account. The email is sent from an account that was set up in association with this project.
     *
     * @param username = username of the desired account
     */
    public void sendPassword(String username) {
        if (acctsDB.checkForAccount(username)) {
            Account myAccount = acctsDB.retrieveAccount(username);
            String pass = myAccount.getPassword();
            String email = myAccount.getEmail();
            createEmail(pass, email);
        } else {
            myErrorHandler.showError(INVALID_USER_ERROR);
        }
    }

    private void createEmail(String pass, String email) {
        String subject = SUBJECT_FORGOT_PASSWORD;
        String body = BODY_TEXT + pass;

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", PORT_NUM);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipients(Message.RecipientType.TO, email);

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USERNAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            myAlert.createAndShow(SENT_TITLE, SENT_HEADER, SENT_CONTENT);

        }
        catch (AddressException ae) {
            myErrorHandler.showError(ADDRESS_FAIL);
        }
        catch (MessagingException me) {
            myErrorHandler.showError(MESSAGE_FAIL);
        }
    }

}
