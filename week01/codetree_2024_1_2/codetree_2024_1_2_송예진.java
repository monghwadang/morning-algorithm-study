package codetree_2024_1_2;

import java.io.*;
import java.util.*;

public class codetree_2024_1_2_송예진 {

    static class Node{
        int mId;
        int pId;
        List<Integer> cId; //바로 depth의 자식들의 nodes의 Idx저장
        int color;
        int maxD;
        int currD; //현재의 depth
        int currPossibleD; //밑으로 가능한 개수

        public Node() {

        }

        public Node(int mId, int pId, int color, int maxD) {
            this.mId = mId;
            this.pId = pId;
            this.color = color;
            this.maxD = maxD;
        }

    }

    public static List<Node> nodes; //노드들을 담을 리스트
    public static Map<Integer, Integer> nodesMatch; //노드 아이디=key , 해당 노드의 리스트에서의 idx

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine()); //Q입력받기
        nodes = new ArrayList<>(); //초기화
        nodesMatch = new HashMap<>();
        StringTokenizer st;

        for(int q=0; q<Q; q++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            //초기화
            int mId, pId, color, maxD = 0;

            switch(order) {
                case 100:
                    //노드 추가
                    mId = Integer.parseInt(st.nextToken());
                    pId = Integer.parseInt(st.nextToken());
                    color = Integer.parseInt(st.nextToken());
                    maxD = Integer.parseInt(st.nextToken());
                    add(mId, pId, color, maxD);
                    break;
                case 200:
                    //색깔 변경
                    mId = Integer.parseInt(st.nextToken());
                    color = Integer.parseInt(st.nextToken());
                    colorChange(mId, color);
                    break;
                case 300:
                    //색깔 조회
                    mId = Integer.parseInt(st.nextToken());
                    System.out.println(color(mId));;
                    break;
                case 400:
                    //점수 조회
                    System.out.println(grade());
                    break;
            }

        }

    }

    public static void add(int mId, int pId, int color, int maxD){
        Node node = new Node(mId, pId, color, maxD);
        node.cId = new ArrayList<>();
        if(pId==-1) {
            //루트 노드일때
            node.currD=1;
            node.currPossibleD = maxD-1;
            nodes.add(node);
            nodesMatch.put(mId, 0);
            return;
        }

//		System.out.println(nodesMatch);
        int pNodeIdx = nodesMatch.get(pId);
        Node pNode = nodes.get(pNodeIdx);
        if(pNode.cId.size()==0) {
            //자식이 추가된적이 없었으면
            pNode.currPossibleD --; //depth 하나 추가했으니까 반영
        }
        node.currPossibleD = Math.min(maxD-1, pNode.currPossibleD);
        if(node.currPossibleD<0) {
            return; //추가할 수 없는 상황
        }

        int NodeIdx = nodes.size(); //지금 사이즈가 추가하는 노드의 인덱스가 됨. 지금 마지막 노드의 인덱스는 -1이기 때문
        node.currD = pNode.currD + 1; //현재 노드의 Depth는 부모 노드의 +1
        pNode.cId.add(NodeIdx); //부모 노드의 자식배열에 저장
        nodes.add(node);
        nodesMatch.put(mId, nodes.size()-1);
    }

    public static void colorChange(int mId, int color) {
        int nodeIdx = nodesMatch.get(mId);
        Node node = nodes.get(nodeIdx);

        Queue<Node> que = new LinkedList<>();
        que.add(node);

        while(!que.isEmpty()) {
            Node curr = que.poll();

            for(int idx : curr.cId) {
                Node tmp = nodes.get(idx);
                tmp.color = color;
                que.add(tmp);
            }

        }

    }

    public static int color(int mId) {
        int nodeIdx = nodesMatch.get(mId);
        Node node = nodes.get(nodeIdx);
        return node.color;
    }

    public static int grade() {

        return 0;
    }

}


