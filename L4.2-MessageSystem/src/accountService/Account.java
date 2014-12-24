package accountService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author e.shubin
 */
public final class Account {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final int id;
    private final String name;
    private final String password;

    public Account(String name, String password) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
