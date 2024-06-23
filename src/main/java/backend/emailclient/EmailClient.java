package backend.emailclient;
import jakarta.mail.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EmailClient {
    private Session session = null;
    private Store emailStore = null;
    private final Properties props = new Properties();

    /**
     * Logs into the Email server if there is no active session
     * This method checks if there is an active session. If not, it
     * creates a new email session using the provided properties and tries
     * to connect to the email server via imap
     *
     *
     * @throws RuntimeException If an error occurs during login
     */
    public void login(String emailAddr, String password, String host, int port, boolean sslEnable) {

        // set Properties used later in session
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", String.valueOf(port));
        props.setProperty("mail.imap.ssl.enable", String.valueOf(sslEnable));

        if (session == null){

            try {
                session = Session.getInstance(props);
                this.emailStore = session.getStore("imaps");
                this.emailStore.connect(host,emailAddr,password);
            }

            catch (MessagingException e) {
                session = null;
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Logs out of the Email server
     * This method logs out of an email server if there is an active session.
     * It sets the email store and session to null, allowing to log in again later.
     *
     * @implNote MessagingException is ignored as it indicates that the email store was not open
     */
    public void logout(){
        try {
            if (this.emailStore != null) this.emailStore.close();

            /* set session and email store to null so can be logged in later */
            this.session = null;
            this.emailStore = null;
        }

        /* MessagingException can be ignored
        * if thrown the emailStore isn't open */
        catch (MessagingException ignored) {}
    }

    /**
     * Gets all emails from a specific Email folder
     * This method opens the specified email folder and gets all emails in it.
     *
     * @param folderName The folder name to get data from
     * @return an ArrayList with all Emails in the specified folder
     * @throws RuntimeException If an error occurs during the read of the email Folder
     * @implNote the email folder won't be closed
     */
    public List<Message> getEmailsFrom(String folderName){
        try {
            Folder emailFolder = this.emailStore.getFolder(folderName);
            emailFolder.open(Folder.READ_WRITE);

            return Arrays.asList(emailFolder.getMessages());
        }

        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}