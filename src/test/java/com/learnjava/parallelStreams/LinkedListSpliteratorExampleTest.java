package com.learnjava.parallelStreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample;

    @BeforeEach
    void setUp() {
        linkedListSpliteratorExample = new LinkedListSpliteratorExample();
    }

    // Working with parallel streams in LinkedList it's really slow. Invoking parallelStream() does not guarantee faster
    // performance of your code as it needs to perform additional steps compared to sequential.
    @RepeatedTest(5)
    void multiplyEachValue() {
        // given
        int size = 1_000_000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        // when
        List<Integer> resultList= linkedListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        // then
        assertEquals(size, resultList.size());
    }

    @RepeatedTest(5)
    void multiplyEachValueInParallel() {
        // given
        int size = 1_000_000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);

        // when
        List<Integer> resultList= linkedListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        // then
        assertEquals(size, resultList.size());
    }
}