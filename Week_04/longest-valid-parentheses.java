// 最长有效括号
import java.util.*;

class longest-valid-parentheses {
    public int longestValidParentheses(String s) {
        int left = 0, right = 0, len = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                len = Math.max(len, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                len = Math.max(len, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }
        return len;
    }
}