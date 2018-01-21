public class GameInfo {
    private Boolean isActive;
    private String winnerName;

    public GameInfo(){
        this.isActive = true;
        this.winnerName = "---";
    }
    public void setWinnerName(String name){this.winnerName = name;}
    public void setIsActive(Boolean val){this.isActive = val;}
    public Boolean getIsActive(){return this.isActive;}
    public String getWinnerName(){return  this.winnerName;}
}
