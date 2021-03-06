import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Application;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.routing.Router;
import org.restlet.util.Series;

import java.util.HashMap;
import java.util.Map;

public class StarterApplication extends Application {

    //  Container standing in for persistent DB, disk storage...
    public static Map<String, JSONObject> items = new HashMap<String, JSONObject>();

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {

        // Create a router Restlet that defines routes.
        Router router = new Router(getContext());

        router.attach("/", RootResource.class);

        router.attach("/items", ItemsResource.class);
        router.attach("/item/{id}", ItemResource.class);

        return router;
    }

    public static Representation getJsonRepresentation(JSONObject json) {
        JsonRepresentation representation = new JsonRepresentation(json);
        representation.setIndenting(true); //  TODO:  Consider commenting out for production
        return representation;
    }

    public static Representation getJsonRepresentation(String name, JSONArray json) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(name, json);
        } catch (JSONException e) {
            return ErrorRepresentation.compose(e);
        }
        return getJsonRepresentation(jsonObject);
    }

    public static void addCORSHeader(Response response) {
        Series<Header> responseHeaders = (Series<Header>) response.getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            response.getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"));
        responseHeaders.add(new Header("Access-Control-Allow-Headers", "origin, x-requested-with, content-type"));
    }

}