package com.learnjava.parallelStreams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class ParallelStreamPerformance {

    public int sumUsingIntStream(int count, boolean isParallel) {
        startTimer();

        IntStream intStream = IntStream.rangeClosed(0, count);

        if (isParallel)
            intStream.parallel();

        int sum = intStream.sum();

        timeTaken();

        return sum;
    }

    public int sumUsingList(List<Integer> inputList, boolean isParallel) {
        startTimer();

        Stream<Integer> intStream = inputList.stream();

        if (isParallel)
            intStream.parallel();

        int sum = intStream
                .mapToInt(Integer::intValue)
                .sum();

        timeTaken();

        return sum;
    }

    public int sumUsingIterate(int n, boolean isParallel) {
        startTimer();

        Stream<Integer> intStream = Stream.iterate(0, i -> i + 1);

        if (isParallel)
            intStream.parallel();

        int sum = intStream
                .limit(n + 1) // To include the end value too
                .reduce(0, Integer::sum);

        timeTaken();

        return sum;
    }
}
