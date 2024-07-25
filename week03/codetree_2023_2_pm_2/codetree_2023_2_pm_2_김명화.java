import java.util.*;
import java.io.*;

class Query {
    int id, t, x;
    String name;

    Query(int id, int t, int x, String name) {
        this.id = id;
        this.t = t;
        this.x = x;
        this.name = name;
    }
}

public class codetree_2023_2_pm_2_김명화 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
            // 쿼리와 데이터를 저장할 자료구조들을 초기화
            List<Query> queries = new ArrayList<>();  // 모든 쿼리 저장
            Map<String, List<Query>> p_queries = new HashMap<>();  // 사람별 초밥 쿼리
            Set<String> names = new HashSet<>();  // 사람 이름 집합
            Map<String, Integer> entry_time = new HashMap<>();  // 사람별 입장 시간
            Map<String, Integer> position = new HashMap<>();  // 사람별 위치
            Map<String, Integer> exit_time = new HashMap<>();  // 사람별 퇴장 시간

            String[] input = br.readLine().split(" ");
            int L = Integer.parseInt(input[0]);
            int Q = Integer.parseInt(input[1]);

            for (int i = 0; i < Q; i++) {
                input = br.readLine().split(" ");
                int id = Integer.parseInt(input[0]);
                int t = Integer.parseInt(input[1]);
                int x = -1;
                String name = "";

                if (id == 100) {
                    // 초밥이 도착하는 쿼리
                    x = Integer.parseInt(input[2]);
                    name = input[3];
                    Query query = new Query(id, t, x, name);
                    queries.add(query);
                    p_queries.computeIfAbsent(name, k -> new ArrayList<>()).add(query);
                } else if (id == 200) {
                    // 손님이 도착하는 쿼리
                    x = Integer.parseInt(input[2]);
                    name = input[3];
                    queries.add(new Query(id, t, x, name));
                    names.add(name);
                    position.put(name, x);
                    entry_time.put(name, t);
                    exit_time.put(name, -1);
                } else if (id == 300) {
                    // 현재 상태를 출력하는 쿼리
                    queries.add(new Query(id, t, x, name));
                }
            }

            // 각 손님의 퇴장 시간을 계산합니다.
            for (String name : names) {
                if (!p_queries.containsKey(name)) continue;
                for (Query pq : p_queries.get(name)) {
                    int leave_time;
                    if (entry_time.get(name) < pq.t) {
                        // 손님이 초밥보다 먼저 온 경우
                        int add_time = (position.get(name) - pq.x + L) % L;
                        leave_time = pq.t + add_time;
                    } else {
                        // 초밥이 손님보다 먼저 온 경우
                        int sushi_pos = (entry_time.get(name) - pq.t + pq.x) % L;
                        int add_time = (position.get(name) - sushi_pos + L) % L;
                        leave_time = entry_time.get(name) + add_time;
                    }
                    queries.add(new Query(150, leave_time, pq.x, name));
                    exit_time.put(name, Math.max(exit_time.get(name), leave_time));
                }
                queries.add(new Query(250, exit_time.get(name), position.get(name), name));
            }

            // 쿼리를 시간순, ID순으로 정렬합니다.
            queries.sort(Comparator.comparingInt((Query q) -> q.t).thenComparingInt(q -> q.id));

            int sushi_cnt = 0, people_cnt = 0;
            for (Query q : queries) {
                switch (q.id) {
                    case 100: sushi_cnt++; break;  // 초밥 도착
                    case 150: sushi_cnt--; break;  // 초밥 소비
                    case 200: people_cnt++; break;  // 손님 도착
                    case 250: people_cnt--; break;  // 손님 퇴장
                    case 300: System.out.println(people_cnt + " " + sushi_cnt); break;  // 현재 상태 출력
                }
            }
        
        br.close();
    }
}