package accountService;

import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * @author e.shubin
 */
public abstract class MessageToAccountService extends Message {
    public MessageToAccountService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public final void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    protected abstract void exec(AccountService service);
}
