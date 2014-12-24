package gameMechanics;

/**
 * @author e.shubin
 */
public final class UserScore {
    private final int userAccountId;
    private int score = GameRules.DEFAULT_SCORE;

    public UserScore(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
