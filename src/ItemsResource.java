
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ItemsResource extends ServerResource {
    @Get("application/json")
    public Representation toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("items", new JSONObject(StarterApplication.items));
            return StarterApplication.getJsonRepresentation(json);
        } catch (Exception e) {
            return ErrorRepresentation.compose(e);
        }
    }
}

