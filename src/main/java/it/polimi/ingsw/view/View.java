package it.polimi.ingsw.view;

import it.polimi.ingsw.message.MessageToServer;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;

/**
 * View is an abstract class which represents an abstraction of View
 * in the pattern MVC.
 */
public abstract class View extends Observable<MessageToServer> {

    private PlayerIndex player;

    public PlayerIndex getPlayer() {
        return player;
    }

    /**
     * If client of msg is correlated to player, it forward msg to controller
     *
     * @param msg message sent by client to be forwarded to the controller
     * @throws NullPointerException if msg is null
     */
    public void handleMessage(MessageToServer msg) throws NullPointerException {
        if (msg == null)
            throw new NullPointerException("msg");
        if (msg.getClient().compareTo(player) == 0)
            notify(msg);
    }


    public void setPlayer(PlayerIndex player) {
        this.player = player;
    }
}
