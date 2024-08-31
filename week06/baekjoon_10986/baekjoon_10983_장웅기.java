package baekjoon_10986;

public class baekjoon_10983_장웅기 {
    static int N, M;
    static long[] Num, Sum, Reminder; // 수, 합, 나머지 개수

    public static void main(String[] args) throws Exception {
        N = read(); M = read();
        Num = new long[N + 1]; Sum = new long[N + 1]; Reminder = new long[M];

        for (int i = 1; i <= N; i++) Num[i] = read();
        for (int i = 1; i <= N; i++) Sum[i] = Sum[i - 1] + Num[i];
        for (int i = 1; i <= N; i++) Reminder[(int) (Sum[i] % M)]++; // 나머지가 Reminder[i] 인 합의 개수를 구함

        long sum = Reminder[0]; // 0은 이미 추가됨.
        // 나머지가 Reminder[i] 인 것 중에 2개를 뽑음
        for (int i = 0; i < M; i++) sum += (Reminder[i] * (Reminder[i] - 1)) / 2;
        System.out.println(sum);
    }

    private static int read() throws Exception {
        int c, n = System.in.read() & 15;
        while ((c = System.in.read()) > 32) n = (n << 3) + (n << 1) + (c & 15);
        return n;
    }
}
