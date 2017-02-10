package com.example;

public class MyClass {
    public static void main(String[] args){
        Person<String> person = new Person<String>();
        person.setSex("女汉子");

        Person<Integer> person2 = new Person<Integer>();
        person2.setSex(0);
    }
}
