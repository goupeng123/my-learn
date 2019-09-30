package com.my;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by frank.gou on 2019/9/21.
 */
public class Test {

    public static void main(String args[]) {

        Set<String> set = new HashSet<>();
        set.add("abc");
        set.add("acd");
        set.add("bcd");
        set.add("dca");
        set.add("acd");
        System.out.println(set.size());
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
