package com.itechart.tdd.simple;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchUtilTest {

    SearchUtil searchUtil = new SearchUtil();
    List<String> list;

    @Before
    public void initList() {
        list = new ArrayList<>();
        list.add("1234567");
        list.add("123qwe");
        list.add("qwerty");
        list.add("qwe12345");
        list.add("_123qwe");
    }

    @Test
    public void shouldBeContained() {
        assertTrue(searchUtil.contains(list, "qwerty"));
    }

    @Test
    public void shouldBeNotContained() {
        assertFalse(searchUtil.contains(list, "cтрока"));
    }

    @Test
    public void ifListIsNull() {
        assertFalse(searchUtil.contains(null, "123"));
    }

    @Test
    public void ifSubStrIsNull() {
        assertFalse(searchUtil.contains(list, null));
    }

}