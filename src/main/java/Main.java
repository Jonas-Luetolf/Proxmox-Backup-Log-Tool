import backend.emailclient.EmailClient;

import static backend.emailclient.EmailUtils.*;
// Jakarta Mail imports
import jakarta.mail.MessagingException;

import java.io.IOException;



public class Main {
    public static void main(String[] args) throws MessagingException, IOException {
        EmailClient email = new EmailClient("jonas.luetolf@outlook.com","","outlook.office365.com",993, true);
        email.login();

       // System.out.println(extractBodyFromMessage(email.getEmailsFrom("Backup-Logs").get(0)));
        for (String body: extractBodyFromMessageList(email.getEmailsFrom("Backup-Logs").subList(0,10
        ))){
            System.out.println(body);
            System.out.println("-----------------------------------------------");
        }

        email.logout();

    }
}
