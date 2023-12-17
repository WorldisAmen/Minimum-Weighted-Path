import java.util.*;

public class Graph {
    private final Map<String, Integer> airportIndexMap;
    private final List<String> airports;
    private int[][] adjacencyMatrix;

    public Graph() {
        airportIndexMap = new HashMap<>();
        airports = new ArrayList<>();
        adjacencyMatrix = new int[0][0];
    }

    public void addFlight(String source, String destination, int distance) {
        if (!airportIndexMap.containsKey(source)) {
            addAirport(source);
        }
        if (!airportIndexMap.containsKey(destination)) {
            addAirport(destination);
        }
        int sourceIndex = airportIndexMap.get(source);
        int destinationIndex = airportIndexMap.get(destination);
        adjacencyMatrix[sourceIndex][destinationIndex] = distance;
        adjacencyMatrix[destinationIndex][sourceIndex] = distance;
    }

    private void addAirport(String airport) {
        int newIndex = airports.size();
        airportIndexMap.put(airport, newIndex);
        airports.add(airport);
        int[][] newMatrix = new int[newIndex + 1][newIndex + 1];
        //for (int i = 0; i < newIndex; i++) {
        //    for (int j = 0; j < newIndex; j++) {
        //        newMatrix[i][j] = adjacencyMatrix[i][j];
        //    }
        //}
        // more professional way than commented one
        for (int i = 0; i < newIndex; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, newIndex);
        }
        adjacencyMatrix = newMatrix;
    }

    public List<String> findShortestPath(String source, String destination) {
        int sourceIndex = airportIndexMap.get(source);
        int destinationIndex = airportIndexMap.get(destination);
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        distances.put(sourceIndex, 0);
        pq.offer(sourceIndex);

        while (!pq.isEmpty()) {
            int current = pq.poll();
            for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
                if (adjacencyMatrix[current][neighbor] > 0) {
                    int newDistance = distances.get(current) + adjacencyMatrix[current][neighbor];

                    if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                        pq.offer(neighbor);
                    }
                }
            }
        }

        List<String> path = new ArrayList<>();
        int current = destinationIndex;

        while (previous.containsKey(current)) {
            path.add(airports.get(current));
            current = previous.get(current);
        }
        path.add(airports.get(sourceIndex));

        Collections.reverse(path);
        return path;
    }

    public int getTotalDistance(List<String> path) {
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentAirportIndex = airportIndexMap.get(path.get(i));
            int nextAirportIndex = airportIndexMap.get(path.get(i + 1));
            totalDistance += adjacencyMatrix[currentAirportIndex][nextAirportIndex];
        }
        return totalDistance;
    }
}
