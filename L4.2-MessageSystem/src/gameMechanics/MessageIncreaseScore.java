package gameMechanics;

import frontEnd.MessageScoreIncreased;
import messageSystem.Address;
import messageSystem.Message;

/**
 * @author e.shubin
 */
public final class MessageIncreaseScore extends MessageToGameMechanics {
    private UserScore score;
    private int delta;

    public MessageIncreaseScore(Address from, Address to, UserScore score, int delta) {
        super(from, to);
        this.score = score;
        this.delta = delta;
    }

    @Override
    protected void exec(GameMechanics gameMechanics) {
        gameMechanics.increaseScore(score, delta);
        Message back = new MessageScoreIncreased(getTo(), getFrom(), score);
        gameMechanics.getMessageSystem().sendMessage(back);
    }
}
