import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {
    private static final String STARTCMD = "start=";
    private static final String FLOORSCMD = "floors=";
    private static final int TRAVELTIME = 10;

    public static void main(String[] args) {
        if (args.length != 2 || 
            !(args[0].toLowerCase().startsWith(STARTCMD) && args[1].toLowerCase().startsWith(FLOORSCMD))) {
            System.out.println("INVALID ARGUMENTS");
            System.out.println("Usage: java elevator start=1 floors=1,2,3");
            System.out.println("Order of arguments matters");
            System.exit(0);
        }
        // get start value
        String startStr = args[0].substring(STARTCMD.length());
        if (!validateFloor(startStr)) {
            System.out.println("INVALID start floor provided, please provide an integer greater than 0");
            System.exit(0);
        }
        int startFloor = Integer.parseInt(startStr);

        // get the list of floors
        String floorStr = args[1].substring(FLOORSCMD.length());
        if (!validateListOfFloors(floorStr)) {
            System.out.println("INVALID list of floors provided, please provide an integer greater than 0, in comma seperated list");
            System.exit(0);
        }

        List<Integer> floorsToVisit = Arrays.stream(getListOfFloors(floorStr)).boxed().collect(Collectors.toList());
        List<Integer> floorsVisited = Arrays.stream(new int[]{startFloor}).boxed().collect(Collectors.toList());

        int startIndex = floorsToVisit.size()-1;
        // get startIndex
        for (int i = 0; i < floorsToVisit.size(); i++) {
            if(floorsToVisit.get(i) == startFloor) {
                floorsToVisit.remove(i);
                i--;
            } else if (floorsToVisit.get(i) > startFloor) {
                startIndex = i;
                break;
            }
        }
        int shortestPath = calculateShortestPath(startIndex, startFloor, floorsToVisit, floorsVisited);
        StringBuilder sb = new StringBuilder();
        sb.append(shortestPath*TRAVELTIME);
        sb.append(" ");
        for (int i = 0; i < floorsVisited.size(); i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(floorsVisited.get(i));
        }
        System.out.println(sb.toString());
    }

    public static int calculateShortestPath(int currentIndex, int currentFloor, List<Integer> floorsToVisit, List<Integer> floorsVisited) {
        if(floorsToVisit.size() <= 0) {
            return 0;
        }

        // check visit current index
        List<Integer> leftFloorsToVisit = new ArrayList<>(floorsToVisit);
        List<Integer> leftFloorsVisited = new ArrayList<>(floorsVisited);
        int leftFloor = leftFloorsToVisit.remove(currentIndex);
        leftFloorsVisited.add(leftFloor);
        int leftPathDistance = Math.abs(currentFloor - leftFloor);
        leftPathDistance += calculateShortestPath(Math.max(currentIndex - 1, 0), leftFloor, leftFloorsToVisit, leftFloorsVisited);

        // check visit current index + 1
        if (currentIndex + 1 < floorsToVisit.size()) {
            List<Integer> rightFloorsToVisit = new ArrayList<>(floorsToVisit);
            List<Integer> rightFloorsVisited = new ArrayList<>(floorsVisited);
            int rightFloor = rightFloorsToVisit.remove(currentIndex + 1);
            rightFloorsVisited.add(rightFloor);
            int rightPathDistance = Math.abs(rightFloor - currentFloor);
            rightPathDistance += calculateShortestPath(currentIndex, rightFloor, rightFloorsToVisit, rightFloorsVisited);
            if (rightPathDistance < leftPathDistance) {
                leftFloorsVisited = rightFloorsVisited;
                leftPathDistance = rightPathDistance;
            }
        }
        floorsVisited.clear();
        floorsVisited.addAll(leftFloorsVisited);
        return leftPathDistance;
    }

    public static boolean validateFloor(String value) {
        String valueToTry = value.trim();
        try {
            return Integer.parseInt(valueToTry) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateListOfFloors(String value) {
        String[] listOfIntegers = value.split(",");
        if (listOfIntegers.length < 1) {
            return false;
        }
        for(String testInteger : listOfIntegers) {
            if(!validateFloor(testInteger)) {
                return false;
            }
        }
        return true;
    }

    public static int[] getListOfFloors(String value) {
        String[] listOfIntegers = value.split(",");
        int[] retArray = new int[listOfIntegers.length];
        for (int i = 0; i < listOfIntegers.length; i++) {
            retArray[i] = Integer.parseInt(listOfIntegers[i].trim());
        }
        Arrays.sort(retArray);
        return retArray;
    }
}
