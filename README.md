# morning-algorithm-study

## 문제 링크
### 1주차
:dart: [색깔 트리](https://www.codetree.ai/training-field/frequent-problems/problems/color-tree)  |  [사물인식 최소 면적 산출 프로그램](https://softeer.ai/practice/6277)

### 2주차
:dart: [코드트리 투어](https://www.codetree.ai/training-field/frequent-problems/problems/codetree-tour/description?page=1&pageSize=5)  |  [Garage game](https://softeer.ai/practice/6276)

## 양식
- 주간 폴더명을 `week01` 형식으로 만들기
- 폴더 내 README파일을 만들고, 문제 및 풀이기간을 명시하기
- 출제 사이트 및 문제 번호로 폴더 만들기 ex) `codetree_연도_상반기/하반기_am/pm_문제번호`, `softeer_문제번호`
> 빈 폴더를 commit하는 방법: 폴더 내에 `.gitkeep` 파일 생성

### 코드 파일명
- `codetree_연도_상반기/하반기_am/pm_문제번호_이름.java`
- `softeer_문제번호_이름.java`

## Branch 관리
- 문제 출제자가 주간 폴더, 문제별 폴더 및 README를 작성하여 Main에 올리기
- **문제를 풀 때에는, Main에 바로 Commit하지 않고, 자기 Branch에서 하기**
- Branch 이름은 다음과 이름 초성-주차로 하기 `mhk-01, ukj-02, iht-03, yjs-04`
- 문제를 다 풀었을 때, Main Branch에 Merge Request 보내기

### How to
1. main branch를 최신으로 업데이트하기
```
git checkout main
git pull
```

2. 새로운 branch를 만들기
```
git branch mhk-01 main
```
- branch 목록 조회 방법: `git branch`
- branch 삭제 방법: `git branch -D mhk-01`

3. 현재 작업하는 branch를 새로 만든 branch로 바꾸기
```
git checkout mhk-01
```

4. 문제 모두 푼 후, 파일 add 및 commit 하기
```
git add .
git commit -m "커밋내용"
```
#### 커밋내용
```
// 출제자
feat: n주차 출제
```
ex) `feat: 1주차 출제`
```
// 문제풀이 제출
태그: 문제번호
```
| 태그       | 설명   |
|------------|--------|
| solved     | 해결   |
| unsolved   | 미해결 |
| fix        | 수정   |

ex) `solved: codetree_2024_1_2`

5. 리모트 레포지토리의 자기 branch로 push하기
```
git push origin mhk-01
```

6. Pull requests - New pull reqest

7. base: main, compare: 작업한 branch로 설정하여, Create pull reqest

8. 내용 간단하게 작성하고 Assignee를 해당 주 문제 출제자로 설정, Milestone을 해당 주차로 설정

> :warning: **Merge가 되면, 리모트 레포지토리에서 해당 branch는 제거됩니다**.






