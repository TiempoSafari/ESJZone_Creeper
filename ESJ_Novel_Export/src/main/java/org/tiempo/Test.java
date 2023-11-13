package org.tiempo;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class Test {
    public static void main(String[] args) {
        String original = "生命不息，奮鬥不止abcd";
        String result = ZhConverterUtil.toSimple(original);

        System.out.println(result);

    }
}
