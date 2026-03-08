package server.commands;

import common.parser.CommandType;
import common.parser.Vehicle;
import server.collection.VehicleRandom;
import common.parser.ReturnCode;


import java.util.List;
import java.util.Scanner;

public class SortCommand implements Command{
    private final VehicleRandom vehicleRandom;
    private final CommandType type = CommandType.NOARGS;

    public SortCommand(VehicleRandom vehicleRandom){
        this.vehicleRandom = vehicleRandom;
    }

    @Override
    public ReturnCode execute(List<String> args, Vehicle vehicle, Boolean isLaud) {
        if (args.size() != 1) return ReturnCode.FAILED;
        else {
            vehicleRandom.sortByID();
            return ReturnCode.OK;
        }
    }

    @Override
    public String getDescription(){
        return " отсортировать коллекцию в естественном порядке";
    }

    @Override
    public CommandType getType() {
        return this.type;
    }


}
