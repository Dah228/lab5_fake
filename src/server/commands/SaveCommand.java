package server.commands;

import common.parser.CommandType;
import common.parser.Vehicle;
import server.collection.VehicleSaver;
import common.parser.ReturnCode;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;
import java.util.Scanner;

public class SaveCommand implements Command{
    VehicleSaver vehicleSaver;
    private final CommandType type = CommandType.NOARGS;

    public SaveCommand(VehicleSaver vehicleSaver){
        this.vehicleSaver = vehicleSaver;
    }

@Override
    public ReturnCode execute(List<String> args, Vehicle vehicle, Boolean isLaud){
        if (args.size()!= 1) return ReturnCode.FAILED;
        try {
            vehicleSaver.saveToFile();
            return ReturnCode.OK;
        } catch (ParserConfigurationException e) {
            System.out.println("Нарушена поддержка парсера");
            return ReturnCode.FAILED;
        } catch (TransformerException e) {
            System.out.println("Ошибка записи файла");
            return ReturnCode.FAILED;
        }
    }

    @Override
    public String getDescription() {
        return " сохранить коллекцию в файл";
    }

    @Override
    public CommandType getType() {
        return this.type;
    }
}
