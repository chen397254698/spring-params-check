package test;

import com.chen.restful.advice.CheckParamsAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chen
 * @Date: 2019/2/28 14:22
 */
public class Test {


    public static void main(String[] args) {
        CheckParamsAdvice advice = new CheckParamsAdvice();
        boolean b = advice.checkRange("(, )", 110);

        System.out.println(b);

//        String rangePattern = "^([\\\\[\\\\)])([0-9]*|[\\\\*]?),([0-9]*|[\\*]?)([\\\\]\\)])$";
//        String rangePattern = "[\\(\\[]";
//
//        Pattern pattern = Pattern.compile(rangePattern);
//        Matcher matcher = pattern.matcher("(");
//        boolean matches = matcher.matches();
//        System.out.println(matches);
    }

}
