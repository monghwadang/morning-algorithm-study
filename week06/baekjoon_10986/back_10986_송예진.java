package back_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Back_10986_나머지합 {
	private static int N, M;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 입력받기
		st = new StringTokenizer(br.readLine());
		long[] arr = new long[N];
		int[] remain = new int[N];
		int[] countR = new int[M];
		long ans = 0;
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			if (i != 0)
				arr[i] = arr[i - 1] + arr[i];
			remain[i] = (int)(arr[i] % M);
			countR[remain[i]]++;
		}

		for(int i=0; i<M; i++){
			ans+=calcul(countR[i]);
		}
		//0은 한 번 더 더해주기
		ans += countR[0];
		System.out.println(ans);

	}

	public static long calcul(long r){
		if(r<2) return 0;
		return r*(r-1)/2;
	}
}
