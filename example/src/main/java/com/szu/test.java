package com.szu;/*
 * @Author 郭学胤
 * @University 深圳大学
 * @Description
 * @Date 2021/3/7 21:07
 */

import java.lang.ref.WeakReference;

public class test extends WeakReference<WeakTest> {

    public test(WeakTest referent) {
        super(referent);
    }

    public static void main(String[] args) throws InterruptedException {
        WeakTest weakTest = new WeakTest();
        test test = new test(weakTest);
        System.out.println(weakTest);
        weakTest = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println(weakTest);
        System.out.println(test);
    }
}
class WeakTest{

}
