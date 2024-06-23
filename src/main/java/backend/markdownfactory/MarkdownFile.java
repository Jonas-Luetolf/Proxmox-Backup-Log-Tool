package backend.markdownfactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MarkdownFile {
    private final File file;
    private String content = "";

    /**
     * Constructor to initialize the Markdown file with a specified file.
     *
     * @param file The file where the Markdown content will be written.
     */
    public MarkdownFile(File file) {
        this.file = file;
    }

    /**
     * This method adds a Markdown component to the file's content.
     * For getting the string it uses the @code {getPlainStingRepresentation}
     * method from the component.
     *
     * @param component The Markdown component to be added.
     */
    public void addComponent(MarkdownComponent component){
        content += component.getPlainStringRepresentation();
        content += "\n";
    }

    /**
     * This method writes the accumulated Markdown content to the file.
     *
     * @throws RuntimeException if and the file can't be written.
     */
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
