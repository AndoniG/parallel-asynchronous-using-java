package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamExample {

    public List<String> stringToLowerCase(List<String> namesList) {
        return namesList
                .parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public List<String> stringTransform(List<String> namesList) {
        return namesList
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> stringTransform2(List<String> namesList, boolean isParallel) {
        Stream<String> namesStream  = namesList.stream();

        if(isParallel)
            namesStream.parallel();

        return namesStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamExample parallelStreamsExample = new ParallelStreamExample();

        startTimer();

        List<String> resultList = parallelStreamsExample.stringTransform(namesList);

        log("resultList: " + resultList);
        timeTaken();

    }
}
