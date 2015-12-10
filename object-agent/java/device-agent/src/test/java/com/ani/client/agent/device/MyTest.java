package com.ani.client.agent.device;

import junit.framework.TestCase;

import java.lang.reflect.Array;

/**
 * Created by huangbin on 12/7/15.
 */
public class MyTest extends TestCase {
    public void test() throws Exception {
        Integer[][] intArray = new Integer[10][20];
        showObject(intArray);
    }

    private void showObject(Object instance) throws ClassNotFoundException {
        Class cls = instance.getClass();
        String name = cls.getName();

        Integer [] aa = new Integer[0];
         aa[0] = 10;
        cls = Class.forName(name);
        Object value = Array.newInstance(cls.getComponentType(), 10);
        showObject(value);
        System.out.println(cls.getSimpleName());

    }
}
