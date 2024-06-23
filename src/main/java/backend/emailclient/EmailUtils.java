package backend.emailclient;

// Jakarta Mail Imports
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class EmailUtils {

    /**
     * Extracts the text body from a multipart message content.
     *
     * This method iterates through all the BodyParts of the given Multipart content
     * and appends the content of those BodyParts whose ContentType is "text/plain"
     * to a StringBuilder.
     *
     * @param content The Multipart message content from which to extract the text body.
     * @return The extracted text body as a String.
     * @throws RuntimeException If an error occurs while accessing the BodyParts or reading the content.
     */
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

    /**
     * Extracts the text body from a given Message.
     *
     * This method first retrieves the content of the Message. If the content is a Multipart object,
     * it delegates the extraction to the {@code extractBodyFromMultipart} method. If the content
     * is a String, it returns the String content directly. If the content is neither a Multipart
     * nor a String, it returns an empty String.
     *
     * @param message The Message from which to extract the text body.
     * @return The extracted text body as a String.
     * @throws RuntimeException If an error occurs while accessing the content of the Message.
     */
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

    /**
     * Extracts the text bodies from a list of Messages.
     *
     * This method iterates through the list of Messages and extracts the text body
     * from each Message using the {@code extractBodyFromMessage} method. It then
     * adds each extracted text body to a List of Strings and returns this List.
     *
     * @param messages The list of Messages from which to extract the text bodies.
     * @return A List of extracted text bodies as Strings.
     */
    public static List<String> extractBodyFromMessageList(List<Message> messages){
        List<String> bodyList= new ArrayList<>();
        for (Message message: messages){
            bodyList.add(extractBodyFromMessage(message));
        }
        return bodyList;
    }
}