
//  This class represents resource data (usually persisted to DB, disk, etc.)

public class Item {

    private String data;

    public Item(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
