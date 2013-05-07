
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import java.util.Arrays;

public class ErrorRepresentation {
    public static Representation compose(Exception e) {

        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{ \"exception\":\"");
        jsonString.append(e.getClass().getName());
        jsonString.append("\", \"message\":\"");
        if (null != e.getMessage() && !e.getMessage().isEmpty())
            jsonString.append(e.getMessage().replace("\"", "\\\""));
        else
            jsonString.append("(no message specified)");
        jsonString.append("\", \"stacktrace\":\"");
        jsonString.append(Arrays.asList(e.getStackTrace()).toString());
        jsonString.append("\"}");

        return new JsonRepresentation(jsonString.toString());
    }

    public static Representation compose(String errorMessage) {
        return new JsonRepresentation("{ \"error\":\"" + errorMessage + "\"}");
    }
}
