package sample;

public class Context {
    private final static Context instance = new Context();

    public static Context getInstance(){
        return instance;
    }

    private Person player1 = new Person("");

    public Person currentPlayer1(){
        return player1;
    }

    private Person player2 = new Person("");

    public Person currentPlayer2(){
        return player2;
    }
}
