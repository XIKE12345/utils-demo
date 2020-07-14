package com.jieyun.test.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huike
 */
public class CollectionsMap {

    public static void main(String[] args) {
        // initMemberList为获取数据的方法
        List<Member> list = CollectionsMap.initMemberList();
        Map<String, String> memberMap = list.stream().collect(HashMap::new, (m, v) ->
                m.put(v.getId(), v.getImgPath()), HashMap::putAll);
        System.out.println(memberMap);
    }

    public static List<Member> initMemberList() {

        Member member1 = new Member();
        member1.setId("id_1");
        member1.setImgPath("http://www.baidu.com");

        // 这里有一个null导致的
        Member member2 = new Member();
        member2.setId("id_2");
        member2.setImgPath(null);

        List<Member> list = new ArrayList<>();
        list.add(member1);
        list.add(member2);
        return list;
    }
}


