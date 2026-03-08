package client.mainpart;

import client.printer.ConsoleResponseSender;
import client.printer.ResponseSender;
import common.parser.CommandType;
import common.parser.Invoker;
import common.parser.ReturnCode;
import common.parser.Vehicle;
import server.commands.CommandsList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

//record ExecutionContext(Scanner sc, List<String> argc, boolean shouldBeLaud){}

public class Executor {
    private final Invoker invoker;


    public Executor(Invoker invoker) {
        this.invoker = invoker;
    }

    public ReturnCode Execute(InputStream stream,  Set<String> calledFiles) {


        CommandsList commandsList = new CommandsList();
        AllCommands allCommands = new AllCommands(commandsList);
        ResponseSender responseSender = new ConsoleResponseSender();


        Scanner scanner = new Scanner(stream);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();


            if (input.equals("exit")) {
                System.out.println("Выход из программы...");
                break;
            }

            if (input.isEmpty()) continue;

            String[] args = input.trim().split("\\s+");
            String commandName = args[0];


//            if (!result.isValid()) {
//                System.out.println("Ошибка: " + result.errorMessage());
//                continue;
//            }

//            String commandName = result.commandName();
//            List<String> args = result.args();

            if (commandName.equals("execute_script")) {
                if (args.length != 2) {
                    System.out.println("execute_script требует имя файла");
                    return ReturnCode.FAILED;
                }

                String fileName = args[1].trim();

                if (calledFiles.contains(fileName)) {
                    System.out.println("Обнаружена рекурсия! Файл уже вызван: " + fileName);
                    System.out.println("Цепочка вызовов: " + calledFiles);
                    return ReturnCode.FAILED;
                }

                Set<String> newCalledFiles = new HashSet<>(calledFiles);
                newCalledFiles.add(fileName);

                try {
                    ReturnCode code = Execute(new FileInputStream(args[1]),calledFiles);
                    if (code != ReturnCode.OK) {
                        return code;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("\n[Ожидание ввода...]");
                } catch (Exception e) {
                    System.out.println("Файл не существует: " + args[1]);
                }
                continue;
            }


            CommandType type = allCommands.getCommandType(commandName);


            if (type == CommandType.NOARGS || type == CommandType.WITHARGS) {
                invoker.executeCommand(commandName, List.of(args), null, !(stream instanceof FileInputStream));
                continue;
            }

            if (type == CommandType.WITHMODEL || type == CommandType.WITHARGSMODEL) {
                Scanner commandScanner = new Scanner(stream);
                DataValidator validator = new DataValidator(commandScanner, !(stream instanceof FileInputStream), responseSender);

                try {
                    Vehicle vehicle = validator.parseVehicle(commandScanner, !(stream instanceof FileInputStream));
                    invoker.executeCommand(commandName, List.of(args), vehicle, !(stream instanceof FileInputStream));
                } catch (Exception e) {
                    if (!(stream instanceof FileInputStream)) {
                        System.out.println("Ошибка при вводе данных: " + e.getMessage());
                    } else {
                        System.out.println("Ошибка в скрипте: " + e.getMessage());
                        return ReturnCode.FAILED;
                    }
                }
            }
//        }
        }
        return ReturnCode.OK;
    }
}




