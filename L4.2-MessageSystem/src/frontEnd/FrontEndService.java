package frontEnd;

/**
 * @author e.shubin
 */
public interface FrontEndService {
    public void register(String name, String password);

    public boolean isRegistered(String name);

    public String authenticate(String name, String password);

    public boolean isAuthenticated(String sessionId);

    public int getScore(String sessionId);

    public void updateScore(String sessionId, int delta);
}
