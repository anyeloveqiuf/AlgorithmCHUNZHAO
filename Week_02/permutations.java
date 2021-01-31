// 全排列
import java.util.*;

class permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList();
        ArrayList<Integer> output = new ArrayList();
        for(int num: nums) {
            output.add(num);
        }
        backtrack(nums.length, output, res, 0);
        return res;
    }
    public void backtrack(int n, ArrayList<Integer> output, List<List<Integer>> res, int start){
        if (start == n) {
            res.add(new ArrayList<Integer>(output));
            return;
        }
        for (int i = start; i < n; i++) {
            Collections.swap(output, i, start);
            backtrack(n, output, res, start + 1);
            Collections.swap(output, i, start);
        }
    }
}