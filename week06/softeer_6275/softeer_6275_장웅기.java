package softeer_6275;

import java.util.Scanner;

public class softeer_6275_장웅기 {

    static int R, C;
    static char[][] map;
    static boolean[][] visited;

    // 0: 아래, 1: 오른, 2: 위, 3: 왼
    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};
    static char[] dir = {'v', '>', '^', '<'}; // 방향 마크
    static String[] rotStr = {"A", "RA", "RRA", "LA"}; // 명령어

    static int lr = 0, lc = 0, ld = 0; // 처음 시작 r, c, d
    static String lPath = ""; // 최종 경로
    static int roadCount = 0; // '#' 개수

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        map = new char[R][C];
        visited = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            String line = sc.next();
            for (int j = 0; j < C; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == '#') roadCount++; // 도로 개수를 구함
            }
        }

        Loop:
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == '.') continue;
                if (!isStart(i, j)) continue; // 시작점이 아니면 continue;
                for (int k = 0; k < 4; k++) {
                    if (!canStraight(i, j, k)) continue; // 직진할 수 있는 방향이 아니면 continue;
                    visited = new boolean[R][C];
                    visited[i][j] = true;

                    lr = i; lc = j; ld = k;
                    backtracking(i, j, k, 1, "");
                    break Loop;
                }
            }
        }

        System.out.println(++lr + " " + ++lc);
        System.out.println(dir[ld]);
        System.out.println(lPath);
    }

    private static void backtracking(int r, int c, int d, int cnt, String path) {
        if (isAllDone(cnt)) { // 전부 이동했으면,
            lPath = path;
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            int nr2 = r + dr[i] * 2;
            int nc2 = c + dc[i] * 2;

            if (!isInBoundary(nr2, nc2)) continue; // 맵 안에 존재하는지
            if (map[nr][nc] == '.' || map[nr2][nc2] == '.') continue;
            if (visited[nr2][nc2]) continue;

            visited[nr][nc] = true;
            visited[nr2][nc2] = true;
            backtracking(nr2, nc2, i, cnt + 2, path + getPath(d, i));
        }
    }

    // 시작점인지 확인하는 메서드
    // 주위에 도로가 하나만 있어야 시작점
    private static boolean isStart(int r, int c) {
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (!isInBoundary(nr, nc)) continue;
            if (map[nr][nc] == '.') continue;
            cnt++;
        }
        return cnt == 1;
    }

    // 직진할 수 있는 방향인지 체크하는 메서드
    // 두 칸 앞으로 갈 때, 두 칸 모두 전부 도로인지 체크함
    private static boolean canStraight(int r, int c, int k) {
        int nr = r + dr[k], nc = c + dc[k];
        int nr2 = r + dr[k] + dr[k], nc2 = c + dc[k] + dc[k];
        return isInBoundary(nr, nc) && map[nr][nc] == '#' && map[nr2][nc2] == '#';
    }

    private static boolean isInBoundary(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    // 도로를 전부 주행했는지 확인하는 메서드
    // 도로의 개수와 지나간 도로 개수가 같으면 전부 주행
    private static boolean isAllDone(int cnt) {
        return roadCount == cnt;
    }

    // 회전 방향에 맞는 명령어를 찾는 메서드
    // 현재 방향과 회전이 끝난 방향을 빼, 회전 방향에 맞는 명령어를 구함
    private static String getPath(int d, int nd) {
        return rotStr[(d - nd + 4) % 4];
    }
}
