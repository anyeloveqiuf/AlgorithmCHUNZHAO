// 括号生成
import java.util.*;

class generate-parentheses {
    List<String> res = new ArrayList<String>();
    public List<String> generateParenthesis(int n) {
        if (n <= 0) return res;
        getParenthesis("", n, n);
        return res;
    }
    private void getParenthesis(String str, int left, int right) {
        if (left == 0 && right == 0) {
            res.add(str);
            return;
        }
        if (left == right) {
            getParenthesis(str + "(", left - 1, right);
        } else if (left < right) {
            getParenthesis(str + ")", left, right - 1);
            if (left > 0) {
                getParenthesis(str + "(", left - 1, right);
            }
        }
    }
}