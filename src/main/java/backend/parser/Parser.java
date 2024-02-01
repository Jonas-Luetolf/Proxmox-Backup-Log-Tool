package backend.parser;

import backend.data.Container;

import java.util.ArrayList;

public interface Parser {
    void parse(ArrayList emails);
    ArrayList<Container> getContainers();
}