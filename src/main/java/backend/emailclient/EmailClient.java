package backend.emailclient;
import jakarta.mail.*;

import java.util.Properties;

public class EmailClient {
    private final String emailAddr;
    private final String password;
    private final String host;
    private Session session = null;
    public Store emailStore = null;
    private final Properties props = new Properties();

    public EmailClient(String emailAddr, String password, String host, int port, boolean sslEnable) {
        this.emailAddr = emailAddr;
        this.password = password;
        this.host = host;

        // set Properties used later in session
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", String.valueOf(port));
        props.setProperty("mail.imap.ssl.enable", String.valueOf(sslEnable));

    }

    /**
     *Logs into the Email server
     */
    public void login(){
        /* only log in if there isn't a session */
        if (session == null){

            try {
                session = Session.getInstance(props);
                this.emailStore = session.getStore("imaps");
                this.emailStore.connect(host,emailAddr,password);
            }

            catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Logs out of the Email server
     *
     * @implNote MessagingException ignored
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
     * gets all emails from a specific Email folder
     * @implNote the email folder won't be closed
     *
     * @param folderName The folder name to get data from
     * @return an ArrayList with all Emails in the specified folder
     */
    public Message[] getEmailsFrom(String folderName){
        try {
            Folder emailFolder = this.emailStore.getFolder(folderName);
            emailFolder.open(Folder.READ_WRITE);

            return emailFolder.getMessages();
        }

        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}