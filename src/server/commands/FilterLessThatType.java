package server.commands;


import common.parser.CommandType;
import common.parser.Vehicle;
import server.collection.VehicleManager;
import common.parser.ReturnCode;
import common.parser.VehicleType;

import java.util.List;
import java.util.Scanner;

public class FilterLessThatType implements Command{

    private final CommandType type = CommandType.WITHARGS;
    VehicleManager vehicleManager;
    public FilterLessThatType(VehicleManager vehicleCollection){
        this.vehicleManager = vehicleCollection;
    }

    @Override
    public ReturnCode execute(List<String> args, Vehicle vehicle, Boolean isLaud) {
        if (args.size() != 2) return ReturnCode.FAILED;
        try {
            VehicleType type = VehicleType.valueOf(args.get(1).toUpperCase());
            vehicleManager.filterLessThanType(type);
            return ReturnCode.OK;
        } catch (IllegalArgumentException e) {
            if(isLaud) System.out.println("Ошибка: неверный тип! Доступные: PLANE, HELICOPTER, BOAT, SHIP, HOVERBOARD");
            return ReturnCode.FAILED;
        }

    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля enginePower которых больше заданного";
    }


    @Override
    public CommandType getType() {
        return this.type;
    }


}
