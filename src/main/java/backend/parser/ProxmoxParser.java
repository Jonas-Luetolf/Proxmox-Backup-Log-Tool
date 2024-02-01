package backend.parser;

import backend.data.Container;

import java.util.ArrayList;

public class ProxmoxParser implements Parser {
    private ArrayList<Container> containers;
    /**
     * parses the given emails and stores the found containers with their information
     *
     * @param emails an Array List with all emails to parse
     */
    @Override
    public void parse(ArrayList emails) {

    }

    /**
     * returns all containers that have been found jet
     *
     * @return ArrayList with all found containers
     */
    @Override
    public ArrayList<Container> getContainers() {
        return null;
    }

    /**
     * checks if an email is a Proxmox backup job log
     *
     * @param message the email message to check
     * @return boolean if the email is a backup log
     */
    private static boolean isPVELog(String message) {
        return false;
    }
}