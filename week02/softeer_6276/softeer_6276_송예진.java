package softeer;

import java.io.*;
import java.util.*;

public class Softeer_6276_GarageGame {
    static class Car{
        int r;
        int c;
        int num;

        public Car(){
            
        }

        public Car(int r, int c, int num){
            this.r = r;
            this.c = c;
            this.num = num;
        }
    }
    
    
    static int[][] boardSource;
    static int max;
    static int N;
    static int[] dr = {-1,1,0,0}; //상하좌우
    static int[] dc = {0,0,-1,1};
    
    public static void main(String[] args) throws Exception{
    // BFS로 boolean 현재 turn의 최대 면적을 구함 (X)
        //-> 그 turn에서는 최대이지만 전체로 보았을 땐 그 turn을 선택하는게 최선이 아닐 수 있음
        //-> 완전 탐색 해야 함
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        boardSource = new int[3*N][N];
        int[][] gameBoard = new int[N][N];
        int[] useSource = new int[N]; //boardSource에서 사용한 인덱스
        max = 0;
        for(int r=3*N-1; r>=0; r--){
            st = new StringTokenizer(br.readLine());
            for(int c=0; c<N; c++){
                boardSource[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        //gameBoard 초기화, useSource 초기화
        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                gameBoard[r][c] = boardSource[r][c];
            }
        }
        for(int n=0; n<N; n++){
            useSource[n] = N; //맨처음엔 source의 N-1번 인덱스까지 채우게 됨
        }


        game(1, 0, gameBoard, useSource);
        System.out.println(max);
    }

    //매 turn마다 탐색하는 함수
    //1: 1 turn에서 탐색 -> 없앤 부분 boolean처리 & sum
    //2: -> 재정렬한 새로운 배열을 -> 2 trun 재귀 -> 없앤 부분 boolean처리 & sum-> 
    //3: -> 재정렬한 새로운 배열을 -> 3 trun 재귀 ->  sum 비교
    // 매 trun에서 한 경우의 수가 끝나면 복원 하고 다른 case진행해야 함 
    //   -> boolean배열 초기화 & 지금 진행한 case를 처리할 boolean배열2는 따로 두어야 함

    public static void game(int turn, int curSum, int[][] nowGameBoard, int[] useSource){
        boolean[][] nowCheck = new boolean[N][N]; //이번턴에서 사용한거 체크

        if(turn >= 3 ){
            for(int r=0; r<N; r++){
                for(int c=0; c<N; c++){
                    //매 점에서 시작
                    if(nowCheck[r][c]) continue; //이미 지나간 곳이면 pass
                    // boolean[][] nowDelete = new boolean[N][N]; //이번에 지워지는 영역   
                    
                    /**
                     * 
                     * 
                     * nowDelete를 생성하지 않아도 됨. 이 로직 제거하고 nowDlelte를 사용하지 않게 BFS2를 만들었더니
                     * 시간초과 해결
                     * 
                     * 
                     * **/
                    
                    int nowSum = BFS2(nowCheck, nowGameBoard, r, c);
                    max = Math.max(curSum+nowSum, max);
                }
            }
            return;
        }
        
        for(int r=0; r<N; r++){
            for(int c=0; c<N; c++){
                //매 점에서 시작
                if(nowCheck[r][c]) continue; //이미 지나간 곳이면 pass
                boolean[][] nowDelete = new boolean[N][N]; //이번에 지워지는 영역
                int nowSum = BFS(nowCheck, nowDelete, nowGameBoard, r, c);
                int[][] newGameBoard = null;
                int[] nowSource = new int[N];
                for(int n=0; n<N; n++){
                        //매번 소스 가리키는 인덱스 갱신
                   nowSource[n] = useSource[n];
                }
                newGameBoard = arrange(nowDelete, nowGameBoard, nowSource);
                game(turn+1, curSum+nowSum, newGameBoard, nowSource);

            }
        }
    }

    
    public static int BFS(boolean[][] nowCheck, boolean[][] nowDelete, int[][] nowGameBoard, int r, int c){
        //사방 탐색 BFS
        int cnt = 0; //삭제되는 블록 카운트
                Queue<Car> que = new LinkedList<>();
                que.add(new Car(r,c,nowGameBoard[r][c]));
                nowCheck[r][c] = true;
                nowDelete[r][c] = true;
                //직사각형 넓이 구할 것
                int minR = Integer.MAX_VALUE;
                int maxR = Integer.MIN_VALUE;
                int minC = Integer.MAX_VALUE;
                int maxC = Integer.MIN_VALUE;
                while(!que.isEmpty()){
                    Car nowCar = que.poll();
                    int cr = nowCar.r;
                    int cc = nowCar.c;
                    cnt++;
                    //직사각형 넓이 구하기 위한 곳
                    minR = Math.min(minR, cr);
                    maxR = Math.max(maxR, cr);
                    minC = Math.min(minC, cc);
                    maxC = Math.max(maxC, cc);
                    for(int d=0; d<4; d++){
                        int nr = cr + dr[d];
                        int nc = cc + dc[d];
                        if(nr<0||nc<0||nr>=N||nc>=N) continue; //경계조건
                        if(nowGameBoard[nr][nc]==nowCar.num && !nowDelete[nr][nc]){
                            que.add(new Car(nr,nc,nowCar.num));
                            nowCheck[nr][nc] = true;
                            nowDelete[nr][nc] = true;
                        }
                    }
                }
 
                //직사각형 넓이 구하기
                return (maxR-minR+1)*(maxC-minC+1) +cnt;   
    }

    public static int BFS2(boolean[][] nowCheck, int[][] nowGameBoard, int r, int c){
        //사방 탐색 BFS
        int cnt = 0; //삭제되는 블록 카운트
                Queue<Car> que = new LinkedList<>();
                que.add(new Car(r,c,nowGameBoard[r][c]));
                nowCheck[r][c] = true;
                //직사각형 넓이 구할 것
                int minR = Integer.MAX_VALUE;
                int maxR = Integer.MIN_VALUE;
                int minC = Integer.MAX_VALUE;
                int maxC = Integer.MIN_VALUE;
                while(!que.isEmpty()){
                    Car nowCar = que.poll();
                    int cr = nowCar.r;
                    int cc = nowCar.c;
                    cnt++;
                    //직사각형 넓이 구하기 위한 곳
                    minR = Math.min(minR, cr);
                    maxR = Math.max(maxR, cr);
                    minC = Math.min(minC, cc);
                    maxC = Math.max(maxC, cc);
                    for(int d=0; d<4; d++){
                        int nr = cr + dr[d];
                        int nc = cc + dc[d];
                        if(nr<0||nc<0||nr>=N||nc>=N) continue; //경계조건
                        if(nowGameBoard[nr][nc]==nowCar.num && !nowCheck[nr][nc]){
                            que.add(new Car(nr,nc,nowCar.num));
                            nowCheck[nr][nc] = true;
                        }
                    }
                }
 
                //직사각형 넓이 구하기
                return (maxR-minR+1)*(maxC-minC+1) +cnt;   
    }

    
    //재정렬하는 함수 -> 반환값 새로운 함수..
    //-> 대기중인 배열의 몇번째 값까지를 가져왔는지를 curr 배열에서 확인해서 그 인덱스부터 가져오기
    public static int[][] arrange(boolean[][] nowDelete, int[][] nowGameBoard, int[] nowSource){
        int[][] newGameBoard = new int[N][N];

        for(int c=0; c<N; c++){
            int fillNum = 0;
            int fillR = 0;
            for(int r=0; r<N; r++){
                if(nowDelete[r][c]){
                    fillNum++;
                    continue;
                }
                newGameBoard[fillR++][c] = nowGameBoard[r][c]; //값이 있는 것 먼저 채우기
            }
            for(int i=1; i<=fillNum; i++){
                newGameBoard[fillR++][c] = boardSource[nowSource[c]++][c];
            }
        }
        return newGameBoard;
    }
}