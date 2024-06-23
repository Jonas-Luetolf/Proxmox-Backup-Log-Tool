package backend.logintemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class LoginTemplate {
    private final File templateFile;

    private String email = "";
    private String password = "";
    private String imapUrl = "";
    private String port = "";
    private String folder = "";

    public LoginTemplate(File templateFile) {
        this.templateFile = templateFile;
        this.parse();
    }

    /**
     * This method parses a Json template File and extracts all found information.
     *
     * The method opens the file specified in the Constructor {@code LoginTemplate} and
     * takes the first JsonObject in the file. It tries to extract
     * the Login Options. If one element isn't found in the file, it will be left as
     * an empty String.
     *
     * @throws RuntimeException If and Error occurs during reading of the file or parsing.
     */
    private void parse(){
        try {
            // load the Json file as JSONArray and get first JSONObject
            JSONObject obj = (JSONObject) ((JSONArray) new JSONParser().parse(Files.readAllLines(Path.of(templateFile.getAbsolutePath())).toString())).get(0);

            // settings that are not found in the file get ignored and not filled out
            email = (String) obj.get("email");
            password = (String) obj.get("password");
            imapUrl = (String) obj.get("imapUrl");
            port = (String) obj.get("port");
            folder = (String) obj.get("folder");
        }

        catch (ParseException | IOException e) {
           throw new RuntimeException(e);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImapUrl() {
        return imapUrl;
    }

    public String getPort() {
        return port;
    }

    public String getFolder() {
        return folder;
    }
}