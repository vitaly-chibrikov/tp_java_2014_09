package main.clients;

import frontEnd.FrontEndService;
import gameMechanics.GameRules;
import main.ThreadSettings;

import java.util.Random;

/**
 * Created by esin on 06.12.2014.
 */
public class Client extends Thread {
    private static final Random RANDOM = new Random();
    private FrontEndService service;
    private String name;
    private String password;
    private String sessionId = "";

    private State state = State.Register;

    public Client(FrontEndService service, String name) {
        this.service = service;
        this.name = name;
        this.password = name;
        setName(name);
    }

    @Override
    public void run() {
        while (state != State.Finished) {
            switch (state) {
                case Register:
                    register();
                    break;
                case CheckRegistration:
                    checkRegistration();
                    break;
                case Auth:
                    auth();
                    break;
                case CheckAuth:
                    checkAuth();
                    break;
                case GetScore:
                    getScore();
                    break;
                case IncreaseScore:
                    increaseScore();
                    break;
            }

            try {
                Thread.sleep(ThreadSettings.CLIENT_SLEEP_TIME + RANDOM.nextInt(ThreadSettings.CLIENT_SLEEP_TIME * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void register() {
        System.out.println(name + " is registering...");
        service.register(name, password);
        state = State.CheckRegistration;
    }

    private void checkRegistration() {
        final boolean registered = service.isRegistered(name);
        System.out.println(name + " registered: " + registered);
        if (registered) {
            state = State.Auth;
        }
    }

    private void auth() {
        System.out.println(name + " is authenticating...");
        sessionId = service.authenticate(name, password);
        System.out.println(name + " got sessionId: " + sessionId);
        state = State.CheckAuth;
    }

    private void checkAuth() {
        System.out.println(name + " is checking auth for session " + sessionId);
        final boolean authenticated = service.isAuthenticated(sessionId);
        System.out.println(name + " is authenticated: " + authenticated);
        if (authenticated) {
            state = State.GetScore;
        }
    }

    private void getScore() {
        final int score = service.getScore(sessionId);
        System.out.println(name + " score is " + score);

        if (score == GameRules.MAX_SCORE || score == GameRules.MIN_SCORE) {
            System.out.println("==== " + name + " finished with score " + score + " ====");
            state = State.Finished;
        } else {
            state = State.IncreaseScore;
        }
    }

    private void increaseScore() {
        final int delta = RANDOM.nextInt(GameRules.MAX_SCORE) - (GameRules.MAX_SCORE / 2);
        System.out.println(name + " increasing score by " + delta);
        service.updateScore(sessionId, delta);
        state = State.GetScore;
    }

    private enum State {
        Register,
        CheckRegistration,
        Auth,
        CheckAuth,
        GetScore,
        IncreaseScore,
        Finished
    }
}
