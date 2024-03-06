package com.lukaslechner.coroutineusecasesonandroid

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.ArithmeticException
import java.lang.Exception

// run blocking is a bridge between sync and async world .. it help to complete all coroutnes inside it

//fun main() = runBlocking<Unit> {
//    launch {
//        println("1")
//        delay(8000)
//    }
//
//    launch {
//        println("2")
//        delay(6000)
//    }
//
//    launch {
//        println("3")
//    }
//}


//================================= Parallel execution & results need to be combined =========
//fun main() = runBlocking {
//
//    val defferedResult1 = async {//
//        println("1")
//        "Result1"
//    }
//
//    val defferedResult2 = async {
//        println("2")
//        "Result2"
//    }
//
//    println("The two result are ${defferedResult1.await()} and ${defferedResult2.await()}")
//}

//========================= Structured concurrency

//fun main() = runBlocking<Unit> { // this coroutneScope does not retuned until all its coroutines launced compeled
//
//    launch {
//        delay(1000L)
//        println("Task in Inherited scope")
//    }
//
//    coroutineScope { // create another coroutines scope that does not return untill all its corountes launced completed
//
//        launch {
//            delay(1500L)
//            println("Task in nested Scope")
//        }
//    }
//    println("Waiting for all task to finish")
//}

// ================================== Concurrent process ==============

//fun main() = runBlocking<Unit> {
//    // all three will run independent  t=0 all three will start, at 800 Task2 done and then after 200 more Task1 then after 1000 Task3
//
//    // start in sequence Launch1 start hit delay(non blocking) -> so Launch2 start hit delay(non blocking) -> Launch 3 start
//    // delay of launch 2 completed first print launch2 then delay of Launch1 complete println launch1
//    launch {
//        delay(1000L)
//        println("Task1")
//    }
//
//    launch {
//        delay(800L)
//        println("Task2")
//    }
//
//    launch {
//        delay(2000L)
//        println("Task3")
//    }
//}

//==================================== Sequential exceution =======

//fun main() = runBlocking {
//
//    val startTime = System.currentTimeMillis()
//
//    val result1 = async { task(" Task 1", 200L) }.await() // await so it will wait task to complete before moving to next
//    val result2 = async { task(" Task 2", 300L) }.await()
//
//    val totalTime = System.currentTimeMillis() - startTime
//
//    println("The result is $result1 and $result2 completed in time $totalTime ms")
//}
//
//suspend fun task(name: String, time : Long) : String {
//    delay(time)
//    return "$name completed"
//}

//============ concurrent Execution ==========

//fun main() = runBlocking {
//
//    val startTime = System.currentTimeMillis()
//
//    val result1 = async { task(" Task 1", 200L) } //
//    val result2 = async { task(" Task 2", 300L) }
//
//    val totalTime = System.currentTimeMillis() - startTime
//
//    println("The result is ${result1.await()} and ${result2.await()} completed in time $totalTime ms")
//}
//
//suspend fun task(name: String, time : Long) : String {
//    delay(time)
//    return "$name completed"
//}


//================== structured scope ========

// first both child scopes complete then parent scope will execute

//fun main() = runBlocking {
//
//    coroutineScope {
//        launch {
//            println("this is child scope1")
//        }
//        launch {
//            println("this is child scope2")
//        }
//    }
//    println("This is parent scope")
//}

//============== dispatched swithc

//fun main() = runBlocking {
//
//    val supervisorScope = CoroutineScope(Dispatchers.Default + SupervisorJob()) // it will not affect the second child
//    supervisorScope.launch {
//
//        println("First child started")
//        delay(1000)
//        println("First child is going to fail")
//        throw ArithmeticException("something went wrong")
//    }
//
//    supervisorScope.launch {
//        try {
//            println("Second child started")
//            delay(2000)
//            print("Second child successfully completed")
//        }catch (e: Exception) {
//            println("got the exception ${e.message}")
//        }
//    }
//
//    supervisorScope.coroutineContext[Job]?.join()
//    println("Parent is completed")
//}

//fun main() =  runBlocking<Unit> {
//    launch(Dispatchers.Main) { // its not  Android no main enviroment
//        println("This is running on ${Thread.currentThread().name}")
//    }
//
//    launch(Dispatchers.IO) {
//        println("This is running on ${Thread.currentThread().name}")
//    }
//
//    launch(Dispatchers.Default) {
//        println("This is running on ${Thread.currentThread().name}")
//    }
//}

//============= Unconfined ====================

//fun main() = runBlocking<Unit> {
//    launch(Dispatchers.Unconfined) {
//        println("before delay ${Thread.currentThread().name}")
//        delay(1000L)
//        println("after delay1 ${Thread.currentThread().name}")
//
//    }
//
//    launch() {
//        println("before delay ${Thread.currentThread().name}")
//        delay(1000L)
//        println("after delay ${Thread.currentThread().name}")
//    }
//}