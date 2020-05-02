package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;

    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private Map<PlayerIndex, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();


    private static int lobbyCount = 0;
    private Game game = new Game();
    private GameManager controller = new GameManager(game);
    private Thread pingThread;
    private boolean isActive = true;

    public synchronized void setActive(boolean condition){
        this.isActive = condition;
    }

    public synchronized boolean getActive(){
        return isActive;
    }

    private Server getServer(){
        return this;
    }

    /**
     * @param c connection to eliminate from the list of client player in lobby
     */
    public synchronized void deleteClient(ClientConnection c){
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null){
            opponent.closeConnection();
        }
        playingConnection.remove(opponent);
        playingConnection.remove(c);
        Iterator<PlayerIndex> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    /**
     * @param c connection to insert in lobby for the game
     */
    public synchronized void lobby(ClientConnection c) {

        if (lobbyCount == 0) {
            waitingConnection.put(PlayerIndex.PLAYER0, c);
            ClientConnection c1 = waitingConnection.get(PlayerIndex.PLAYER0);
            RemoteView player1View = new RemoteView(PlayerIndex.PLAYER0, c1);
            player1View.addObserver(controller);
            controller.addRemoteView(PlayerIndex.PLAYER0, player1View);
            lobbyCount++;
        }
        else if(lobbyCount == 1){
            waitingConnection.put(PlayerIndex.PLAYER1, c);
            if(waitingConnection.size() == 2){
                ClientConnection c2 = waitingConnection.get(PlayerIndex.PLAYER1);
                RemoteView player2View = new RemoteView(PlayerIndex.PLAYER1, c2);
                player2View.addObserver(controller);
                controller.addRemoteView(PlayerIndex.PLAYER1, player2View);
                lobbyCount++;
            }
        }
        else if (lobbyCount == 2 && controller.getPlayerNum() == 3) {
            waitingConnection.put(PlayerIndex.PLAYER2,c);
            if(waitingConnection.size() == 3) {
                ClientConnection c3 = waitingConnection.get(PlayerIndex.PLAYER2);
                RemoteView player3View = new RemoteView(PlayerIndex.PLAYER2, c3);
                player3View.addObserver(controller);
                controller.addRemoteView(PlayerIndex.PLAYER2, player3View);
                lobbyCount = 5;
                }
            }
        }

     public Thread pingRunThread(){
        Thread t = new Thread(() -> {
            while(getActive()){
                for(Map.Entry<PlayerIndex,ClientConnection> client : waitingConnection.entrySet()){
                    if(client != null && client.getValue().isConnected()){
                        client.getValue().ping(client.getKey());
                    }
                }
                try{
                    pingThread.sleep(1000);
                }catch (InterruptedException e){
                    System.err.println("Ping thread is interrupted");
                    e.printStackTrace();
                    setActive(false);
                    pingThread.interrupt();
                }
            }
        });
        t.start();
        return t;

     }

     public Thread threadInConnection(){
        Thread t = new Thread(() -> {
            while (true){
                try{
                    Socket socket = serverSocket.accept();
                    System.out.println("Client is connected");
                    SocketClientConnection socketClientConnection = new SocketClientConnection(socket,getServer());
                    executor.submit(socketClientConnection);
                }catch (IOException e){
                    System.err.println("Error during the open port on server");
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return t;
     }


    public Server() throws IOException{
        this.serverSocket = new ServerSocket(PORT);
        System.out.println("Port is open ");
    }

    public void run(){

            try {
               pingThread = pingRunThread();
               Thread runThread = threadInConnection();
               runThread.join();
               pingThread.join();
            }catch (InterruptedException | NoSuchElementException e){
                System.err.println("Error during the join of threads");
                e.printStackTrace();
            }
        }
    }


