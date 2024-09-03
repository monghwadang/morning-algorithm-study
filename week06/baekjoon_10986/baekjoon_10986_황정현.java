import java.util.*;
import java.io.*;


public class baekjoon_10986_황정현 {
	static int N, M;
	static long prefix[], prefixPercent[];

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 누적합 배열의 맨 앞은 0으로 설정해놓는다. (아무것도 누적되어있지 않은 경우)
		prefix = new long[N+1];
		prefix[0] = 0;

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			prefix[i] = prefix[i-1] + Integer.parseInt(st.nextToken());
		}

		/**
		 * 1차 시도 
		 */
		// 1부터 5까지 있다는 가정하에 아래처럼 누적합을 구할 수 있다.
		// 만일 1부터 5번까지의 누적합을 구하게 된다면 5번 누적합에서 아무것도 빼면 안된다.
		// 즉, prefix[0] 을 0으로 설정한 이유가 여기에 있다.
		// 1-5  1-4  1-3  1-2
		// 2-5  2-4  2-3
		// 3-5  3-4
		// 4-5


		//		int count = 0;
		//		for(int i = N; i > 1; i--) {
		//			for(int j = 0; j < i; j++) {
		//				int sum = prefix[i] - prefix[j];
		//				if(sum % M == 0) count++;
		//			}
		//		}
		//		System.out.println(count);

		/**
		 * 2차 시도 (여기서 상수는 배열 인덱스를 의미한다.)
		 * 1 ~ 4 까지 누적합해서 %M를 하는 것은 (prefix[4] - prefix[1]) % M
		 * 이는 곳 (prefix[4] % M) - (prefix[1] % M) 와 같다. 
		 * 여기서 해당 공식이 0이 될때가 M으로 나눠떨어지는 경우이기 때문에
		 * (prefix[4] % M) - (prefix[1] % M) = 0 -> (prefix[4] % M) = (prefix[1] % M) 인 것을 알 수 있다.
		 * 즉, 각 누적합을 M으로 나눴을 나머지가 서로 같은 경우의 모든 경우의 수를 구하면 된다는 이야기 이다.
		 * 해당 input을 예로 들자면, 
		 * (원본) 1 2 3 1 2 -> (누적합) 0 1 3 6 7 9 -> (누적합 % M) 0 1 0 0 1 0
		 * 즉, 0(4개 중 2개) + 1(2개 중 2개) 구하면 된다.
		 * 4C2 + 2C2 라는 뜻이다.
		 */

		prefixPercent = new long[M];
		for(int i = 0; i <= N; i++) {
			prefixPercent[(int)(prefix[i] % M)]++;
		}

		long sum = 0;
		for(int i = 0; i < M; i++) {
			if(prefixPercent[i] >= 2) {
				sum += prefixPercent[i] * (prefixPercent[i]-1) / 2;
			}
		}

		System.out.println(sum);
	}
}
