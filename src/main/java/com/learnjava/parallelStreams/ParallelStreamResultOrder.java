package com.learnjava.parallelStreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {

    public static List<Integer> listOrder(List<Integer> inputList){
        return inputList.parallelStream()
                .map(integer -> integer*2)
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> inputList){
        return inputList.parallelStream()
                .map(integer -> integer*2)
                .collect(Collectors.toSet());
    }


    public static void main(String[] args) {
        List<Integer> inputList = List.of(1,2,3,4,5,6,7,8,9);
        log("inputList: " + inputList);
        List<Integer> result = listOrder(inputList);
        log("result: " + result);

        Set<Integer> inputSet = Set.of(1,2,3,4,5,6,7,8,9);
        log("inputSet: " + inputSet);
        Set<Integer> setResult = setOrder(inputSet);
        log("setResult: " + setResult);
    }
}
