package net.neoremind.java8learning;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static net.neoremind.java8learning.AlbumBuilder.getAlbums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import net.neoremind.java8learning.bo.Album;
import net.neoremind.java8learning.bo.Track;

/**
 * @author zhangxu
 */
public class LamdaTest {

    @Test
    public void testInterfaceDefaultMethod() {
        List<Album> albums = getAlbums();
        for (Album album : albums) {
            album.welcom();
        }
    }

    @Test
    public void testMap1() {
        List<String> res = Stream.of("abc", "xyz", "hh").map(str -> str.toUpperCase()).collect(toList());
        System.out.println(res);

        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API");
        res = features.stream().map(str -> str.toUpperCase()).collect(toList());
        System.out.println(res);
    }

    @Test
    public void testMap() {
        // Convert String to Uppercase and join them using coma
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany",
                "Italy", "U.K.", "Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase())
                .collect(Collectors.joining(", "));
        System.out.println(G7Countries);

        // MethodReference way
        G7Countries = G7.stream().map(String::toLowerCase)
                .collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    @Test
    public void testForEachDoubleColon() {
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API");
        features.forEach(n -> System.out.println(n));

        // Even better use Method reference feature of Java 8
        // method reference is denoted by :: (double colon) operator
        // looks similar to score resolution operator of C++
        features.forEach(System.out::println);
    }

    @Test
    public void testThread() {
        //Before Java 8:
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8 ");
            }
        }).start();

        //Java 8 way:
        new Thread(() -> System.out.println("In Java8")).start();
    }

    @Test
    public void testPredicate() {
        // We can even combine Predicate using and(), or() And xor() logical functions
        // for example to find names, which starts with J and four letters long, you
        // can pass combination of two Predicate
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;

        List<String> names = Arrays.asList("Java", "c++", "Scala", "JDK8");

        names.stream()
                .filter(startsWithJ.and(fourLetterLong))
                .forEach((n) -> System.out.print("\nName which starts with 'J' and four letter long is: " + n));
    }

    @Test
    public void testMapReduce1() {
        int count = Stream.of(1, 2, 3).reduce(10, (acc, element) -> acc + element);
        System.out.println(count);
    }

    @Test
    public void testMapForEach() {
        // applying 12% VAT on each purchase
        // Without lambda expressions:
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            System.out.println(price);
        }

        // With Lambda expression:
        costBeforeTax.stream().map((cost) -> cost + .12 * cost)
                .forEach(System.out::println);
    }

    @Test
    public void testMapReduce2() {
        // Applying 12% VAT on each purchase
        // Old way:
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            total = total + price;

        }
        System.out.println("Total : " + total);

        // New way:
        double bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost)
                .reduce((sum, cost) -> sum + cost)
                .get();
        System.out.println("Total : " + bill);
    }

    @Test
    public void testFilter() {
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API", "X");

        // Create a List with String more than 2 characters
        List<String> filtered = features.stream().filter(x -> x.length() > 2)
                .collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n",
                features, filtered);
    }

    @Test
    public void testCount() {
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API", "X");

        // Create a List with String more than 2 characters
        long count = features.stream().filter(x -> x.length() > 2).count();
        System.out.println(count);
    }

    @Test
    public void testFilterGroupBy() {
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API",
                "Date and Time API", "X", "Lambdas", "Stream API");

        // Create a List with String more than 2 characters
        Map<String, Integer> map = features.stream().filter(x -> x.length() > 2)
                .collect(Collectors.groupingBy(p -> p, Collectors.summingInt(p -> 1)));
        System.out.println(map);
    }

    /**
     * 你可能会觉得在这个例子里，List l被迭代了好多次，map，filter，distinct都分别是一次循环，效率会不好。实际并非如此。这些返回另一个Stream的方法都是“懒（lazy
     * ）”的，而最后返回最终结果的collect方法则是“急（eager）”的。在遇到eager方法之前，lazy的方法不会执行。
     * <p>
     * 除collect外其它的eager操作还有forEach，toArray，reduce等。
     */
    @Test
    public void testDistinct() {
        // Create List of square of all distinct numbers
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map(i -> i * i).distinct()
                .collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    }

    @Test
    public void testSummaryStatistics() {
        //Get count, min, max, sum, and average for numbers
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x)
                .summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }

    @Test
    public void testFunction() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language : ");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> str.length() > 4);
    }

    private static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    @Test
    public void testFindLongTracks() {
        Set<String> longTrackNames = getAlbums().stream()
                .flatMap(album -> album.getTracks().stream())
                .filter(track -> track.getLength() > 60)
                .map(track -> track.getName())
                .limit(2)
                .collect(toSet());
        System.out.println(longTrackNames);
    }

    @Test
    public void testFindLongestTracks() {
        Track longestTrack = getAlbums().stream()
                .flatMap(album -> album.getTracks().stream())
                .max(Comparator.comparing(track -> track.getLength()))
                .get();
        System.out.println(longestTrack);
    }

    @Test
    public void testGenerateRandome() {
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
    }

    @Test
    public void testGenerate() {
        Stream.generate(() -> "hello world")
                .limit(3)
                .forEach(System.out::println);
    }

    @Test
    public void testIterate() {
        Stream.iterate(0, x -> x + 1)
                .limit(5)
                .forEach(System.out::println);
    }

}
