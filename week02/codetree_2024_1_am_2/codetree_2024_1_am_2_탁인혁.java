import java.io.*;
import java.util.*;

public class Main {
    static class Land {
        int v;
        int u;
        int w;
        public Land(int v, int u, int w) {
            this.v = v;
            this.u = u;
            this.w = w;
        }
    }
    static class Product {
        int id;
        int revenue;
        int dest;
        int value;
        public Product(int id, int revenue, int dest, int value) {
            this.id = id;
            this.revenue = revenue;
            this.dest = dest;
            this.value = value;
        }
    }
    static List<Land>[] lands;
    static PriorityQueue<Product> products = new PriorityQueue<>(new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            if (o1.value > o2.value) return -1;
            else if (o1.value < o2.value) return 1;
            else return o1.id - o2.id;
        }
    });
    static int start = 0;
    static final int INF = Integer.MAX_VALUE;
    static Map<Integer, int[]> costMap = new HashMap<>();
    static boolean[] isDeleted = new boolean[30001];
    static boolean[] visited = new boolean[30001];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int Q = Integer.parseInt(br.readLine());


        for (int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine());

            int action = Integer.parseInt(st.nextToken());
            switch (action) {
                case 100:
                    int n = Integer.parseInt(st.nextToken());
                    lands = new List[n];
                    for (int i = 0; i < n; i++) {
                        lands[i] = new ArrayList<>();
                    }

                    int m = Integer.parseInt(st.nextToken());
                    for (int i = 0; i < m; i++) {
                        int v = Integer.parseInt(st.nextToken());
                        int u = Integer.parseInt(st.nextToken());
                        int w = Integer.parseInt(st.nextToken());
                        lands[v].add(new Land(v, u, w));
                        lands[u].add(new Land(u, v, w));
                    }

                    calculateCost();
                    break;
                case 200:
                    int id = Integer.parseInt(st.nextToken());
                    int revenue = Integer.parseInt(st.nextToken());
                    int dest = Integer.parseInt(st.nextToken());
                    int[] costs = costMap.get(start);
                    products.add(new Product(id, revenue, dest, revenue - costs[dest]));
                    visited[id] = true;
                    break;
                case 300:
                    int deleteId = Integer.parseInt(st.nextToken());
                    if (visited[deleteId]) {
                        isDeleted[deleteId] = true;
                    }
                    break;
                case 400:
                    Product product = products.peek();
                    while (product != null && isDeleted[product.id]) {
                        products.poll();
                        product = products.peek();
                    }
                    if (product == null || product.value < 0) {
                        System.out.println(-1);
                    } else {
                        System.out.println(product.id);
                        products.poll();
                    }
                    break;
                case 500:
                    int s = Integer.parseInt(st.nextToken());
                    if (start != s) {
                        start = s;
                        if (!costMap.containsKey(start)) {
                            calculateCost();
                        }
                        int[] newCosts = costMap.get(start);
                        Stack<Product> stack = new Stack<>();
                        while (!products.isEmpty()) {
                            Product p = products.poll();
                            p.value = p.revenue - newCosts[p.dest];
                            stack.push(p);
                        }
                        while (!stack.isEmpty()) {
                            products.add(stack.pop());
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    }

    public static void calculateCost() {
        PriorityQueue<Land> pq = new PriorityQueue<>(new Comparator<Land>() {
            @Override
            public int compare(Land o1, Land o2) {
                return Integer.compare(o1.w, o2.w);
            }
        });

        int[] costs = new int[lands.length];
        Arrays.fill(costs, INF);
        costs[start] = 0;
        boolean[] visited = new boolean[lands.length];
        visited[start] = true;
        for (Land curr : lands[start]) {
            if (costs[curr.u] > curr.w){
                costs[curr.u] = curr.w;
            }
            pq.add(curr);
        }

        while (!pq.isEmpty()) {
            Land curr = pq.poll();
            if (visited[curr.u]) continue;
            visited[curr.u] = true;
            for (Land next : lands[curr.u]) {
                if (costs[next.u] > costs[next.v] + next.w) {
                    costs[next.u] = costs[next.v] + next.w;
                    pq.add(new Land(next.v, next.u, costs[next.u]));
                }
            }
        }

        costMap.put(start, costs);
    }
}