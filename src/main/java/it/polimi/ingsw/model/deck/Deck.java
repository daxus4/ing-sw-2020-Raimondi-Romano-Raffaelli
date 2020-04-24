package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidNumberCardsChosenException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.player.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private static final List<CardInterface> godCards = new ArrayList<>(
            List.of(
                    new ApolloDecorator("Apollo", "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.", GameState.MOVE, GameState.CHECKWIN),
                    new ArtemisDecorator("Artemis", "Your Worker may move one additional time, but not back to its initial space.", GameState.MOVE, GameState.CHECKWIN),
                    new AthenaDecorator("Athena", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", GameState.MOVE, GameState.CHECKWIN),
                    new AtlasDecorator("Atlas", "Your Worker may build a dome at any level.", GameState.BUILD, GameState.ENDTURN),
                    new DemeterDecorator("Demeter", "Your Worker may build one additional time, but not on the same space.", GameState.BUILD, GameState.ENDTURN),
                    new HephaestusDecorator("Hephaestus", "Your Worker may build one additional block (not dome) on top of your first block.", GameState.BUILD, GameState.ENDTURN),
                    new MinotaurDecorator("Minotaur", "our Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", GameState.MOVE, GameState.CHECKWIN),
                    new PanDecorator("Pan", "You also win if your Worker moves down two or more levels.", GameState.MOVE, GameState.CHECKWIN),
                    new PrometheusDecorator("Prometheus", "If your Worker does not move up, it may build both before and after moving.", GameState.INITURN, GameState.CANMOVE)
            )
    );
    public final static int size = 9;
    private final int playersNumber;

    public Deck(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public static boolean isCorrectedName(String name) throws NullPointerException {
        if (name == null)
            throw new NullPointerException("name");
        return godCards.stream().anyMatch(card -> card.getGodName().equals(name));
    }

    public void playerGodLikeChoose(CardInterface card) throws NullPointerException {
        if (card == null)
            throw new NullPointerException("Card is null");
        card.setChosenGod(true);
    }

    public void setChosenGodCards(List<String> gods) throws NullPointerException, ArrayIndexOutOfBoundsException {
        if (gods == null)
            throw new NullPointerException("gods");
        if (gods.size() != playersNumber)
            throw new InvalidNumberCardsChosenException(playersNumber, gods.size());

        for (String name : gods) {
            boolean thereIs = false;
            for (CardInterface god : godCards) {
                if (name.equals(god.getGodName())){
                    god.setChosenGod(true);
                    thereIs = true;
                }
            }
            if (!thereIs)
                throw new WrongGodNameException(name);
        }
    }

    public List<CardInterface> getGodCards() {
        return godCards;
    }

    public CardInterface getGodCard(String name) throws NullPointerException, WrongGodNameException {
        if (name == null)
            throw new NullPointerException("name");
        for (CardInterface card : godCards) {
            if (card.getGodName().equals(name))
                return card;
        }
        throw new WrongGodNameException(name);
    }

    public List<CardInterface> getChosenGodCards() {
        return godCards.stream()
                .filter(CardInterface::getBoolChosenGod)
                .collect(Collectors.toList());
    }

}
