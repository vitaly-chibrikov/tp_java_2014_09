package base;

import frontend.GameWebSocket;

/**
 * @author v.chibrikov
 */
public interface WebSocketService {

    public void addUser(GameWebSocket user);

    public void notifyMyNewScore(GameUser user);

    public void notifyEnemyNewScore(GameUser user);

    public void notifyStartGame(GameUser user);

    public void notifyGameOver(GameUser user, boolean win);
}
