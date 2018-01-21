import java.io.BufferedReader;
import java.io.FileReader;

public class BlocksMap {
    private Boolean map[][];
    private int size;
    private String path = "lab_map.txt";

    public BlocksMap(int size){
        this.size = size;
        checkSize();
        initMap();
        //printMap();
    }
    private void initMap(){
        map = new Boolean[size][size];
        try(
                BufferedReader reader = new BufferedReader(new FileReader(path));
                ){

            String line = reader.readLine(); // to ommit first line in file
            for(int i = 0; i < size; i++) {
                line = reader.readLine();
                for (int j = 0; j < size; j++){
                    if(line.charAt(j) == '0')
                        this.map[i][j] = Boolean.FALSE;
                    else{
                        this.map[i][j] = Boolean.TRUE;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public int getSize(){return this.size;};
    public Boolean isBlock(int x, int y){
        if(map[x][y] == Boolean.TRUE)
            return Boolean.TRUE;
        else return Boolean.FALSE;
    }
    private void checkSize(){
        try(
                BufferedReader reader = new BufferedReader(new FileReader(path));
            ){
            if(Integer.parseInt(reader.readLine()) != size)
                System.out.println("Size of map in server config file is diffrent from one in map file. Might cause problem");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void printMap(){
        for(int i = 0; i < size; i++){
            String line = "";
            for(int j = 0; j < size; j++) {
                if (map[i][j] == Boolean.FALSE)
                    line += "0";
                else
                    line += "1";
                line += "\n";
            }
            System.out.println(line);
        }
    }
}
