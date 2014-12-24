package frontEnd;

import accountService.Account;
import accountService.MessageAuthenticate;
import accountService.MessageRegister;
import gameMechanics.MessageIncreaseScore;
import gameMechanics.UserScore;
import main.ThreadSettings;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.MessageSystem;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author e.shubin
 */
public final class FrontEnd implements FrontEndService, Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystem messageSystem;

    private final Map<String, Boolean> waitingUsers = new HashMap<>();
    private final Map<String, Account> accountMap = new HashMap<>();
    private final Map<Integer, UserScore> scoreMap = new HashMap<>();

    public FrontEnd(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerFrontEnd(this);
    }

    @Override
    public void register(String name, String password) {
        final Message messageRegister = new MessageRegister(getAddress(), messageSystem.getAddressService().getAccountServiceAddress(), name, password);
        messageSystem.sendMessage(messageRegister);
    }

    @Override
    public boolean isRegistered(String name) {
        Boolean value = waitingUsers.get(name);
        return value != null && value;
    }

    @Override
    public String authenticate(String name, String password) {
        final String sessionId = UUID.randomUUID().toString();
        Message messageAuthenticate = new MessageAuthenticate(getAddress(), messageSystem.getAddressService().getAccountServiceAddress(), name, password, sessionId);
        messageSystem.sendMessage(messageAuthenticate);
        return sessionId;
    }

    @Override
    public boolean isAuthenticated(String sessionId) {
        return accountMap.containsKey(sessionId);
    }

    @Override
    public int getScore(String sessionId) {
        final Account account = accountMap.get(sessionId);
        if (account == null) {
            throw new InvalidStateException("Wrong session id");
        }

        final UserScore userScore = scoreMap.get(account.getId());
        return userScore.getScore();
    }

    @Override
    public void updateScore(String sessionId, int delta) {
        final Account account = accountMap.get(sessionId);
        if (account == null) {
            throw new InvalidStateException("Wrong session id");
        }

        Message messageIncreaseScore = new MessageIncreaseScore(getAddress(), messageSystem.getAddressService().getGameMechanicsAddress(), scoreMap.get(account.getId()), delta);
        messageSystem.sendMessage(messageIncreaseScore);
    }

    void registered(String name, boolean result) {
        waitingUsers.put(name, result);
    }

    void authenticated(String sessionId, Account account) {
        accountMap.put(sessionId, account);
        if(account != null) {
            scoreMap.put(account.getId(), new UserScore(account.getId()));
        }
    }

    void setScore(UserScore score) {
        scoreMap.put(score.getUserAccountId(), score);
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
