    package server.commands;

    import common.parser.CommandType;
    import common.parser.ReturnCode;
    import common.parser.Vehicle;


    import java.util.List;
    import java.util.Scanner;

    public interface Command {
        ReturnCode execute(List<String> args, Vehicle vehicle, Boolean isLaud);
        String getDescription();
        CommandType getType();
    }
