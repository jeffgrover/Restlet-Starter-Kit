
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RootResource extends ServerResource {

    private String emitPage(String contents) {
        return "<html><head><title>RESTFul Local Test User Interface</title>\n" +
                "<script src=\"http://code.jquery.com/jquery-1.9.1.min.js\"></script>\n" +
                "</head><body><h1 align=\"center\">Local RESTful Interactive Tester</h1>" +

                contents +

                "</ul></body></html>\n";
    }

    private String getItemSection() {
        return "<h3><a href=\"javascript:window.location.href = '/item/' + $('#itemID').val()\">" +
                "   / item / </a><input type=\"text\" id=\"itemID\" value=\"ItemID\"/> (lookup one item)</h3> ";
    }

    private String postItemSection() {
        return  "   <h3><u>C</u>reate an item\n" +
                "       <input type=\"text\" id=\"data\" value=\"SOME_DATA\"/>\n" +
                "       <input type=\"submit\" value=\"Create\" \n" +
                "           onClick=\"$.ajax({url: '/item/' + $('#itemID').val()," +
                "           data: '{data:\\'' + $('#data').val() + '\\'}', type: 'POST'" +
//                "           , success: alert('Created item: ' + $('#itemID').val())" +
                "})\"/></h3>\n";
    }

    private String putItemSection() {
        return  "   <h3><u>U</u>pdate an item\n" +
                "       <input type=\"text\" id=\"update\" value=\"DIFFERENT_DATA\"/>\n" +
                "       <input type=\"submit\" value=\"Update\"\n" +
                "           onClick=\"$.ajax({url: '/item/' + $('#itemID').val()," +
                "           data: '{data:\\'' + $('#update').val() + '\\'}', type: 'PUT'" +
//                "           , success: alert('Updated item: ' + $('#itemID').val())" +
                "})\"/></h3>\n";
    }


    private String deleteItemSection() {
        return  "   <h3><u>D</u>elete an item\n" +
                "       <input type=\"submit\" value=\"Delete\" \n" +
                "           onClick=\"$.ajax({url: '/item/' + $('#itemID').val(), type: 'DELETE'" +
//                "           ,success: alert('Deleted item: ' + $('#itemID').val())\n" +
                "})\"/></h3>\n";
    }

    private String getItemsListSection() {
        return  "   <h3>/ <a href=\"/items\">items</a> (list all items)<h3>\n";
    }

    private String getPageContents() {
        return getItemSection() + postItemSection() + putItemSection() + deleteItemSection() + getItemsListSection();
    }

    @Get("text/html")
    public Representation getUI() {
        try {
            return new StringRepresentation(emitPage(getPageContents()), MediaType.TEXT_HTML);
        } catch (Exception e) {
            return ErrorRepresentation.compose(e);

        }
    }


}

