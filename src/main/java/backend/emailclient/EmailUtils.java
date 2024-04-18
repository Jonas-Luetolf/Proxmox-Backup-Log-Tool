package backend.emailclient;

// Jakarta Mail Imports
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract public class EmailUtils {

    public static String extractBodyFromMultipart(Multipart content){
        StringBuilder body = new StringBuilder();

        try {
            for (int i = 0; i < content.getCount(); i++) {
                BodyPart bodyPart = content.getBodyPart(i);


                if (bodyPart.getContentType().startsWith("text/plain")) {
                    body.append(bodyPart.getContent());
                }
            }
        }

        catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }

        return body.toString();
    }

    public static String extractBodyFromMessage(Message message){
        Object content;
        try {
            content = message.getContent();
        }

        catch (IOException | MessagingException e) {
            throw new RuntimeException(e);
        }

        if (content instanceof Multipart){
            return extractBodyFromMultipart((Multipart) content);
        }

        else if (content instanceof String){
            return (String) content;
        }

        return "";
    }

    public static List<String> extractBodyFromMessageList(List<Message> messages){
        List<String> bodyList= new ArrayList<>();
        for (Message message: messages){
            bodyList.add(extractBodyFromMessage(message));
        }
        return bodyList;
    }
}