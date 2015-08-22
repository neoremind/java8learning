package net.neoremind.java8learning.able;

/**
 * @author zhangxu
 */
@FunctionalInterface
public interface Transformer<F, T> {

    T convert(F f);

    // T convert2(F f);  // javac won't allow this to happen

}
