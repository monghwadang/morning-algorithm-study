package baekjoon_2512;

public class baekjoon_2512_장웅기 {

    static int N, M; // 예산요청처 N, 예산총액 M
    static int[] A; // 예산요청액[] A

    public static void main(String[] args) throws Exception {
        N = read();
        A = new int[N];

        for (int i = 0; i < N; i++) A[i] = read();

        M = read();

        int max = max(A); // 가장 큰 예상요청액
        // 총예산이 각 예산요청액의 합보다 크거나 같으면, 각 요청중 가장 큰 것
        // 그렇지 않으면, 이분 탐색
        System.out.println(M >= getBudget(max) ? max : binarySearch());
    }

    // 예상액 k보다 총액 M이 크면 배정액 m을 줄임.
    // 예상액 k보다 총액 M이 작으면 배정액 m을 늘림.
    static int binarySearch() {
        int l = 0, h = M, m;
        while (l < h) {
            m = (l + h) / 2;
            int k = getBudget(m);
            if (M > k) l = m + 1;
            else if (M < k) h = m;
            else return m;
        }
        return l - 1;
    }

    // 예상액 = sum(min(배정액 m, 예산요청액 i))
    static int getBudget(int m) {
        int sum = 0;
        for (int i : A) {
            sum += Math.min(m, i);
        }
        return sum;
    }

    // 예산 요청액 중 가장 큰 것
    static int max(int[] a) {
        int max = a[0];
        for (int i : a) max = Math.max(max, i);
        return max;
    }

    static int read() throws Exception {
        int c, n = System.in.read() & 15;
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return n;
    }
}
