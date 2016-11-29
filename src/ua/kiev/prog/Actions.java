package ua.kiev.prog;


import java.util.HashMap;
import java.util.Map;

public enum Actions {

    LOG_IN("logIn"),
    GET_LIST("getList");

    private static Map<String, Actions> actionsMap = new HashMap<>();
    private String action;

    Actions(String action) {
        this.action = action;
    }

    static {
        for (Actions a : Actions.values()) {
            actionsMap.put(a.action, a);
        }
    }

    public static  Actions getActionById(String id) {
        return actionsMap.get(id);
    }
}
