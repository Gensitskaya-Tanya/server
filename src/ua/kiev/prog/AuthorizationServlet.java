package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuthorizationServlet extends HttpServlet {
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ACTION = "action";
    private static Map<String, String> authorizationMap = new HashMap<>();
    private static List<User> userList = new ArrayList<>();

    static {
        authorizationMap.put("tanya", "1");
        authorizationMap.put("anya", "2");
        authorizationMap.put("katya", "3");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION);
        String login = req.getParameter(LOGIN);
        String pass = req.getParameter(PASSWORD);
        Actions currentAction = Actions.getActionById(action);
        switch (currentAction){
            case LOG_IN:
                boolean validationResult = validation(login, pass);
                if (validationResult) {
                    User user = new User(login, pass);
                    userList.add(user);
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;
            case GET_LIST:
                StringBuilder sb = new StringBuilder();
                for(User u : userList){
                   sb.append(u.getLogin()).append(" ");
                }
                resp.setStatus(HttpServletResponse.SC_OK);
                try (OutputStream os = resp.getOutputStream()) {
                    byte[] buf = sb.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(buf);
                }
                break;
        }

    }

    private boolean validation(String login, String pass) {
        String tempPass = authorizationMap.get(login);
        if (tempPass == null) {
            return false;
        } else if (!tempPass.equals(pass)) {
            return false;
        } else {
            boolean temp = false;
            for (User u : userList) {
                temp = u.getLogin().equals(login) && u.getPassword().equals(pass);
                if (temp) {
                    break;
                }
            }
            return !temp;
        }
    }
}
