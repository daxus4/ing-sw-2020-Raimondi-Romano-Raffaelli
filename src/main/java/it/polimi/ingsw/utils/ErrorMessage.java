package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class ErrorMessage extends InformationMessage {

    public ErrorMessage(PlayerIndex client, TypeMessage specificErrorType, String errorMessage) {
        super(client, TypeMessage.ERROR, specificErrorType, errorMessage);
    }

    public String getErrorMessage() {
        return super.getString();
    }

    public TypeMessage getSpecificErrorType() {
        return super.getSpecificType();
    }
}