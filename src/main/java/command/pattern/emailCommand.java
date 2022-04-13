package command.pattern;

public class emailCommand implements Command{

    Email email;

    public emailCommand(Email email){
        this.email = email;
    }

    @Override
    public void execute() {
        email.sendEmail();
    }
}