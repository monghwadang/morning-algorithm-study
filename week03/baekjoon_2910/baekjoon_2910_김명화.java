package trial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class frequency implements Comparable<frequency> {

	int n;
	int idx;
	int cnt;

	public frequency(int n, int idx, int cnt) {
		super();
		this.n = n;
		this.idx = idx;
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "frequency [n=" + n + ", idx=" + idx + ", cnt=" + cnt + "]";
	}

	@Override
	public int compareTo(frequency o) {

		if (this.cnt == o.cnt) { // 등장 횟수가 같다면
			return this.idx - o.idx; // 먼저 등장한 것이 앞에 오도록
		}
		return o.cnt - this.cnt;
	}

}

public class baekjoon_2910_김명화 {

	static int N, C;
	static ArrayList<frequency> list;
	static Map<Integer, frequency> map;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		list = new ArrayList<>();
		map = new HashMap<>();

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {

			msg(Integer.parseInt(st.nextToken()), i); // map 채우기

		}

		// 정렬을 위해 list에 넣기
		list.addAll(map.values());
		Collections.sort(list);

		decode();

	}

	private static void msg(int n, int idx) {

		frequency curr = map.get(n);
		if (curr == null) { // n이 새로 등장
			map.put(n, new frequency(n, idx, 1));
		} else { // 이미 등장한 적 있다면
			curr.cnt++;
		}

	}

	private static void decode() {

		for (frequency f : list) { // 각 n에 대해서
			for (int i = 0; i < f.cnt; i++) { // 등장 횟수만큼 출력
				sb.append(f.n + " ");
			}
		}

		System.out.println(sb);

	}
}
