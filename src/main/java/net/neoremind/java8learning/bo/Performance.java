package net.neoremind.java8learning.bo;

public interface Performance {

    default void welcom() {
        System.out.println("hello!");
    }

}
