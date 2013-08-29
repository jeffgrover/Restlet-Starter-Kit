import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.io.IOException;

public class ItemResource extends ServerResource {

    String id;

    @Override
    protected void doInit() throws ResourceException {
        this.id = (String) getRequest().getAttributes().get("id");
    }

    @Put
    @Options    //  FOR CORS
    public Representation modifyItem(String jsonString) {
        try {
            StarterApplication.addCORSHeader(getResponse());

            if (!StarterApplication.items.containsKey(id)) {
                StarterApplication.items.put(id, new JSONObject(jsonString));
                setStatus(Status.SUCCESS_CREATED);
            }

            StarterApplication.items.get(id).remove("data");
            StarterApplication.items.put(id, new JSONObject(jsonString));
            setStatus(Status.SUCCESS_OK);
            return StarterApplication.getJsonRepresentation(new JSONObject(StarterApplication.items.get(id)));
        } catch (JSONException e) {
            setStatus(Status.SERVER_ERROR_INTERNAL);
            return ErrorRepresentation.compose(e);
        }
    }

    @Delete
    @Options    //  FOR CORS
    public Representation removeItem() {
        StarterApplication.addCORSHeader(getResponse());
        if (!StarterApplication.items.containsKey(id)) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            ErrorRepresentation.compose("Could not locate item \"" + id + "\" for deletion.");
        } else
            StarterApplication.items.remove(id);
        setStatus(Status.SUCCESS_NO_CONTENT);
        return new EmptyRepresentation();
    }

    @Post
    public Representation storeItem(String jsonString) throws IOException {
        StarterApplication.addCORSHeader(getResponse());
        if (StarterApplication.items.containsKey(id)) {
            setStatus(Status.CLIENT_ERROR_CONFLICT);
            return ErrorRepresentation.compose("Item \"" + id + "\" already exists! (" +
                    StarterApplication.items.get(id) + ")");
        }

        try {
            JSONObject json = new JSONObject(jsonString);
            StarterApplication.items.put(id, json);
            setStatus(Status.SUCCESS_CREATED);
            return new EmptyRepresentation();
        } catch (JSONException e) {
            setStatus(Status.SERVER_ERROR_INTERNAL);
            return ErrorRepresentation.compose(e);
        }
    }

    @Get("application/json")
    public Representation getItemJsonRepresentation() {
        StarterApplication.addCORSHeader(getResponse());
        if (!StarterApplication.items.containsKey(id)) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ErrorRepresentation.compose("Item \"" + id + "\" not found.");
        }

        setStatus(Status.SUCCESS_OK);
        return StarterApplication.getJsonRepresentation(StarterApplication.items.get(id));
    }
}
