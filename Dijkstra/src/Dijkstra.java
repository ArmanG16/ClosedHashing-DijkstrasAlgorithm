import java.util.*;

public class Dijkstra {
    static final int MAX = Integer.MAX_VALUE;

    private static class Node implements Comparable<Node> {
        int vert;
        int dist;

        public Node(int vert, int dist) {
            this.vert = vert;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    public static void main(String[] args) {
        int[][] adjMatrix = {
                {0, 53, 10, 12, 0, 0, 0, 0, 0, 0},
                {53, 0, 33, 0, 2, 0, 101, 0, 0, 0},
                {10, 33, 0, 9, 30, 18, 0, 0, 0, 0},
                {12, 0, 9, 0, 0, 17, 0, 0, 0, 6},
                {0, 2, 30, 0, 0, 14, 123, 122, 0, 0},
                {0, 0, 18, 17, 14, 0, 0, 137, 7, 0},
                {0, 101, 0, 0, 123, 0, 0, 8, 0, 71},
                {0, 0, 0, 0, 122, 137, 8, 0, 145, 66},
                {0, 0, 0, 0, 0, 7, 0, 145, 0, 212},
                {0, 0, 0, 6, 0, 0, 71, 66, 212, 0}
        };

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the start node (0-9): ");
        int startNode = input.nextInt();

        System.out.print("Enter the end node (0-9): ");
        int endNode = input.nextInt();

        findShortestPath(adjMatrix, startNode, endNode);
    }

    private static void findShortestPath(int[][] graph, int start, int end) {
        int size = graph.length;
        int[] costs = new int[size];
        int[] prevNode = new int[size];
        boolean[] marked = new boolean[size];

        Arrays.fill(costs, MAX);
        Arrays.fill(prevNode, -1);

        costs[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (marked[curr.vert]) continue;

            marked[curr.vert] = true;

            for (int i = 0; i < size; i++) {
                if (graph[curr.vert][i] != 0 && !marked[i]) {
                    int updatedCost = costs[curr.vert] + graph[curr.vert][i];
                    if (updatedCost < costs[i]) {
                        costs[i] = updatedCost;
                        prevNode[i] = curr.vert;
                        pq.add(new Node(i, updatedCost));
                    }
                }
            }
        }

        System.out.println("Shortest distance from " + start + " to " + end + " is " + costs[end]);
        System.out.println("Shortest path: " + generatePath(prevNode, end));
    }

    private static List<Integer> generatePath(int[] prevNode, int target) {
        List<Integer> path = new ArrayList<>();
        int curr = target;

        while (curr != -1) {
            path.add(curr);
            curr = prevNode[curr];
        }

        Collections.reverse(path);
        return path;
    }
}