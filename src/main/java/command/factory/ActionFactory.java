package command.factory;

import command.ActionCommand;
import command.client.CommandEnum;
import jakarta.servlet.http.HttpServletRequest;
import managers.ConfigurationManager;
import managers.MessageManager;

import java.util.Locale;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest req){
        ActionCommand currentCommand = new ActionCommand() {
            @Override
            public String execute(HttpServletRequest req) {
                return ConfigurationManager.getProperty("path.page.index");
            }
        };// TODO: переробити на блокировка запроса на випадок якщо невалідне значення параметру команд
        String action = req.getParameter("command");
        if (action == null || action.isEmpty()){
            return currentCommand;
        }
        try{
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            currentCommand = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            req.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongAction"));
        }
        return currentCommand;
    }
}
