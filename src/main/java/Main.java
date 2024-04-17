import backend.emailclient.EmailClient;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws MessagingException, IOException {
        EmailClient email = new EmailClient("jonas.luetolf@outlook.com","","outlook.office365.com",993, true);
        email.login();
        //System.out.println(email.emailStore);
        Object content;
        try {
            content = email.getEmailsFrom("Backup-Logs")[0].getContent();
        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }

        if (content instanceof String) {
            // Simple text message
            System.out.println("Text content:\n" + content);
        } else if (content instanceof Multipart) {
            // Multipart message (text + attachments)
            Multipart multipartContent = (Multipart) content;

            for (int i = 0; i < multipartContent.getCount(); i++) {
                BodyPart bodyPart = multipartContent.getBodyPart(i);

                if (bodyPart.getContentType().startsWith("text/plain")) {
                    System.out.println("Text content:\n" + bodyPart.getContent());
                }
            }
        }
        email.logout();

    }
}
