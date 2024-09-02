import java.io.*;
import java.util.*;

public class Main {
    static int H; // 행 r
    static int W; // 열 c
    static int[][] map; // 맵
    static boolean[][] visited; // 방문확인
    static int[] dr = {-1, 1, 0, 0}; // 상하좌우
    static int[] dc = {0, 0, -1, 1}; // 상하좌우
    static int[] start = new int[2]; // 시작점
    static int dir = -1; // 시작방향
    static StringBuilder sb = new StringBuilder(); // 명령어

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        // map 세팅
        map = new int[H][W];
        visited = new boolean[H][W];
        for (int r = 0; r < H; r++) {
            String str = br.readLine();
            char[] strArr = str.toCharArray();
            for (int c = 0; c < W; c++) {
                if (strArr[c] == '#') {
                    map[r][c] = 1; // 길은 '#'이 아닌 1로 저장
                }
            }
        }

        // 시작점 찾기
        findStartPoint();

        // 명령어 입력
        findCommand(start[0], start[1], dir);

        // 출력할 때 r, c +1 해서 출력하기 (int[] start)
        System.out.println((start[0] + 1) + " " + (start[1] + 1));
        char cDir = '.';
        if (dir == 0) { // 상
            cDir = '^';
        } else if (dir == 1) { // 하
            cDir = 'v';
        } else if (dir == 2) { // 좌
            cDir = '<';
        } else if (dir == 3) { // 우
            cDir = '>';
        }
        System.out.println(cDir);
        System.out.println(sb);
    }

    public static void findStartPoint() { // 시작점 찾기
        int gr = 0;
        int gc = 0;
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (map[r][c] == 1) {
                    int check = 0;
                    for (int i = 0; i < 4; i++) { // 4방탐색
                        int nr = r + dr[i];
                        int nc = c + dc[i];
                        if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] == 1) { // 탐색한 곳이 길이면 체크하고 저장
                            check++;
                            gr = nr;
                            gc = nc;
                        }
                    }
                    if (check == 1) { // 상하좌우 확인해서 벽(1)이 1개만 있으면 끝점 -> 시작점으로 설정
                        // 시작점 세팅
                        start[0] = r;
                        start[1] = c;
                        visited[r][c] = true;
                        // 방향 세팅
                        int rr = gr - r;
                        int cc = gc - c;
                        if (rr == -1 && cc == 0) { // 상
                            dir = 0;
                        } else if (rr == 1 && cc == 0) { // 하
                            dir = 1;
                        } else if (rr == 0 && cc == -1) { // 좌
                            dir = 2;
                        } else if (rr == 0 && cc == 1) { // 우
                            dir = 3;
                        }
                        return;
                    }
                }
            }
        }
    }

    public static void findCommand(int r, int c, int d) { // 명령어 입력
        int dd = -1;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] == 1 && !visited[nr][nc]) {
                // 방향 세팅
                int rr = nr - r;
                int cc = nc - c;
                if (rr == -1 && cc == 0) { // 상
                    dd = 0;
                } else if (rr == 1 && cc == 0) { // 하
                    dd = 1;
                } else if (rr == 0 && cc == -1) { // 좌
                    dd = 2;
                } else if (rr == 0 && cc == 1) { // 우
                    dd = 3;
                }

                // 방향 전환
                if (d != dd) {
                    if (d == 0) {
                        if (dd == 2) {
                            sb.append("L");
                        } else if (dd == 3) {
                            sb.append("R");
                        }
                    } else if (d == 1) {
                        if (dd == 2) {
                            sb.append("R");
                        } else if (dd == 3) {
                            sb.append("L");
                        }
                    } else if (d == 2) {
                        if (dd == 0) {
                            sb.append("R");
                        } else if (dd == 1) {
                            sb.append("L");
                        }
                    } else if (d == 3) {
                        if (dd == 0) {
                            sb.append("L");
                        } else if (dd == 1) {
                            sb.append("R");
                        }
                    }
                }
                // 전진
                sb.append("A");

                visited[nr][nc] = true;
                visited[nr+rr][nc+cc] = true;
                findCommand(nr + rr, nc + cc, dd);
                break;
            }
        }
    }

}