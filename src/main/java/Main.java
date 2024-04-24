import backend.data.Container;
import backend.emailclient.EmailClient;

// Jakarta Mail imports
import backend.parser.ProxmoxParser;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws MessagingException, IOException {

        EmailClient email = new EmailClient("jonas.luetolf@outlook.com","","outlook.office365.com",993, true);
        email.login();

        ProxmoxParser parser = new ProxmoxParser();

        parser.parse(email.getEmailsFrom("Backup-Logs").subList(0,10));
        List<Container> containers = parser.getContainers();

        System.out.printf("Parsed %d containers.\n", containers.size());
        for (Container container : containers) {
            System.out.printf("Parsed %d logs from container %d: %s\n",container.getLogs().size(),container.getId(),container.getName());
            System.out.printf("Container ID: %d Backup Size: %f Gib, Time: %f min\n",container.getId(),container.getLogs().get(0).getSize(),container.getLogs().get(0).getTime());
        }



        email.logout();

    }
}
