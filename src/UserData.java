public class UserData {
    private int id;
    private String name;
    private int x_coord;
    private int y_coord;

    public UserData(int id){this.id = id;}
    public void setName(String s){this.name = name;}
    public void setX(int x_coord){this.x_coord = x_coord;}
    public void setY(int y_coord){this.y_coord = y_coord;}
    public int getId(){return id;};
    public String getName(){return name;}
    public int getX(){return x_coord;}
    public int getY(){return y_coord;}
}
