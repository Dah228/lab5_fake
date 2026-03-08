package client.mainpart;

import common.parser.Invoker;
import server.collection.*;
import common.parser.Parser;
import common.parser.Vehicle;
import server.commands.CommandsList;




import static common.parser.Vehicle.printVehicle;

public class Main {
    public static void main(String[] arg) {

        VehicleCollection collection = new VehicleCollection();

        CommandsList commandsList = new CommandsList();
        AllCommands allCommands = new AllCommands(commandsList);

        Invoker invoker = commandsList.getInvoker();



        try {
            String filePath = arg.length > 0 ? arg[0] : "server.collection.xml";
            collection.addList(Parser.parse(filePath));
            for (Vehicle v : collection.getVehicles()) printVehicle(v);
        } catch (Exception e) {
            System.out.println("Указанного файла не существует/ не соответствует заданному формату введите корректный файл");
        }


        Executor executor = new Executor(invoker);


        executor.Execute(System.in, null);


    }
}