package accountService;

import main.ThreadSettings;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;

/**
 * @author e.shubin
 */
public final class AccountService implements Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystem messageSystem;

    private final AccountDAO accountDAO;

    public AccountService(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerAccountService(this);

        this.accountDAO = new AccountDAOImpl();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true){
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(ThreadSettings.SERVICE_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
