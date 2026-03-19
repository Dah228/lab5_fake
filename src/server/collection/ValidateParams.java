package server.collection;


import common.Vehicle;


import java.util.List;

public class ValidateParams {

    private final List<String> args;

    public ValidateParams(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Нет аргументов");
        }
        this.args = args;
    }

    public GroupingField getGroupingField() {
        String first = args.get(1).trim().toUpperCase();

        // 1. VehicleType
        if (first.equals("TYPE")) {
            return new GroupingField("type", Vehicle::getType);
        }

        // 2. FuelType
        if (first.equals("FUELTYPE")) {
            return new GroupingField("fueltype", Vehicle::getFuelType);
        }

        // 3. Координаты
        if (first.equals("COORDINATES")) {
            return new GroupingField("coordinates", Vehicle::getCoordinates);
        }

        throw new IllegalArgumentException("Не распознано: " + first);
    }
}
//
//
//    private boolean isVehicleType(String value) {
//        try {
//            VehicleType.valueOf(value.toUpperCase());
//            return true;
//        } catch (IllegalArgumentException | NullPointerException e) {
//            return false;
//        }
//    }
//
//    private boolean isFuelType(String value) {
//        try {
//            FuelType.valueOf(value.toUpperCase());
//            return true;
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    private boolean isCoordinates(List<String> args) {
//        if (args.size() < 3) return false;
//        try {
//            Integer.parseInt(args.get(1).trim());
//            Float.parseFloat(args.get(2).trim());
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//}