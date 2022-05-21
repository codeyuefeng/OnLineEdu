package com.atguigu.demo;

public class Student {
    private String name;
    private Integer age;

    public Student(){}
    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    private void say()
    {
        System.out.println(name);
    }
}
