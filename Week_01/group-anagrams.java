// 字母异位词分组
import java.util.*;

class group-anagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str: strs) {
            char[] ch = str.toCharArray();
            Arrays.sort(ch);
            String s = "";
            for (Character c: ch) {
                s += c;
            }
            String key = new String(s);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());
    }
}