package backend.markdownfactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MarkdownFile {
    private final File file;
    private String content = "";

    public MarkdownFile(File file) {
        this.file = file;
    }

    public void addComponent(MarkdownComponent component){
        content += component.getPlainStringRepresentation();
        content += "\n";
    }

    public void writeToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
