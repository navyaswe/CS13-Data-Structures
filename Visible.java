import java.util.*;

class Visible {
    static boolean toCompare(List<Integer> box1, List<Integer> box2) {
        // sort the boxes to compare dimensions of each box.
        int size = box1.size();
        Collections.sort(box1);
        Collections.sort(box2);
        //Returns true if box1 is smaller than box2 otherwise false.
        for (int i = 0; i < size; i++) {
            if (box1.get(i) >= box2.get(i)) {
                return false;
            }
        }
        return true;
    }

    static class Graph {
        private List<List<Integer>> graph;
        private int box;

        Graph(List<List<Integer>> bpGraph) {
            graph = bpGraph;
            box = graph.size();
        }

        boolean match(int u, int[] matchR, boolean[] visible) {
            for (int v = 0; v < box; v++) {
                if (graph.get(u).get(v) == 1 && !visible[v]) {
                    visible[v] = true;
                    if (matchR[v] == -1 || match(matchR[v], matchR, visible)) {
                        matchR[v] = u;
                        return true;
                    }
                }
            }
            return false;
        }
        // Returns maximum no of matches
        int maxMatch() {
            int[] matchR = new int[box];
            Arrays.fill(matchR, -1);
            int result = 0;
            for (int i = 0; i < box; i++) {
                boolean[] seen = new boolean[box];
                if (match(i, matchR, seen)) {
                    result++;
                }
            }
            return result;
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Taking input as number of boxes from the user
        int no_of_boxes = sc.nextInt();
        List<List<Integer>> list_boxes = new ArrayList<>();
        // Making dimensions of 'n' boxes as list
        for (int i = 0; i < no_of_boxes; i++) {
            List<Integer> dimension = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                int d = sc.nextInt();
                dimension.add(d);
            }
            List<Integer> box = new ArrayList<>();
            box.add(i);
            box.addAll(dimension);
            list_boxes.add(box);
        }

        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < no_of_boxes; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < no_of_boxes; j++) {
                row.add(0);
            }
            matrix.add(row);
        }

        for (List<Integer> box1 : list_boxes) {
            for (List<Integer> box2 : list_boxes) {
                //Filling values in  a matrix
                int x1 = box1.get(0);
                int x2 = box2.get(0);
                List<Integer> dimension1 = box1.subList(1, box1.size());
                List<Integer> dimension2 = box2.subList(1, box2.size());
                matrix.get(x1).set(x2, toCompare(dimension1, dimension2) ? 1 : 0);
            }
        }

        Graph g = new Graph(matrix);
        int min_visible_boxes = no_of_boxes - g.maxMatch();
        System.out.println("The minimum number of visible boxes is " + min_visible_boxes);
    }
}
