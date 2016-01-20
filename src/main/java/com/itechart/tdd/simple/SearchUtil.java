package com.itechart.tdd.simple;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SearchUtil {

    public boolean contains(List<String> list, String subStr) {
        if (list == null || list.isEmpty()) {
            return false;
        }

        if (StringUtils.isBlank(subStr)) {
            return false;
        }

        for (String str : list) {
            if (str.contains(subStr)) {
                return true;
            }
        }

        return false;
    }
}
