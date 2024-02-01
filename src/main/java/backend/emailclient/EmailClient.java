package backend.emailclient;
import jdk.jshell.spi.ExecutionControl;
import java.util.ArrayList;

public class EmailClient {
    private String emailAddr;
    private String password;
    private String host;
    private boolean sslEnable;

    public EmailClient(String emailAddr, String password, String host, boolean sslEnable) {
        this.emailAddr = emailAddr;
        this.password = password;
        this.host = host;
        this.sslEnable = sslEnable;
    }

    /**
     *Logs into the Email server
     */
    public void login(){
    }

    /**
     * Logs out of the Email server
     */
    public void logout(){
    }

    /**
     * gets all emails from a specific Email folder
     * @param folderName The folder name to get data from
     * @return an ArrayList with all Emails in the specified folder
     */
    public ArrayList getEmailsFrom(String folderName){
        return null;
    }
}
