package sa.pums.pums.Model;


public class dataModel {
    public int id;
    public String Id_2;

    public String name;

    public dataModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public dataModel(String id, String name) {
        Id_2 = id;
        this.name = name;
    }

    public void setId(String id) {
        Id_2 = id;
    }

    public String getId_2() {
        return Id_2;
    }

    public void setId_2(String id_2) {
        Id_2 = id_2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
