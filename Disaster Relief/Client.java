import java.util.*;

public class Client {
    private static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        // List<Location> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Location> scenario = createSimpleScenario();
        System.out.println(scenario);
        
        double budget = 2000;
        Set<Allocation> allocations = generateOptions(budget, scenario);
        printResult(allocations, budget);
    }

    public static Set<Allocation> generateOptions(double budget, List<Location> sites) {
        // creates a set of Allocations (each allocation represents a different option)
        Set<Allocation> allOptions = new HashSet<Allocation>();
        // uses helper method and returns the set of all options after program is done recursing
        generateOptions(budget, sites, new Allocation(), allOptions);
        return allOptions;
    }

    // TODO: add any of your own helper methods here
    private static void generateOptions(double budget, List<Location> sites, Allocation curr, Set<Allocation> allOptions) {
        // creates a copy of sites to represent the remaining list of locations
        List<Location> tempSites = new ArrayList<Location>(sites);
        // base case: when no more locations left
        if(tempSites.isEmpty()) {
            allOptions.add(curr);
        } else { // recursive case
            // accesses the first location in the list
            Location temp = tempSites.remove(0);
            if(temp.getCost() <= budget) {
                // finds options with the budget now changed and the allocation has the 'temp' location
                generateOptions(budget - temp.getCost(), tempSites, curr.withLoc(temp), allOptions);
            }
            // will now look at the next location
            generateOptions(budget, tempSites, curr, allOptions);
        }
    }


    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!**

    public static void printResult(Set<Allocation> allocs, double budget) {
        for (Allocation alloc : allocs) {
            System.out.println("Result: ");
            System.out.println("  " + alloc);
            System.out.println("  People helped: " + alloc.totalPeople());
            System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
            System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));        
        }
    }

    public static List<Location> createRandomScenario(int numLocs, int minPop, int maxPop, double minCostPer, double maxCostPer) {
        List<Location> result = new ArrayList<>();

        for (int i = 0; i < numLocs; i++) {
            int pop = rand.nextInt(minPop, maxPop + 1);
            double cost = rand.nextDouble(minCostPer, maxCostPer) * pop;
            result.add(new Location("Location #" + i, pop, round2(cost)));
        }

        return result;
    }

    public static List<Location> createSimpleScenario() {
        List<Location> result = new ArrayList<>();

        result.add(new Location("Location #1", 50, 500));
        result.add(new Location("Location #2", 100, 700));
        result.add(new Location("Location #3", 60, 1000));
        result.add(new Location("Location #4", 20, 1000));
        result.add(new Location("Location #5", 200, 900));

        return result;
    }    

    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
