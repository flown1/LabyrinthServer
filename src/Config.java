import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Config {
    private int port,
                boardSize;
    private String hostname;

    public Config(){
        readConfig();
    }
    private void readConfig(){
        info("[CONFIG] Trying to read configuration");
        try{
            File configFile = new File("config.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document configDoc = documentBuilder.parse(configFile);

            this.port = Integer.parseInt(configDoc.getElementsByTagName("port").item(0).getTextContent());
            this.hostname = configDoc.getElementsByTagName("hostname").item(0).getTextContent();
            this.boardSize = Integer.parseInt(configDoc.getElementsByTagName("boardsize").item(0).getTextContent());

            info("[CONFIG] Reading config successful!");
        }catch(Exception e){
            info("[ERROR] Failed while getting config");
        }
    }

    public int getPort(){return this.port;};
    public String getHostname(){return this.hostname;};
    public int getBoardSize(){return this.boardSize;};

    private void info(String s){System.out.println(s);};
}
