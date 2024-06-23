package backend.parser;

import backend.data.Container;
import backend.data.Log;
import backend.emailclient.EmailUtils;
import static backend.parser.ParserUtils.*;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxmoxParser implements Parser {
    private final ArrayList<Container> containers = new ArrayList<>();

    /**
     * parses the given emails and stores the found containers with their information
     * This method filters the emails and ignores emails that are not Proxmox logs.
     * The method extracts the body from the emails and parses it. The container ID and Name
     * the backup size, time and state  and the whole log text will be extracted.
     * The logs will be associated with their containers. The containers can be gotten by calling
     * {@code getContainers()} . If an information can't be parsed correctly the Log will be ignored.
     *
     * @param emails a List with all emails to parse
     */
    @Override
    public void parse(List<Message> emails) {

        // filter list after @code isPVELog
        List<Message> filteredMessages;
        filteredMessages = emails.stream().filter(ProxmoxParser::isPVELog).toList();

        // get body form Messages via @code EmailUtils.extractBodyFromMessage
        List<String> bodyStrings;
        bodyStrings = filteredMessages.stream().map(EmailUtils::extractBodyFromMessage).toList();

        for (String body: bodyStrings){
            // strip empty lines from body
            body = body.replaceAll("(?m)^\\s+", "").replaceAll("(?m)\\s+$", "");

            // split in parts Details and Logs
            List<String> bodyParts = Arrays.asList(body.split("={2,}"));

            // remove empty strings
            bodyParts = bodyParts.stream().filter(s -> (!s.isEmpty())).toList();

            List<String> details = Arrays.asList(bodyParts.get(1).split("\n")[2].split("\s{2,}"));
            String logText = bodyParts.get(2);

            try {
                int id = Integer.parseInt(details.get(0));
                String name = details.get(1);
                boolean status = details.get(2).equalsIgnoreCase("ok");
                float time = getTimeAsFloat(details.get(3));
                float size = (float) (getFloatFromString(details.get(4)) * getFactorToGib(details.get(4)));

                this.addLogToContainer(id,name,new Log(status, size, time, logText));
            }

            // if a Log can't be parsed correctly, it will be ignored
            catch (NumberFormatException ignored){}
        }
    }

    /**
     * This method returns a List of all parsed containers.
     *
     * @return List with all found containers
     */
    @Override
    public List<Container> getContainers() {
        return this.containers;
    }

    /**
     * adds a log entry to a container
     * This method checks if the container already exist. If not it will creat it.
     * The method ads the log to the specified container.
     *
     * @param ctID the ID of the container the log should be added to.
     * @param ctName the name of the container the log should be added to.
     * @param log the log entry that should be added
     */
    private void addLogToContainer(int ctID, String ctName, Log log){
        for (Container container: this.containers){
            if (container.getId() == ctID){
                container.addLog(log);
                return;
            }
        }
        this.containers.add(new Container(ctID,ctName));
        containers.get(containers.size()-1).addLog(log);
    }

    /**
     * checks if an email is a Proxmox backup job log
     * This method checks if the subject of the message starts with "vzdump backup status"
     * if an MessagingException occurs the method will return false.
     *
     * @param message the email message to check
     * @return boolean if the email is a backup log
     */
    private static boolean isPVELog(Message message) {
        try {
            if (message.getSubject().startsWith("vzdump backup status")) return true;
        }
        catch (MessagingException e) {
            return false;
        }
        return false;
    }
}