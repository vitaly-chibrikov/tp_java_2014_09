package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author e.shubin
 */
public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final int id;

    public Address(){
        id = ID_GENERATOR.getAndIncrement();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
