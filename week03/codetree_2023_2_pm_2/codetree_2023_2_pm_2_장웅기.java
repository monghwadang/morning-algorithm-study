package codetree_2023_2_pm_2;

import java.util.*;

public class codetree_2023_2_pm_2_장웅기 {

    static int L, Q;
    static int ct;
    static int cq, sq;

    static Map<String, List<Info>> sushiMap; // key: 초밥 이름, value: 초밥 처음 놓인 시간, 처음 위치
    static Map<String, Info> customerMap; // key: 손님 이름, value: 손님 입장 시간, 손님 위치, 먹을 초밥 수

    static List<Query> queries = new ArrayList<>(); // 쿼리 저장소

    static class Info {
        int t, ix, n;
        Info(int t, int ix, int n) { this.t = t; this.ix = ix; this.n = n; }
    }

    static class Query {
        int t, id;
        Query(int t, int id) { this.t = t; this.id = id; }
        // 1: 초밥 in
        // 2: 초밥 out
        // 3: 손님 in
        // 4: 손님 out
        // 5: 사진 촬영
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        L = sc.nextInt();
        Q = sc.nextInt();

        sushiMap = new HashMap<>();
        customerMap = new HashMap<>();

        while (Q-- > 0) {
            int cmd = sc.nextInt();
            int ct = sc.nextInt();
            switch(cmd) {
                case 100: {
                    int x = sc.nextInt();
                    String name = sc.next();
                    place(name, ct, x);
                    break;
                }
                case 200: {
                    int x = sc.nextInt();
                    String name = sc.next();
                    int n = sc.nextInt();
                    enter(name, ct, x, n);
                    break;
                }
                case 300: {
                    capture(ct, 300);
                    break;
                }
            }
        }

        // 초밥 out, 사람 out 구하기
        for (Map.Entry<String, Info> entry : customerMap.entrySet()) {
            String name = entry.getKey();

            List<Info> sushiList = sushiMap.get(name);
            Info customer = entry.getValue();

            int ot = 0; // 손님이 나갈 시간

            for (Info sushi : sushiList) {
                if (sushi.t < customer.t) { // 초밥이 먼저 놓인 경우
                    int sx = (sushi.ix + (customer.t - sushi.t)) % L; // 손님 입장 직후 초밥의 위치
                    int et = (customer.ix - sx + L) % L + customer.t; // 손님이 온 후, 손님이 먹을 시간
                    queries.add(new Query(et, 2)); // 초밥 out

                    ot = Math.max(ot, et); // 손님이 나갈 시간을 가장 나중에 먹은 시간으로 갱신
                }
                else { // 손님이 먼저 온 경우
                    int et = (customer.ix - sushi.ix + L) % L + sushi.t; // 초밥이 놓인 후, 손님이 먹을 시간
                    queries.add(new Query(et, 2)); // 초밥 out

                    ot = Math.max(ot, et); // 손님이 나갈 시간을 가장 나중에 먹은 시간으로 갱신
                }
            }

            queries.add(new Query(ot, 4)); // 손님 out
        }

        // 쿼리를 시간, id 순으로 정렬
        queries.sort((q1, q2) -> {
            if (q1.t != q2.t) return q1.t - q2.t;
            return q1.id - q2.id;
        });

        // 쿼리에 따라 초밥 수, 손님 수 계산
        for (Query q : queries) {
            switch (q.id) {
                case 1: sq++; break;
                case 2: sq--; break;
                case 3: cq++; break;
                case 4: cq--; break;
                case 5: System.out.println(cq + " " + sq); break;
            }
        }
    }

    // 초밥 in 쿼리
    static void place(String name, int ct, int x) {
        Info sushi = new Info(ct, x, 0);
        // name 키가 없을 때 ArrayList 생성 후 반환, 키가 있으면 value 반환
        // 그 이후 add
        sushiMap.computeIfAbsent(name, k -> new ArrayList<>()).add(sushi);

        queries.add(new Query(ct, 1));
    }

    // 손님 in 쿼리
    static void enter(String name, int ct, int x, int n) {
        Info customer = new Info(ct, x, n);
        customerMap.put(name, customer);

        queries.add(new Query(ct, 3));
    }

    // 사진 촬영 쿼리
    static void capture(int ct, int cmd) {
        queries.add(new Query(ct, 5));
    }
}
