package server.collection;


import common.ResponseSender;
import common.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class VehicleAdder {
    private final VehicleCollection collection;
    private final ResponseSender responseSender;

    public VehicleAdder(VehicleCollection collection, ResponseSender responseSender) {
        this.responseSender = responseSender;
        this.collection = collection;

    }


    public void updateElementByID(long id, Vehicle vehicle, Boolean isLaud) {
        if (collection.getVehicleByID(id) != null) {
            vehicle.setId(id);
            collection.replaceVehicle(id, vehicle);
            if (isLaud) responseSender.send("Элемент обновлен");
        }
    }


    public void rmByID(long id, Boolean isLaud) {
        //noinspection rawtypes
        List arr = collection.getAllID();
        for (Object i : arr) {
            if ((long) i == id) {
                collection.rmEl(collection.getVehicleByID(id));
            }
        }
        if (isLaud) responseSender.send("Элемент удален");

    }


    public void addElement(Vehicle vehicle, Boolean isLaud) {
        collection.add(vehicle);
        if (isLaud) responseSender.send("Элемент добавлен");
    }


    public void addIfMax(Vehicle veh, Boolean isLaud) {
        if (collection.getVehicles().stream().allMatch(v -> v.getDistanceTravelled() < veh.getDistanceTravelled())) {
            collection.add(veh);
            if (isLaud) responseSender.send("Элемент добавлен");
        } else {
            if (isLaud)
                responseSender.send("Не добавлено: в коллекции есть элементы с distanceTravelled >= " + veh.getDistanceTravelled());
        }

    }

    public void groupByParam(List<String> args, ResponseSender responseSender) {
        ValidateParams validator = new ValidateParams(args);
        GroupingField field = validator.getGroupingField();

        Map<Comparable<?>, Long> grouped = collection.getVehicles().stream()
                .collect(Collectors.groupingBy(
                        field.extractor(),
                        Collectors.counting()
                ));
        if (grouped.isEmpty()) {
            responseSender.send("Ничего не найдено");
            return;
        }

        // 3. Формирование вывода
        responseSender.send("Группировка по полю: " + field.fieldName());
        responseSender.send("-------------------------------");

        grouped.forEach((key, count) ->
                responseSender.send(String.format("%s: %d объект(ов)",
                        key != null ? key.toString() : "[не задано]",
                        count))
        );

        long total = grouped.values().stream().mapToLong(Long::longValue).sum();
        responseSender.send("-------------------------------");
        responseSender.send("Всего сгруппировано: " + total);
    }


}
