package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class HephaestusDecorator extends PlayerBuildDecorator {


    private Cell buildCell;

    public HephaestusDecorator(String godName, String description, GameState powerState, GameState nextState){

        super(godName, description, powerState, nextState);
    }



    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }


    @Override
    public boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException {
        this.buildCell = buildCell;
        return super.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public void activePowerAfterBuild() {
        this.buildCell.incrementLevel();

    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        if(super.canBuild(adjacentPlayerList, adjacentList.get(0)) && this.buildCell.equals(adjacentList.get(0)) && (adjacentList.get(0).getLevel() < 3))
            return true;
        return false;
    }

    @Override
    public BoardChange usePower(Cell powerCell) {
        return new BoardChange(powerCell.getPosition(), BuildType.LEVEL);
    }

    @Override
    public int getPowerListDimension(){
        return 1;
    }


}
