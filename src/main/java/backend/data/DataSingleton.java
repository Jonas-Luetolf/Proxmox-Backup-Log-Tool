package backend.data;

import backend.emailclient.EmailClient;
import backend.parser.Parser;

public class DataSingleton {
    private static final DataSingleton instance = new DataSingleton();

    private Parser parser;
    private EmailClient emailClient;

    private DataSingleton() {}

    /**
     * This Method returns the static instance of the singleton
     *
     * Use this method to access data saved in the singleton.
     * @return DataSingleton static instance of DataSingleton
     */
    public static DataSingleton getInstance() {
        return instance;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public EmailClient getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(EmailClient emailClient) {
        this.emailClient = emailClient;
    }
}
