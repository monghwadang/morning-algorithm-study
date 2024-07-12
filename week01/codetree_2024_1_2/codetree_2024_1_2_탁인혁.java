import java.io.*;
import java.util.*;

public class codetree_2024_1_2_탁인혁 {
    static class Node {
        int mId;
        int pId;
        int color;
        int maxDepth;
        boolean[] colors;
        public Node(int mId, int pId, int color, int maxDepth) {
            this.mId = mId;
            this.pId = pId;
            this.color = color;
            this.maxDepth = maxDepth;
            this.colors = new boolean[6];
            this.colors[color] = true;
        }
    }

    // 노드 추가 100 m_id p_id color max_depth
    // 색깔 변경 200 m_id color
    // 색깔 조회 300 m_id
    // 점수 조회 400

    static int mId = 0;
    static int pId = 0;
    static int color = 0;
    static int maxDepth = 0;

    static Node[] tree = new Node[100001];
    static List<Integer>[] children = new List[100001];
    static Deque<Node> deque = new ArrayDeque<>();
    static List<Integer> roots = new ArrayList<>();

    static int result = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int q = Integer.parseInt(br.readLine());

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int action = Integer.parseInt(st.nextToken());
            switch (action) {
                case 100:
                    mId = Integer.parseInt(st.nextToken());
                    pId = Integer.parseInt(st.nextToken());
                    color = Integer.parseInt(st.nextToken());
                    maxDepth = Math.min(Integer.parseInt(st.nextToken()), pId == -1 ? 100000 : tree[pId].maxDepth - 1);
                    addNode(mId, pId, color, maxDepth);
                    break;
                case 200:
                    mId = Integer.parseInt(st.nextToken());
                    color = Integer.parseInt(st.nextToken());
                    changeColor(mId, color);
                    break;
                case 300:
                    mId = Integer.parseInt(st.nextToken());
                    result = selectColor(mId);
                    System.out.println(result);
                    break;
                case 400:
                    result = 0;
                    for (int j = 0; j < roots.size(); j++) {
                        mId = roots.get(j);
                        selectValue(mId);
                    }
                    System.out.println(result);
                    break;
                default:
                    break;
            }
        }
    }

    public static void addNode(int mId, int pId, int color, int maxDepth) {
        if (maxDepth < 1) {
            return;
        }
        tree[mId] = new Node(mId, pId, color, maxDepth);
        if (pId == -1) {
            roots.add(mId);
        } else {
            if (children[pId] == null) {
                children[pId] = new ArrayList<>();
            }
            children[pId].add(mId);
        }
    }

    public static void changeColor(int mId, int color) {
        deque.add(tree[mId]);
        while (!deque.isEmpty()) {
            Node node = deque.poll();
            node.color = color;
            if (children[node.mId] != null) {
                for (int child : children[node.mId]) {
                    deque.add(tree[child]);
                }
            }
        }
    }

    public static int selectColor(int mId) {
        return tree[mId].color;
    }

    public static void selectValue(int mId) {
        int value = 1;
        Arrays.fill(tree[mId].colors, false);
        tree[mId].colors[tree[mId].color] = true;
        if (children[mId] != null) {
            for (int child : children[mId]) {
                selectValue(child);
                for (int i = 1; i <= 5; i++) {
                    if (tree[child].colors[i]) {
                        tree[mId].colors[i] = true;
                    }
                }
            }
            int cnt = 0;
            for (int i = 1; i <= 5; i++) {
                if (tree[mId].colors[i]) {
                    cnt++;
                }
            }
            value = cnt;
        }
        result += value * value;
    }
}