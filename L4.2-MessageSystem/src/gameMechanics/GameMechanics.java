package gameMechanics;

import main.ThreadSettings;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;

/**
 * @author e.shubin
 */
public final class GameMechanics implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystem messageSystem;

    public GameMechanics(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerGameMechanics(this);
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public void increaseScore(UserScore score, int delta) {
        int newScore = score.getScore() + delta;

        if (newScore > GameRules.MAX_SCORE) {
            newScore = GameRules.MAX_SCORE;
        }

        if (newScore < GameRules.MIN_SCORE) {
            newScore = GameRules.MIN_SCORE;
        }

        score.setScore(newScore);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(ThreadSettings.SERVICE_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
