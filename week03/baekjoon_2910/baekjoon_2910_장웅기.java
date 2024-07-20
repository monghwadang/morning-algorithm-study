package baekjoon_2910;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class baekjoon_2910_장웅기 {

    static int N, C;

    // map<숫자, 들어간 숫자의 개수>
    static Map<Integer, Integer> map = new LinkedHashMap<>(); // 순서를 보장하는 연결 맵

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String[] tokens = br.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        C = Integer.parseInt(tokens[1]);

        tokens = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            Integer key = Integer.parseInt(tokens[i]);
            // (key, value)를 넣을 때 key가 존재하면 overwrite;
            // 이걸 이용해, key가 존재해면 원래의 value를 더해주면서 갱신하는 작업
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        // LinkedHashMap을 사용했기 때문에 List에 순서대로 keySet이 들어감
        // 그래서 value가 같은 값일 때의 정렬 로직을 신결 필요가 없음
        List<Integer> list = new ArrayList<>(map.keySet());
        list.sort((i1, i2) -> map.get(i2) - map.get(i1)); // 정렬하면 먼저 온 순서와 value 크기의 내림차순으로 정렬

        for (Integer i : list) {
            int v = map.get(i); // v: 숫자의 개수
            while (v-- > 0) sb.append(i).append(' '); // 숫자의 개수만큼 출력
        }
        System.out.println(sb);
    }
}
