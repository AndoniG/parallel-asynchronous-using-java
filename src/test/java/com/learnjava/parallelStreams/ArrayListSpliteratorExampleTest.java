package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample;

    @BeforeEach
    void setUp() {
        arrayListSpliteratorExample = new ArrayListSpliteratorExample();
    }


    // This annotation allows us to run the test multiple times and test the performance of our code taking in account the JVM cache.
    @RepeatedTest(5)
    void multiplyEachValue() {
        // given
        int size = 1_000_000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        // when
        List<Integer> resultList= arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        // then
        assertEquals(size, resultList.size());
    }

    // This annotation allows us to run the test multiple times and test the performance of our code taking in account the JVM cache.
    @RepeatedTest(5)
    void multiplyEachValueInParallel() {
        // given
        int size = 1_000_000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);

        // when
        List<Integer> resultList= arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        // then
        assertEquals(size, resultList.size());
    }
}