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

    private void parse(){
        try {
            JSONObject obj = (JSONObject) ((JSONArray) new JSONParser().parse(Files.readAllLines(Path.of(templateFile.getAbsolutePath())).toString())).get(0);
            email = (String) obj.get("email");
            password = (String) obj.get("password");
            imapUrl = (String) obj.get("imapUrl");
            port = (String) obj.get("port");
            folder = (String) obj.get("folder");
        }

        catch (ParseException e) {
            System.out.println("Parsing error: " + e.getMessage());
        }

        catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
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