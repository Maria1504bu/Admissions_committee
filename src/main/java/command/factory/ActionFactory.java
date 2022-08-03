package command.factory;

import command.ActionCommand;
import command.CommandContainer;
import managers.ConfigurationManager;
import managers.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest req){
        ActionCommand currentCommand = new ActionCommand() {
            @Override
            public String execute(HttpServletRequest req) {
                return ConfigurationManager.getProperty("path.commmon.index");
            }
        };// TODO: переробити на блокировка запроса на випадок якщо невалідне значення параметру команд
        String commandName = req.getParameter("command");
        if (commandName == null || commandName.isEmpty()){
            return currentCommand;
        }
        try{
            currentCommand = CommandContainer.get(commandName);
        } catch (IllegalArgumentException e) {
            req.setAttribute("wrongCommandName", commandName + MessageManager.getProperty("message.wrongAction"));
        }
        return currentCommand;
    }
}
