import java.util.*;
import java.io.*;
import java.awt.*;

public class softeer_6276_김명화 {
    static int[] dr = {0, 0, -1, 1};
    static int[] dc = {-1, 1, 0, 0};
    static int[][] board = new int[45][15];
    static int N;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < 3 * N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dfs(0, 0);
        System.out.println(max);
    }

    // turn : 차례, score : 점수
    static void dfs(int turn, int score) {
        int[][] backup_board = new int[45][15];
        for (int i = 0; i < board.length; i++) {
            backup_board[i] = board[i].clone(); // 복사
        }

        boolean[][] visit = new boolean[15][15];
        for (int i = 2 * N; i < 3 * N; i++) {
            for (int j = 0; j < N; j++) {
                int flag = backup_board[i][j]; // 한 칸 선택
                if (!visit[i-2 * N][j] && flag != 0) { // 이런 상태라면 다시 원상복구
                    for (int k = 0; k < board.length; k++) {
                        board[k] = backup_board[k].clone();
                    }

                    int minX = i, maxX = i, minY = j, maxY = j;
                    Queue<Point> q = new LinkedList<>();
                    q.offer(new Point(i,j));
                    visit[i - 2 * N][j] = true;
                    int cnt = 1; // 없애는 자동차의 개수
            
                    while (!q.isEmpty()) {
                        Point curr = q.poll();
                        int currX = curr.x;
                        int currY = curr.y;
            
                        board[currX][currY] = 0; // 없애기
                        minX = Math.min(minX, currX);
                        maxX = Math.max(maxX, currX);
                        minY = Math.min(minY, currY);
                        maxY = Math.max(maxY, currY);
            
                        for (int d = 0; d < 4; d++) {
                            int nr = currX + dr[d], nc = currY + dc[d];
                            if (check(nr, nc)&& !visit[nr-2 * N][nc] && backup_board[nr][nc] == flag) {
                                visit[nr-2 * N][nc] = true;
                                q.offer(new Point(nr,nc));
                                cnt++;
                            }
                        }
                    }
                    
                    // 중력 시뮬
                    if (turn == 0 || turn == 1) { // 첫 번째, 두 번째 시뮬에서만 실행
                        for (int x = minX; x <= maxX; x++) {
                            for (int y = minY; y <= maxY; y++) {
                                if (board[x][y] == 0) {
                                    int step = 0; // 해당 열에서 몇 칸 내릴지 저장
                                    for (int xx = x-1; xx >= 0; xx--) {
                                        if (board[xx][y] != 0) {
                                            step = x - xx;
                                            break;
                                        }
                                    }
                                    for (int xx = x; xx >= step; xx--) {
                                        board[xx][y] = board[xx-step][y];
                                        board[xx-step][y] = 0;
                                    }
                                }
                            }
                        }              
                        // 재귀
                        dfs(turn+1, score + cnt + ((maxX-minX+1)*(maxY-minY+1)));
                    } else { // 세 번째 시뮬에서는 최대 점수만 업데이트
                        max = Math.max(max, score + cnt + ((maxX-minX+1)*(maxY-minY+1)));
                    }
                }
            }
        }
    }

    static boolean check(int x, int y){
      return  x >= 2 * N && x < 3 * N && y >= 0 && y < N;
    }
}