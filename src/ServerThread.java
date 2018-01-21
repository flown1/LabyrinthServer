
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread {
    private Socket socket = null;

    private int clientID;

    public ServerThread(Socket socket, int clientID){
        super("ServerThread");
        this.socket = socket;
        this.clientID = clientID;
    }
    private void sendBoardSize(PrintWriter out){
        out.println(Server.config.getBoardSize());
    }
    private void sendBlocksMap(PrintWriter out){
        try{
            System.out.println("[OUT] Sending BlocksMap");
            int size = Server.blocksmap.getSize();

            out.println("MAP_INCOMMING");
            for(int i = 0; i<size; i++) {
                for (int j = 0; j < size; j++) {
                    if (Server.blocksmap.isBlock(i, j) == Boolean.FALSE) {
                        out.println("0");
                    }else
                        out.println("1");
                }
            }
            out.println("END_MAP");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void handleWinInfo(BufferedReader in, PrintWriter out){

        try{
            String playerName = in.readLine();
            Server.gameInfo.setIsActive(false);
            Server.gameInfo.setWinnerName(playerName);
            System.out.format("Player %s WON. Propagating WIN info...\n", playerName);
            out.println("OTHER_PLAYER_WON");
            out.println(Server.gameInfo.getWinnerName());
        }catch (Exception e){
            System.out.format("%s\n", e);
        }
    }
    private void handleGameDataRequest(PrintWriter out){
//        System.out.println("Will try to send game data to user...");
//        System.out.format("Game isActive=%s\n",Server.gameInfo.getIsActive());
        try {
            if(Server.gameInfo.getIsActive() == false)
                out.println("GAME_ENDED");
            else
                out.println("GAME_NOT_ENDED");
        }catch(Exception e){
            System.out.println("[EXC] While sending Game Data");
        }
    }
    private void handlePlayerNewPosition(BufferedReader in){
        try {
            int newX = Integer.parseInt(in.readLine());
            int newY = Integer.parseInt(in.readLine());

            System.out.format("New player\'s coords are: %d %d\n", newX, newY);
            Server.updatePosition(clientID, newX, newY);
        }catch(Exception e){
            System.out.println("[EXC] Cannot read new coords of player");
            System.out.println(e);
        }
    }
    private void handleSendPlayersPostitions(PrintWriter out){
        try {
            System.out.println("[OUT] Sending players positions");
            out.println("DATA_INCOMMING");
            for(UserData p : Server.players){
                out.println("PLAYER_INFO_COMMING");
                out.println(p.getX());
                out.println(p.getY());
            }
            out.println("END_OF_DATA");
        }catch(Exception e){
            System.out.println("[EXC] While sending Game Data");
        }
    }
    public void run(){
        System.out.format("New client %d thread running...\n",clientID);
        try{

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendBoardSize(out);

            String inInfo;
            while ((inInfo = in.readLine()) != null) {

                switch (inInfo) {
                    case "SOMEONE_WON":
                        handleWinInfo(in,out);
                        break;
                    case "GIMME_GAME_DATA":
                        handleGameDataRequest(out);
                        break;
                    case "PLAYER_NEW_POSITION":
                        handlePlayerNewPosition(in);
                        break;
                    case "SEND_PLAYERS_POSITIONS":
                        handleSendPlayersPostitions(out);
                        break;
                    case "GIMME_MAP":
                        /* WYLACZYC JESLI MAPA NA SZTYWNO*/
                        sendBlocksMap(out);
                        break;
                    default:
                        System.out.format("[WARN] UNEXPECTED INFO RECEIVED: %s\n", inInfo);
                        break;
                }
            }
        }catch(SocketException e){
            System.out.println("[EXC]socket exception while reading from client. Possibly disconnected");
        }catch(Exception e){
            System.out.println(e);
            System.exit(1);
        }
    }
}