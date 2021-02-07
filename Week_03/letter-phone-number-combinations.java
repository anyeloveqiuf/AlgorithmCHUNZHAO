// 电话号码的字母组合

import java.util.*;

class letter-phone-number-combinations {
    HashMap<String, String> hash = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
        }};
    List<String> ans = new ArrayList<>();

    public List<String> letter-phone-number-combinations(String digits) {
        if (digits.length != 0) {
            backtrack("", digits);
        }
        return ans;
    }
    public void backtrack(String combination, String next_digits) {
        if (next_digits.length() == 0) {
            ans.add(combination);
            return;
        } else {
            String digit = next_digits.substring(0, 1);
            String letters = hash.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                String letter = letters.substring(i, i + 1);
                backtrack(combination + letter, next_digits.substring(1));
            }
        }
    }
}
