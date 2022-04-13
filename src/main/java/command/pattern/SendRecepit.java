package command.pattern;

public class SendRecepit {

    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }

    public void send(){
        command.execute();
    }

}