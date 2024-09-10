import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class baekjoon_7662_장웅기 {

    static int T, k;
    // 이중 우선순위 큐 생성
    static DoublePriorityQueue dpq = new DoublePriorityQueue();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt();
        while (T-- > 0) {
            dpq.init();
            k = sc.nextInt();
            while (k-- > 0) {
                String cmd = sc.next();
                Integer arg = Integer.parseInt(sc.next());
                switch (cmd) {
                    case "I": {
                        dpq.insert(arg);
                        break;
                    }
                    case "D": {
                        if (arg == 1) dpq.removeHigher();
                        else dpq.removeLower();
                        break;
                    }
                }
            }
            System.out.println(dpq.print());
        }
    }

    // 이중 우선순위 큐 클래스 선언
    private static class DoublePriorityQueue {
        TreeMap<Integer, Integer> tm;

        public void init() {
            tm = new TreeMap<>();
        }

        // 추가하는 로직 (여러 개라면, value++)
        public void insert(Integer key) {
            tm.put(key, tm.getOrDefault(key, 0) + 1);
        }

        // 높은 친구 삭제
        public void removeHigher() {
            if (tm.isEmpty()) return;
            remove(tm.lastKey());
        }

        // 낮은 친구 삭제
        public void removeLower() {
            if (tm.isEmpty()) return;
            remove(tm.firstKey());
        }

        // 삭제하는 로직 (여러 개라면, value--)
        private void remove(int lowerKey) {
            if (tm.get(lowerKey) == 1) tm.remove(lowerKey);
            else tm.put(lowerKey, tm.get(lowerKey) - 1);
        }

        
        public String print() {
            return tm.isEmpty() ? "EMPTY" : tm.lastKey() + " " + tm.firstKey();
        }
    }
}