package accountService;

import frontEnd.MessageIsAuthenticated;
import messageSystem.Address;
import messageSystem.Message;

/**
 * @author e.shubin
 */
public final class MessageAuthenticate extends MessageToAccountService {
    private String name;
    private String password;
    private String sessionId;

    public MessageAuthenticate(Address from, Address to, String name, String password, String sessionId) {
        super(from, to);
        this.name = name;
        this.password = password;
        this.sessionId = sessionId;
    }

    @Override
    protected void exec(AccountService service) {
        final Account account = service.getAccountDAO().authenticate(name, password);
        final Message back = new MessageIsAuthenticated(getTo(), getFrom(), sessionId, account);
        service.getMessageSystem().sendMessage(back);
    }
}
