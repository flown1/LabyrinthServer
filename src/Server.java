import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    static ArrayList<UserData> usersData = new ArrayList<UserData>();

    static Config config = new Config();
    static BlocksMap blocksmap = new BlocksMap(config.getBoardSize());
    static ArrayList<ServerThread> connections;
    static GameInfo gameInfo = new GameInfo();
    static ArrayList<UserData> players;

    public static void updatePosition(int clientID, int newX, int newY){
        for (UserData p : players){
            if(p.getId() == clientID){
                p.setX(newX);
                p.setY(newY);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (
            ServerSocket serverSocket = new ServerSocket(config.getPort());
        ){
            int clientID = 0;
            connections = new ArrayList<ServerThread>();
            players = new ArrayList<UserData>();

            System.out.println("Server running...");
            while(true) {
                clientID += 1;
                connections.add(new ServerThread(serverSocket.accept(), clientID));
                connections.get(connections.size() - 1).start();
                UserData userData = new UserData(clientID);
                players.add(userData);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}