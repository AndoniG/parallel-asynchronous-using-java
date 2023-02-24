package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class StreamExample {

    public List<String> stringTransform(List<String> namesList) {
        return namesList
                .stream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        StreamExample parallelStreamsExample = new StreamExample();

        startTimer();

        List<String> resultList = parallelStreamsExample.stringTransform(namesList);

        log("resultList: " + resultList);
        timeTaken();

    }
}
