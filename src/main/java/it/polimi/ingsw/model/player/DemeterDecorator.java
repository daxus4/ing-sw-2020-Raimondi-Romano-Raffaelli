package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class DemeterDecorator extends PlayerBuildDecorator {

    private Cell firstBuildCell;

    public DemeterDecorator(String godName, String description, GameState powerState, GameState nextState){

        super(godName, description, powerState, nextState);
    }



    @Override
    public boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell){
        this.firstBuildCell = buildCell;
        return super.canBuild(adjacentPlayerList, buildCell);
    }


    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canBuild(adjacentPlayerList, adjacentList.get(0)) && !adjacentList.get(0).equals(this.firstBuildCell);
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        return new BoardChange(powerCell.getPosition(), BuildType.LEVEL);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

    @Override
    public int getPowerListDimension(){
        return 1;
    }


}

