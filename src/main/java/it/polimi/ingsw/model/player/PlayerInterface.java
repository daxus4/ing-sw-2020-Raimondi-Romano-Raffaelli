package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

/**
 * Interface of all methods implemented by Player and decorated classes of player
 * */
public interface PlayerInterface {

    /**
     * @return nickname of the player
     * */
    String getNickname();

    /**
     *  Set the situation when worker has never been moved yet
     *  @param cellOccupied is the new cell occupied
     *  @param cantGoUp say if is active a power that blocks moves up
     *  */
    void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp);

    /**
     *  Each time a user select a worker tile, Game will set the needed information through this method
     *  @param oldCell is the old cell
     *  @param cellOccupied is the new cell occupied
     *  @param cantGoUp say if is active a power that blocks moves up
     *  */
    void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp);

    /**
     *  Set the situation after a move
     *  @param oldCell is the old cell
     *  @param cellOccupied is the new cell occupied
     *  */
    void setAfterMove(Cell oldCell, Cell cellOccupied);

    /**
     * Setter of cantGoUp
     * @param cantGoUp say if is active a power that blocks moves up
     * */
    void setCantGoUp(boolean cantGoUp);

    /**
     * @return true iff user select a possible move action
     * @param adjacentPlayerList is a Map (@Position, @PlayerIndex) that contains the players on the cell moveCell
     * @param  moveCell is a Position that is the position to check
     * @throws  InvalidPositionException if movePos is an illegal position
     * @throws  NullPointerException if adjacentCells or adjacentPlayerList is null
     * */
    boolean canMove(Map<Position, PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException, NullPointerException;

    /**
     * @return true iff it is possible for the current player to move on a cell in moveCell
     * @param adjacentPlayerList is the Map (@Position, @PlayerIndex) that contains the players needed for the movement
     * @param moveCell is a List (@Cell) that contains the cell where to move
     * @param occupiedCell is the cell occupied before the move
     * @param cantGoUp is the value cantGoUp in Game
     * */
    boolean canMoveWithPowers(Map<Position, PlayerIndex> adjacentPlayerList, List<Cell> moveCell, Cell occupiedCell, boolean cantGoUp) throws InvalidPositionException, NullPointerException;

    /**
     * @return true if user select a possible build action
     * @param adjacentPlayerList is a Map (@Position, @PlayerIndex) that contains all the players adjacent to the selected worker
     * @param buildCell a Position that is the position to check
     * @throws  InvalidPositionException if movePos is an illegal position
     * @throws  NullPointerException is adjacentCells or adjacentPlayerList is null
     * */
    boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException;

    /**
     * @return true iff is verified a win condition
     * @throws  NullPointerException if is not selected any worker
     * */
    boolean hasWin() throws NullPointerException;

    /**
     *  Update the occupiedCell after a move with
     *  @param newOccupiedCell so the hasWin() method can runs correctly
     * */
    void move(Cell newOccupiedCell) throws NullPointerException;

    /**
     * Method that will be specialized in the Decorator class, it refers to a specific God power
     * @return true if is possible to use the power
     * @param adjacentList is a List (@Cell) that contains the cell needed for the specialized power
     * @param adjacentPlayerList is a Map (@Position, @PlayerIndex) that contains the players on the cells in adjacentList
     * */
    boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList);

    /**
     * Method that implements the power of a specific God, specialized in Decorator
     * @return a BoardChange that contains all the information to update the Board
     * @param powerCell is the Cell where the player wants to use the power
     * */
    BoardChange usePower(Cell powerCell);

    /**
     * @return the old Cell of the player
     * @throws NullPointerException if player has not set an old cell
     * */
    Cell getOldCell() throws NullPointerException;

    /**
     * @return the Cell occupied of the player
     * @throws NullPointerException if player has not set an old cell
     * */
    Cell getCellOccupied() throws NullPointerException;

    /**
     * Method used only in gods that needs two positions to use the power
     * @param firstPowerPos is the first power position
     * @return the second
     * */
    Position getSecondPowerPosition(Position firstPowerPos);

    /**
     * @return cantGoUp attribute
     * */
    boolean getCantGoUp();

    /**
     * @return the index of the player
     * */
    PlayerIndex getPlayerNum();

    /**
     * @return the number of power cells needed for the god power
     * */
    int getPowerListDimension();

    /**
     * @return the name of the god chosen
     * */
    String getGodName();

    /**
     * @return the state when user can use his god power
     * */
    GameState getPowerState();

    /**
     * @return the state where the game goes after using his god power
     * */
    GameState getNextState();
}
