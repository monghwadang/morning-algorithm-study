import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class land implements Comparable<land> {

	int u;
	int w;

	public land(int u, int w) {
		super();
		this.u = u;
		this.w = w;
	}

	@Override
	public String toString() {
		return "land [u=" + u + ", w=" + w + "]";
	}

	@Override
	public int compareTo(land o) {
		return this.w - o.w;
	}

}

class product implements Comparable<product> {

	int id;
	int rev;
	int dest;
	int profit;

	public product(int id, int rev, int dest, int profit) {
		super();
		this.id = id;
		this.rev = rev;
		this.dest = dest;
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "product [id=" + id + ", rev=" + rev + ", dest=" + dest + ", profit=" + profit + "]";
	}

	@Override
	public int compareTo(product o) {

		if (o.profit == this.profit) {
			return this.id - o.id;
		}

		return o.profit - this.profit;
	}

}

public class codetree_2024_1_am_2_김명화 {

	static int Q, op, n, m, s;
	static ArrayList<land>[] tour;
	static int[] dist;
	static boolean[] visited;
	static PriorityQueue<product> prod;
	static Map<Integer, product> productMap;
	static PriorityQueue<land> pq;

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Q = Integer.parseInt(br.readLine());

		for (int i = 0; i < Q; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			op = Integer.parseInt(st.nextToken());

			switch (op) {

			// 랜드 건설
			case 100:
				n = Integer.parseInt(st.nextToken());
				m = Integer.parseInt(st.nextToken());

				tour = new ArrayList[n];
				dist = new int[n];
				s = 0; // 출발지 0 디폴트

				for (int j = 0; j < n; j++) {
					tour[j] = new ArrayList<>();
				}

				for (int j = 0; j < m; j++) {
					int v = Integer.parseInt(st.nextToken());
					int u = Integer.parseInt(st.nextToken());
					int w = Integer.parseInt(st.nextToken());
					tour[v].add(new land(u, w));
					tour[u].add(new land(v, w));
				}

				prod = new PriorityQueue<>(); // 상품을 담기 위한 pq
				productMap = new HashMap<>();
				dijkstra(s);

				break;
			case 200:
				create(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
						Integer.parseInt(st.nextToken()));
				break;
			case 300:
				cancle(Integer.parseInt(st.nextToken()));
				break;
			case 400:
				best();
				break;
			case 500:
				change(Integer.parseInt(st.nextToken()));
				break;

			}

		}

	}

	// 다익스트라
	private static void dijkstra(int s) {
		visited = new boolean[n];

		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[s] = 0; // 출발지의 dist값은 0으로 설정

		pq = new PriorityQueue<>();
		pq.add(new land(s, dist[s])); // 출발점 넣기

		while (!pq.isEmpty()) {

			land curr = pq.poll();

			if (visited[curr.u])
				continue;

			visited[curr.u] = true; // 방문체크

			for (land l : tour[curr.u]) {

				if (!visited[l.u] && dist[l.u] > dist[curr.u] + l.w) {
					dist[l.u] = dist[curr.u] + l.w;
					pq.add(new land(l.u, dist[l.u]));
				}

			}

		}

	}

	// 200 여행 상품 생성
	private static void create(int id, int rev, int dest) {

		int profit = dist[dest] == Integer.MAX_VALUE ? -1 : rev - dist[dest];
		product p = new product(id, rev, dest, profit);
		prod.add(p);
		productMap.put(id, p);

	}

	// 300 여행 상품 취소
	private static void cancle(int id) {

		product p = productMap.remove(id);
		if (p != null) {
			prod.remove(p);
		}

	}

	// 400 최적의 여행 상품 판매
	private static void best() {

		if (!prod.isEmpty()) { // 판매 가능한 상품이 있을 때
			product bestProduct = prod.poll();
			if (bestProduct.profit >= 0) {
				System.out.println(bestProduct.id);
				productMap.remove(bestProduct.id);
			} else {
				prod.add(bestProduct);
				System.out.println(-1);
			}
		} else { // 판매 가능한 상품이 없을 때
			System.out.println(-1);
		}

	}

	// 500 출발지 변경
	private static void change(int idx) {
		s = idx;
		dijkstra(s);

		// prod에 있는 상품들로 상품 재생성 필요
		PriorityQueue<product> temp = new PriorityQueue<>(); // prod에서 뺀 것을 다시 담기 위한 pq
		int n = prod.size();
		for (int i = 0; i < n; i++) {
			temp.add(prod.poll());
		} // temp로 다 옮기기

		prod.clear();
		while (!temp.isEmpty()) {

			product p = temp.poll(); // 빠져나왔고
			create(p.id, p.rev, p.dest); // 상품 생성 조건에 성립하면 다시 들어간다
		}

	}

}
