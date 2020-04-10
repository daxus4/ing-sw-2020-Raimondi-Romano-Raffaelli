package org.example;

import java.util.Map;

public class BoardChange {
    private final Map<PositionContainer, PlayerIndex> playerChanges;
    private final Position positionBuild;
    private final BuildType buildType;
    private final boolean canGoUp;

    BoardChange(boolean canGoUp) {
        this.canGoUp = canGoUp;
        this.playerChanges = null;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;
    }

    BoardChange(Map<PositionContainer, PlayerIndex> playerChanges) {
        this.canGoUp = true;
        this.playerChanges = playerChanges;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;
    }

    public boolean getCanGoUp() {
        return canGoUp;
    }

    public Map<PositionContainer, PlayerIndex> getChanges() throws NullPointerException {
        if (playerChanges == null)
            throw new NullPointerException("changes");
        return playerChanges;
    }

    public Position getPositionBuild() {
        return positionBuild;
    }

    public BuildType getBuildType() {
        return buildType;
    }


}
