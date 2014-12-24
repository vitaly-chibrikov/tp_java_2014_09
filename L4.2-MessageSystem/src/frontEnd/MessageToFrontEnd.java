package frontEnd;

import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * @author e.shubin
 */
public abstract class MessageToFrontEnd extends Message {
    public MessageToFrontEnd(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof FrontEnd) {
            exec((FrontEnd) abonent);
        }
    }

    protected abstract void exec(FrontEnd frontEnd);
}
