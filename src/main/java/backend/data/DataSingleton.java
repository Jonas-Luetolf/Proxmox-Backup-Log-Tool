package backend.data;

import backend.emailclient.EmailClient;
import backend.parser.Parser;

public class DataSingleton {
    private static DataSingleton instance = new DataSingleton();

    private Parser parser;
    private EmailClient emailClient;

    private DataSingleton() {}

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
