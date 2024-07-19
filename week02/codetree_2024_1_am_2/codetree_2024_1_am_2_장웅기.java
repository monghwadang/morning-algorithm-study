package week02.codetree_2024_1_am_2;

import java.util.*;

public class codetree_2024_1_am_2_장웅기 {

    static int Q;
    static ItemManager im; // 상품 관리 클래스
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Q = sc.nextInt();

        while (Q-- > 0) {
            int cmd = sc.nextInt();
            switch (cmd) {
                case 100: {
                    int n = sc.nextInt();
                    int m = sc.nextInt();
                    int[][] infos  = new int[m][3];
                    for (int i = 0; i < m; i++) {
                        infos[i][0] = sc.nextInt();
                        infos[i][1] = sc.nextInt();
                        infos[i][2] = sc.nextInt();
                    }
                    im = new ItemManager(n, m, infos);
                    break;
                }
                case 200: {
                    int id = sc.nextInt();
                    int revenue = sc.nextInt();
                    int dest = sc.nextInt();
                    im.makeItem(id, revenue, dest);
                    break;
                }
                case 300: {
                    int id = sc.nextInt();
                    im.cancelItem(id);
                    break;
                }
                case 400: {
                    int id = im.sellItem();
                    sb.append(id).append("\n");
                    break;
                }
                case 500: {
                    int s = sc.nextInt();
                    im.changeOrigin(s);
                    break;
                }
            }
        }
        System.out.print(sb);
    }

    // 상품 관리 클래스
    static class ItemManager {
        private int origin = 0; // 출발지
        private List<Info>[] land; // 그래프 (인접 리스트)
        private Map<Integer, Item> itemMap; // 상품 맵
        private PriorityQueue<Item> pq = new PriorityQueue<>((i1, i2) -> compare(i1, i2)); // 상품 정렬 구조
        private int[] dist; // 출발지부터 목적지까지 최소 거리

        ItemManager(int n, int m, int[][] infos) {
            land = new List[n];
            itemMap = new HashMap<>();
            for (int i = 0; i < n; i++) land[i] = new ArrayList<>();

            // 그래프 만들기
            for (int i = 0; i < m; i++) {
                int st = infos[i][0];
                int en = infos[i][1];
                int c = infos[i][2];
                land[st].add(new Info(en, c));
                land[en].add(new Info(st, c));
            }
            dist = dijkstra(origin); // 출발지 기준으로 거리 계산
        }

        // 상품 만들기
        private void makeItem(int id, int revenue, int dest) {
            Item item = new Item(id, revenue, dest);
            itemMap.put(id, item);

            int benefit = revenue - dist[dest]; // 이득 계산
            if (0 <= benefit) pq.add(item); // 이득이 음수가 아니면 정렬 구조에 넣음
        }

        // 상품 취소
        private void cancelItem(int id) {
            itemMap.remove(id); // 상품 맵에서 제거
            pq.removeIf(i -> i.id == id); // 상품 정렬 구조에서 제거
        }

        // 상품 판매
        private int sellItem() {
            Item item = pq.poll(); // 최적의 상품 꺼냄
            // 상품 존재 시
            if (item != null) {
                itemMap.remove(item.id); // 상품 맵에서 제거
                return item.id;
            }
            return -1; // 최적의 상품 존재하지 않을 시 -1 반환
        }

        // 출발지 변경
        private void changeOrigin(int s) {
            origin = s;
            dist = dijkstra(origin); // 다른 출발지로부터 목적지까지 거리 다시 계산

            pq = new PriorityQueue<>((i1, i2) -> compare(i1, i2)); // 정렬 기준을 다시 정함.

            // 모든 상품 꺼냄
            for (Item item : itemMap.values()) {
                int benefit = item.revenue - dist[item.dest]; // 상품 이득 다시 계산
                if (0 <= benefit) pq.add(item); // 다시 넣음
            }
        }

        // 정렬 기준 메서드
        private int compare(Item item1, Item item2) {
            int benefit1 = item1.revenue - dist[item1.dest];
            int benefit2 = item2.revenue - dist[item2.dest];
            if (benefit1 != benefit2) return benefit2 - benefit1; // 이득이 큰 것 먼저
            return item1.id - item2.id; // 이득이 같으면 id가 작은 것 먼저
        }

        // 데이크스트라
        private int[] dijkstra(int origin) {
            int[] dist = new int[land.length];
            Arrays.fill(dist, 987654321);
            dist[origin] = 0;

            PriorityQueue<Info> pq = new PriorityQueue<>((i1, i2) -> i1.cost - i2.cost);
            pq.add(new Info(origin, 0));

            while (!pq.isEmpty()) {
                Info info = pq.remove();
                int cur = info.dest;
                int cost = info.cost;

                if (cost > dist[cur]) continue;

                for (Info nxt : land[cur]) {
                    if (dist[nxt.dest] > cost + nxt.cost) {
                        dist[nxt.dest] = cost + nxt.cost;
                        pq.add(new Info(nxt.dest, cost + nxt.cost));
                    }
                }
            }
            return dist;
        }
    }

    static class Info {
        int dest, cost;
        public Info(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }

    static class Item {
        int id, revenue, dest;
        public Item(int id, int revenue, int dest) {
            this.id = id;
            this.revenue = revenue;
            this.dest = dest;
        }
    }
}