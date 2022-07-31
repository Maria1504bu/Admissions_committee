package command.client;

import command.*;

public enum CommandEnum {
    LOGIN {{
        this.command = new LoginCommand();
    }},
    LOGOUT {{
        this.command = new LogoutCommand();
    }},
    SIGNUP {{
        this.command = new SignupCommand();
    }},
    INITSIGNUP {{
        this.command = new InitSignupCommand();
    }},

    CANDIDATE_PROFILE {{
        this.command = new CandidateProfileCommand();
    }};
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }

}
