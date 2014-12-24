package accountService;

/**
 * @author e.shubin
 */
public interface AccountDAO {
    boolean register(String name, String password);

    Account authenticate(String name, String password);
}
