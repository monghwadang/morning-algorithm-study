package week02.softeer_6276;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class softeer_6276_장웅기 {

    static int N, mx = Integer.MIN_VALUE; // N: 차고 크가, mx: 최대 점수
    static int[][] garage; // 큰 차고 배열

    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        garage = new int[3 * N][N];
        for (int i = 3 * N - 1; i >= 0; i--) {
            for (int j = 0; j < N; j++) {
                garage[i][j] = sc.nextInt();
            }
        }
        backtracking(garage, 0, 0);
        System.out.println(mx);
    }

    // 백트래킹
    static void backtracking(int[][] garage, int depth, int sum) {
        boolean[][] visited = new boolean[N][N]; // 작은 차고 배열 방문 여부

        // depth가 2 이면 큰 차고 배열을 복사하지 않고 점수만 계산
        if (depth == 2) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (visited[i][j]) continue;

                    int color = garage[i][j];
                    int score = bfs(visited, garage, i, j, color);
                    mx = Math.max(mx, sum + score);
                }
            }
            return;
        }

        // depth가 2 미만이면 큰 차고 배열을 복사하고 점수를 계산해, 없어진 색깔을 밀어서 채운 다음 백트래킹
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j]) continue;

                int[][] tmp = new int[3 * N][N]; // tmp: 큰 차고 배열 복사본
                for (int r = 0; r < 3 * N; r++) {
                    for (int c = 0; c < N; c++) {
                        tmp[r][c] = garage[r][c];
                    }
                }

                int color = tmp[i][j];
                int score = bfs(visited, tmp, i, j, color); // bfs로 점수 계산
                fillNonZero(tmp); // 없어진 색깔을 밀어서 채움
                backtracking(tmp, depth + 1, sum + score);
            }
        }

    }

    // 없어진 색깔을 밀어서 채우는 메서드
    // 0: 없어진 색깔
    // 0인 r을 찾은 다음, r에 가장 가까운 0이 아닌 nr을 찾아 서로 바꿈
    private static void fillNonZero(int[][] tmp) {
        for (int c = 0; c < N; c++) {
            for (int r = 0; r < 3 * N; r++) {
                if (tmp[r][c] > 0) continue;

                int nr = r;
                while (nr < 3 * N && tmp[nr][c] == 0) nr++;
                if (nr >= 3 * N) continue;
                tmp[r][c] = tmp[nr][c];
                tmp[nr][c] = 0;
            }
        }
    }

    // bfs로 인접한 같은 색깔을 찾아 0으로 바꾸고,
    // "0으로 바꾼 갯수"와 "해당 차고를 포함하는 가장 작은 직사각형 면적"을 찾아 더하는 메서드
    private static int bfs(boolean[][] visited, int[][] tmp, int i, int j, int color) {
        int minR = 100, maxR = 0, minC = 100, maxC = 0;
        int cnt = 0;

        Queue<Info> q = new LinkedList<>();
        visited[i][j] = true;
        tmp[i][j] = 0;
        q.add(new Info(i, j));

        while (!q.isEmpty()) {
            Info cur = q.remove();

            minR = Math.min(minR, cur.r);
            maxR = Math.max(maxR, cur.r);
            minC = Math.min(minC, cur.c);
            maxC = Math.max(maxC, cur.c);
            cnt++;

            for (int k = 0; k < 4; k++) {
                int nr = cur.r + dr[k];
                int nc = cur.c + dc[k];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (visited[nr][nc]) continue;
                if (color != tmp[nr][nc]) continue;

                visited[nr][nc] = true;
                tmp[nr][nc] = 0;
                q.add(new Info(nr, nc));
            }
        }
        return ((maxR - minR + 1) * (maxC - minC + 1)) + cnt;
    }

    static class Info {
        int r, c;
        public Info(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
