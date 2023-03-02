package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamExampleTest {

    ParallelStreamExample parallelStreamExample = new ParallelStreamExample();

    @Test
    void stringTransform() {
        // given
        List<String> inputList = DataSet.namesList();

        // when
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform(inputList);
        timeTaken();

        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> {
            assertTrue(name.contains("-"));
        });
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void stringTransform2IsParallel(boolean isParallel) {
        // given
        List<String> inputList = DataSet.namesList();

        // when
        startTimer();
        List<String> resultList = parallelStreamExample.stringTransform2(inputList, isParallel);
        timeTaken();

        //then
        assertEquals(4, resultList.size());
        resultList.forEach(name -> {
            assertTrue(name.contains("-"));
        });
    }

    @Test
    void stringToLowerCase() {
        // given
        List<String> inputList = DataSet.namesList();
        List<String> resultList = new ArrayList<>(Arrays.asList("bob", "jamie", "jill", "rick"));

        // when
        startTimer();
        List<String> testList = parallelStreamExample.stringToLowerCase(inputList);
        timeTaken();

        //then
        assertEquals(4, testList.size());
        assertEquals(resultList, testList);
    }
}