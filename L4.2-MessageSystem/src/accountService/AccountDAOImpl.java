package accountService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author e.shubin
 */
public final class AccountDAOImpl implements AccountDAO {
    private static final Map<String, Account> ACCOUNT_MAP = new HashMap<>();

    @Override
    public boolean register(String name, String password) {
        synchronized (ACCOUNT_MAP) {
            if (ACCOUNT_MAP.containsKey(name)) {
                return false;
            }
            ACCOUNT_MAP.put(name, new Account(name, password));
            return true;
        }
    }

    @Override
    public Account authenticate(String name, String password) {
        Account account = ACCOUNT_MAP.get(name);
        if (account == null || !account.getPassword().equals(password)) {
            return null;
        }

        return account;
    }
}
