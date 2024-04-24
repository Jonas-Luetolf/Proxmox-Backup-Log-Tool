package backend.parser;

import backend.data.Container;
import jakarta.mail.Message;

import java.util.List;

public interface Parser {
    void parse(List<Message> emails);
    List<Container> getContainers();
}