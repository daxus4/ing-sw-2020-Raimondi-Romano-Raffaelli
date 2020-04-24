package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.MaxPlayersException;
import it.polimi.ingsw.exception.NameAlreadyTakenException;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameLobby {

    private HashMap<PlayerIndex, String> lobbyPlayers;
    private boolean threePlayersGame;

    public GameLobby(){
        this.lobbyPlayers = new HashMap<>();
    }

    public void setThreePlayersGame(boolean threePlayersGame){
        this.threePlayersGame = threePlayersGame;
    }

    public void addPlayer(PlayerIndex playerIndex, String playerNick){
        if(isFull()) throw new MaxPlayersException();
        if(isNameAlreadyTaken(playerNick)) throw new NameAlreadyTakenException();
        if(isPlayerAlreadyInLobby(playerIndex)) throw new IllegalArgumentException("Player " + playerIndex + " is already in the lobby");

        this.lobbyPlayers.put(playerIndex, playerNick);
    }

    public HashMap<PlayerIndex, String> getLobbyPlayers(){
        return this.lobbyPlayers;
    }

    public boolean isNameAlreadyTaken(String nickName){
        for(String s : this.lobbyPlayers.values()){
            if(nickName.equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerAlreadyInLobby(PlayerIndex playerIndex){
        return this.lobbyPlayers.keySet().contains(playerIndex);
    }

    public boolean isFull(){
        if(this.threePlayersGame){
            return this.lobbyPlayers.size() == 3;
        }
        return this.lobbyPlayers.size() == 2;
    }
}
