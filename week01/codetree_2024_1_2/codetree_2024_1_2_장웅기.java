package codetree_2024_1_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class codetree_2024_1_2_장웅기 {
    static int Q;   // Q: 명령의 수

    static Node[] nodes = new Node[100001]; // nodes: 노드를 저장할 배열 (인덱스는 노드 고유 번호)

    static class Node {
        int m;      // 노드 고유 번호
        int p;      // 부모 노드 번호
        int color;  // 색깔
        int max;    // 최대 깊이
        List<Node> children = new ArrayList<>(); // 자식 노드

        public Node(int m, int p, int color, int max) {
            this.m = m;
            this.p = p;
            this.color = color;
            this.max = max;
        }
    }

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());

        for (int i = 0; i < Q; i++) {
            String[] tokens = br.readLine().split(" ");
            int cmd = Integer.parseInt(tokens[0]);
            switch (cmd) {
                case 100: {
                    int m = Integer.parseInt(tokens[1]);
                    int p = Integer.parseInt(tokens[2]);
                    int color = Integer.parseInt(tokens[3]);
                    int max = Integer.parseInt(tokens[4]);
                    addNode(m, p, color, max);
                    break;
                }
                case 200: {
                    int m = Integer.parseInt(tokens[1]);
                    int color = Integer.parseInt(tokens[2]);
                    changeColor(m, color);
                    break;
                }
                case 300: {
                    int m = Integer.parseInt(tokens[1]);
                    showColor(m);
                    break;
                }
                case 400: {
                    showScore();
                    break;
                }
            }
        }
        System.out.print(sb);
    }

    /**
     * 노드를 추가하는 메서드
     * 만약 조상 노드 중 하나라도 최대 깊이에 모순이 있을 경우, 노드 추가를 하지 않음
     *
     * @param m     고유 번호
     * @param p     부모 노드 번호 (-1이면 본인이 루트)
     * @param color 색깔(빨강 1, 주황 2, 노랑 3, 초록 4, 파랑 5)
     * @param max   해당 노드를 루트로 하는 서브 트리의 최대 깊이 (자기 자신은 1)
     */
    private static void addNode(int m, int p, int color, int max) {
        Node node = new Node(m, p, color, max);
        if (p == -1) {
            nodes[m] = node;
            return;
        }
        if (!isCorrectParentDepth(p, 1)) return;
        nodes[m] = node;
        nodes[p].children.add(node);
    }

    /**
     * 노드 추가시, 조상 노드의 모순이 있는지 확인하는 메서드
     *
     * @param p     검사할 노드의 부모 노드 번호
     * @param depth 노드를 추가한다면, 조상 노드로부터 추가할 노드까지 트리의 깊이
     * @return      모순(false), 모순아님(true)
     */
    private static boolean isCorrectParentDepth(int p, int depth) {
        if (p == -1) return true; // 부모 노드가 -1이라면, 모순이 없이 루트까지 왔다는 뜻이므로 true
        if (nodes[p].max < depth + 1) return false; // 부모 노드의 최대 깊이가 부모를 포함한 서브트리의 깊이보다 작다면 false;
        return isCorrectParentDepth(nodes[p].p, depth + 1); // 모순이 없다면, 부모 탐색
    }

    /**
     * 노드 m을 루트로 하는 서브 트리의 모든 노드의 색깔을 color로 변경하는 메서드
     * @param m
     * @param color
     */
    private static void changeColor(int m, int color) {
        Node cur = nodes[m];
        cur.color = color;
        if (cur.children.isEmpty()) return;
        for (Node child : cur.children) {
            changeColor(child.m, color);
        }
    }

    /**
     * 노드 m의 현재 색깔 조회해 출력하는 메서드
     * @param m 노드 고유 번호
     */
    private static void showColor(int m) {
        sb.append(nodes[m].color).append("\n");
    }

    /**
     * 가치 제곱의 합을 출력하는 메서드
     */
    private static void showScore() {
        sb.append(getScore()).append("\n");
    }

    /**
     * 모든 노드의 가치를 계산, 가치 제곱의 합을 계산하는 메서드
     * 가치 : 해당 노드를 루트로 하는 서브트리 내 서로 다른 색깔의 수
     * @return 전체 점수
     */
    private static int getScore() {
        int sum = 0;
        for (Node node : nodes) {
            if (node == null) continue;
            int count = Integer.bitCount(getCount(node));
            int value = count * count;
            sum += value;
        }
        return sum;
    }

    /**
     * 해당 노드를 루트로 하는 서브트리 내 서로 다른 색깔의 수를 구하는 메서드
     * @param cur 현재 노드
     * @return 해당 노드를 루트로 하는 서브트리 내 서로 다른 색깔의 수 (비트)
     */
    private static int getCount(Node cur) {
        int check = 1 << cur.color;
        List<Node> children = cur.children;
        if (children.isEmpty()) return check;

        for (Node child : children) {
            check |= getCount(child);
        }
        return check;
    }
}
