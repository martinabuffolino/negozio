package command.pattern;

public class smsCommand implements Command {

    Sms sms;

    public smsCommand(Sms sms){
        this.sms = sms;
    }

    @Override
    public void execute() {
        sms.sendSms();

    }
}
