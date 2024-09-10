import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeSet;

public class pro2_장웅기 {

    static int N, L, M;
    static int[] mAbility;
    static League[] leagues;
    static Deque<Integer> dq;

    public void init(int n, int l, int[] mAbility) {
        N = n;
        L = l;
        M = N / L;
        pro2_장웅기.mAbility = new int[N];
        leagues = new League[L];
        System.arraycopy(mAbility, 0, pro2_장웅기.mAbility, 0, N);

        dq = new LinkedList<>(); // move, trade 시 사용하는 큐

        // 비교 Comparator
        // Ability 같으면, id 비교
        // Ability 다르면, Ability 값 비교
        Comparator<Integer> comparator = (i1, i2) -> {
            if (mAbility[i2] == mAbility[i1]) return i1 - i2;
            return mAbility[i2] - mAbility[i1];
        };

        for (int i = 0; i < L; i++) {
            leagues[i] = new League(comparator);
            for (int j = 0; j < M; j++) {
                leagues[i].add(i * M + j);
            }
        }
    }

    public int move() {
        int L_1 = L-1;

        for (int l = 0; l < L_1; l++)
            dq.addLast(leagues[l].removeLast());
        for (int l = 1; l < L; l++)
            dq.addLast(leagues[l].removeFirst());

        int sum = 0;
        for (int i : dq) sum += i;

        for (int l = 1; l < L; l++) {
            leagues[l].add(dq.removeFirst());
        }
        for (int l = 0; l < L_1; l++) {
            leagues[l].add(dq.removeFirst());
        }

        return sum;
    }

    public int trade() {
        int L_1 = L-1;

        for (int l = 0; l < L_1; l++) {
            dq.addLast(leagues[l].removeMiddle());
        }

        for (int l = 1; l < L; l++) {
            dq.addLast(leagues[l].removeFirst());
        }

        int sum = 0;
        for (int i : dq) sum += i;

        for (int l = 1; l < L; l++) {
            leagues[l].add(dq.removeFirst());

        }
        for (int l = 0; l < L_1; l++) {
            leagues[l].add(dq.removeFirst());
        }

        return sum;
    }

    // League 클래스
    private static class League {
        private TreeSet<Integer> leagueUpper;
        private TreeSet<Integer> leagueLower;
        
        // League의 저장소를 두개로 나누어, 중간값을 찾기 쉽도록 함
        public League(Comparator<Integer> comparator) {
            leagueUpper = new TreeSet<>(comparator);
            leagueLower = new TreeSet<>(comparator);
        }

        // 추가 메서드
        public void add(int id) {
            leagueUpper.add(id); // 무조건 처음에 upper에 저장
            
            // upper 크기가 M/2+1를 넘어가면,
            // upper의 마지막을 lower에 넣음
            if (leagueUpper.size() > M/2 + 1) {
                leagueLower.add(leagueUpper.pollLast());
            }

            // 균형 맞추는 로직 (중간값 꺼내서 윗 리그에 넣을 때 사용)
            // lower가 비어있지 않을 때,
            if (!leagueLower.isEmpty()) {
                int id1 = leagueUpper.last();
                int id2 = leagueLower.first();
                int p1 = mAbility[id1];
                int p2 = mAbility[id2];
                
                // upper의 마지막 id의 Ability와
                // lower의 처음 id의 Ability 비교
                if (p1 < p2) {
                    // 교환
                    leagueLower.add(leagueUpper.pollLast());
                    leagueUpper.add(leagueLower.pollFirst());
                } else if (p1 > p2) {
                    return;
                    
                // 두 Ability 같으면,
                } else {
                    // id 비교
                    if (id1 > id2) {
                        // 교환
                        leagueLower.add(leagueUpper.pollLast());
                        leagueUpper.add(leagueLower.pollFirst());
                    } else if (id2 > id1) {
                        return;
                    }
                }
            }
        }

        public Integer removeFirst() {
            return leagueUpper.pollFirst();
        }

        public Integer removeLast() {
            return leagueLower.pollLast();
        }

        public Integer removeMiddle() {
            return leagueUpper.pollLast();
        }
    }
}