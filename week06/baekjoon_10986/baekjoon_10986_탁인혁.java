import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] modCnt = new int[M]; // 나머지 개수 저장 배열

        st = new StringTokenizer(br.readLine());
        int sumMod = 0; // 누적합 나머지 저장 변수
        for (int i = 0; i < N; i++) {
            sumMod = (sumMod + Integer.parseInt(st.nextToken())) % M; // 누적합 나머지 계산
            modCnt[sumMod]++; // 나머지 개수 저장 배열에 +1
        }

        long result = modCnt[0]; // 나머지 0 개수로 초기화, 자료형 long
        for (int i = 0; i < M; i++) {
            long cnt = modCnt[i]; // 자료형 long
            result += (cnt * (cnt - 1)) / 2; // 조합식(Combination)
        }

        System.out.println(result);
    }
}