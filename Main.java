import java.util.*;
public class Main {
    public static void main(String[] args) {

        Graph flightGraph = new Graph();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the list of airports (comma-separated), or 'exit' to end: ");
            String airportsInput = scanner.nextLine().trim();

            if (airportsInput.equalsIgnoreCase("exit")) {
                break;
            }

            String[] airports = airportsInput.split(",\\s*");

            System.out.print("Enter the flights (comma-separated): ");
            String[] flights = scanner.nextLine().split(",\\s*");

            for (String flight : flights) {
                String[] parts = flight.split("-");
                String source = parts[0];
                String destination = parts[1];

                System.out.print("Enter the distance for flight " + flight + " (in miles): ");
                int distance = scanner.nextInt();

                flightGraph.addFlight(source, destination, distance);
            }

            System.out.print("Enter source airport: ");
            String source = scanner.next();

            System.out.print("Enter destination airport: ");
            String destination = scanner.next();

            List<String> shortestPath = flightGraph.findShortestPath(source, destination);

            int totalDistance = flightGraph.getTotalDistance(shortestPath);

            if(totalDistance == 0)
                System.out.println("Can't travel between these 2 end points");
            else {
                System.out.println("Shortest path from " + source + " to " + destination + ": " + String.join(" - ", shortestPath));
                System.out.println("Total distance: " + totalDistance + " miles");
            }

            // Consume the newline character
            scanner.nextLine();
        }

        System.out.println("Exiting the program.");
        scanner.close();
    }
}
