package net.neoremind.java8learning;

import org.junit.Test;

import net.neoremind.java8learning.able.Transformer;
import net.neoremind.java8learning.bo.Track;

/**
 * @author zhangxu
 */
public class FunctionalInterfaceTest {

    @Test
    public void testFunctionalInterface() {
        Track track = new Track("abc", 123);
        Transformer<Track, String> transformer = t -> t.getName();
        String res = to(transformer, track);
        System.out.println(res);
    }

    public <F, T> T to(Transformer<F, T> valuable, F f) {
        return valuable.convert(f);
    }

}
