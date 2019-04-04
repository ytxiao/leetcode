package hash;


import java.util.ArrayList;
import java.util.List;

public class StrClass {


    /**
     * 给定一个字符串，找出不含有重复字符的最长子串的长度。
     示例：
     给定 "abcabcbb" ，没有重复字符的最长子串是 "abc" ，那么长度就是3。
     给定 "bbbbb" ，最长的子串就是 "b" ，长度是1。
     给定 "pwwkew" ，最长子串是 "wke" ，长度是3。请注意答案必须是一个子串，"pwke" 是 子序列  而不是子串。
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        List<Character> chars = new ArrayList();
        int num = 0;
        for (char c:s.toCharArray()) {
            if (chars.contains(c)){
                if (chars.size() > num){
                    num = chars.size();
                }
                for (int i = 0;i<chars.size();i++){
                    if (chars.get(i) == c){
                        chars.remove(i);
                        break;
                    }else {
                        chars.remove(i);
                    }
                    i--;
                }

            }
            chars.add(c);
            if (chars.size() > num){
                num = chars.size();
            }
        }
        return num;
    }

    public static void main(String[] args) {
        int m = lengthOfLongestSubstring("abcdefg");
        System.out.println(m);
    }
}
