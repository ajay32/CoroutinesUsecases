package com.lukaslechner.coroutineusecasesonandroid.flowTestingbyme

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Test


class FlowTestClass{

    @Test
    fun testFlowEmissions() = runBlockingTest {

        val flow = flowOf(1,2,3,4,5)

        val result = flow.toList()

        assertEquals(listOf(1,2,3,4,5), result)

    }

    @Test
     fun timeTestingFlow()  = runBlockingTest { // like testing delay

      //  val textCoroutineDispatched = TestCoroutineDispatcher()

      //  withContext(textCoroutineDispatched) {
            val flow = flow {
                delay(2000)
                emit(20)
            }


            advanceTimeBy(2000)
            val result = flow.first()

            assertEquals(20, result)

        }





}

