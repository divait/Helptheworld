package co.waspp.divait.helptheworld.events;

/**
 * Created by divait on 1/10/2016.
 */

public class UserStateChangeEvent {
    private boolean isLooged;

    public UserStateChangeEvent(boolean isLogged) {
        this.isLooged = isLogged;
    }
}
