package softeer_6277;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 모든 색깔을 한 번씩 탐색할 때마다, 가장 작은 면적을 구함
 *
 * 1. 새로운 점을 포함할 때, 최소 최대 xy를 구함
 * 2. 1에서 구한 최소 최대 xy의 면적이 현재 최소 면적보다
 *      크면, 같은 색깔의 다른 점 탐색
 *      작으면, 최소 최대 xy 갱신 후, 다른 색깔 탐색
 * 3. 모든 색깔 탐색하면, 현재 최소 면적과 최소 최대 xy의 면적을 비교 후 갱신
 */

public class softeer_6277_장웅기 {

    static int N, K;                    // N: 점 수, K: 색깔 수
    static int min = Integer.MAX_VALUE; // min: 최소 면적

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static List<List<Point>> pointList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();

        for (int i = 0; i <= K; i++) {
            pointList.add(new ArrayList<>());
        }

        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int c = sc.nextInt();
            Point p = new Point(x, y);
            pointList.get(c).add(p);
        }

        backtracking(1, 1001, -1001, 1001, -1001);
        System.out.println(min);
    }

    /**
     * depth(=색깔 숫자)를 늘려가며 depth에 포함된 각 점의 최소, 최대 xy를 찾아
     * xy에 따라 최소 면적을 찾는 메서드
     *
     * @param depth 색깔 숫자
     * @param minX  최소 x
     * @param maxX  최대 x
     * @param minY  최소 y
     * @param maxY  최소 y
     */
    private static void backtracking(int depth, int minX, int maxX, int minY, int maxY) {
        if (depth > K) {
            min = Math.min(min, getArea(minX, maxX, minY, maxY));
            return;
        }

        List<Point> points = pointList.get(depth);

        // 각 색깔의 점을 차례로 탐색
        // 현재 최소 최대 xy와 포함할 점의 xy를 비교함
        for (Point point : points) {
            int minXN = Math.min(minX, point.x);
            int maxXN = Math.max(maxX, point.x);
            int minYN = Math.min(minY, point.y);
            int maxYN = Math.max(maxY, point.y);
            int area = getArea(minXN, maxXN, minYN, maxYN);

            // 구한 면적이 현재 최소 면적보다 작으면 다음 색깔 탐색
            if (area < min || depth == 1) {
                backtracking(depth + 1, minXN, maxXN, minYN, maxYN);
            }
        }
    }

    /**
     * 사각형의 면적을 구하는 메서드
     * @param minX 최소 x
     * @param maxX 최대 x
     * @param minY 최소 y
     * @param maxY 최대 y
     * @return 사각형의 면적
     */
    private static int getArea(int minX, int maxX, int minY, int maxY) {
        return Math.abs((maxX - minX) * (maxY - minY));
    }
}
