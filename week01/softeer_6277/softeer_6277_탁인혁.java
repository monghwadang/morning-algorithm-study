import java.io.*;
import java.util.*;

public class softeer_6277_탁인혁 {
    static class Dot {
        int x;
        int y;
        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    static int N;
    static int K;
    static List<Dot>[] dots;

    static int result = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dots = new List[K+1];
        for (int i = 1; i <= K; i++) {
            dots[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            dots[k].add(new Dot(x, y));
        }

        if (N == 1 || K == 1) {
            result = 0;
        } else {
            selectDot(1000, 1000, -1000, -1000, 1);
        }

        System.out.println(result);
    }

    public static void selectDot(int x1, int y1, int x2, int y2, int k) {
        if (k > K) {
            result = Math.min(result, (x2 - x1) * (y2 - y1));
            return;
        }

        for (Dot dot : dots[k]) {
            if (dot.x >= x1 && dot.x <= x2 && dot.y >= y1 && dot.y <= y2) {
                selectDot(x1, y1, x2, y2, k + 1);
                break;
            }
            int nx1 = Math.min(x1, dot.x);
            int ny1 = Math.min(y1, dot.y);
            int nx2 = Math.max(x2, dot.x);
            int ny2 = Math.max(y2, dot.y);
            selectDot(nx1, ny1, nx2, ny2, k + 1);
        }
    }
}