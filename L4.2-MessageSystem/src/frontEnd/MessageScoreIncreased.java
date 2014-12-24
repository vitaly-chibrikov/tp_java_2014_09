package frontEnd;

import gameMechanics.UserScore;
import messageSystem.Address;

/**
 * @author e.shubin
 */
public final class MessageScoreIncreased extends MessageToFrontEnd {
    private UserScore score;

    public MessageScoreIncreased(Address from, Address to, UserScore score) {
        super(from, to);
        this.score = score;
    }

    @Override
    protected void exec(FrontEnd frontEnd) {
        frontEnd.setScore(score);
    }
}
