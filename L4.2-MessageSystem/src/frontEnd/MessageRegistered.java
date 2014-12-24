package frontEnd;

import messageSystem.Address;

/**
 * @author e.shubin
 */
public final class MessageRegistered extends MessageToFrontEnd {
    private String name;
    private boolean registered;

    public MessageRegistered(Address from, Address to, String name, boolean registered) {
        super(from, to);
        this.name = name;
        this.registered = registered;
    }

    @Override
    protected void exec(FrontEnd frontEnd) {
        frontEnd.registered(name, registered);
    }
}
