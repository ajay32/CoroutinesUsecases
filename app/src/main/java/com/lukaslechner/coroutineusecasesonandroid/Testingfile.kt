package com.lukaslechner.coroutineusecasesonandroid


import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//fun simpleFlow() : Flow<Int> = flow {
//    for(i in 1..4) {
//        delay(1000)
//        emit(i)
//    }
//}
//
//fun main() = runBlocking<Unit> {
//
//    launch {
//        simpleFlow().collect { value ->
//            println(value)
//        }
//    }
//
//}
// using map to modify the values
//fun numberFlow(): Flow<Int> = flow {
//    emit(1)
//    emit(2)
//    emit(3)
//}
//
//fun main() = runBlocking {
//
//    numberFlow().map { value ->
//        "Number $value"
//    }.collect { value->
//        println(value)
//    }
//
//}

// zip operator of flow .. zip is lambda fun it does not collect the data it transform them

//fun flow1() : Flow<String> = flowOf("One","Two","Three")
//fun flow2(): Flow<Int> = flowOf(1,2,3)
//
//fun main() = runBlocking<Unit> {
//
//    flow1().zip(flow2()) { a, b ->
//      "$a $b"
//    }.collect { value ->
//        println(value)
//
//    }
//}

// flow complete and exception catch

//fun flowOfData(): Flow<Int> = flow {
//    emit(1)
//    emit(2)
//    emit(3)
//    throw Exception()
//}
//
//fun main() = runBlocking {
//    flowOfData()
//        .onCompletion { cause ->
//            if(cause != null) println("got the exception $cause")
//            else println("Flow completed successfully")
//        }
//        .catch { e -> println("The exception $e") }
//        .collect {
//        println(it)
//    }
//}


// you can emit value from catch block in flow .. Upstream flow in Flow

//fun faultyFlow(): Flow<Int> = flow {
//    emit(1)
//    throw Exception("Faulty code")
//}
//
//fun main() =  runBlocking {
//    faultyFlow()
//        .catch {
//            emit(-1)
//        }
//        .collect {
//            println(it)
//        }
//}


// transform operator can transform the value like it will change 1 into 10 , 2 into 20

//fun transformFlow(): Flow<Int> = flow {
//    emit(2)
//    emit(3)
//}
//
//fun main() = runBlocking {// printing table of 2,3 using transform
//    transformFlow()
//
//        .transform { value ->
//            emit(value * 1)
//            emit(value * 2)
//            emit(value * 3)
//            emit(value * 4)
//            emit(value * 5)
//            emit(value * 6)
//            emit(value * 7)
//            emit(value * 8)
//            emit(value * 9)
//            emit(value * 10)
//        }
//        .collect { value ->
//            println(value)
//        }
//}


// combine operator to combine to flows



import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.flow.internal.combineInternal
//import kotlinx.coroutines.flow.internal.unsafeFlow
import kotlinx.coroutines.runBlocking
//
//fun main() = runBlocking {
//    flow {
//        for (i in 1..3) {
//            delay(100) // pretend we are asynchronously waiting 100ms
//            emit(i)
//        }
//    } //.buffer() // buffer emissions, don't wait for collect to finish
//        .collect { value ->
//            delay(300) // pretend we are doing something slow
//            println(value)
//        }
//}

//@JvmName("flowCombine")
//public fun <T1, T2, R> Flow<T1>.combine(flow: Flow<T2>, transform: suspend (a: T1, b: T2) -> R): Flow<R> = unsafeFlow {
//    combineInternal(arrayOf(this@combine, flow), nullArrayFactory(), { emit(transform(it[0] as T1, it[1] as T2)) })
//}

// lets try combine operator
//fun main() = runBlocking {
//    val flow1 = flowOf("A", "B", "C")
//    val flow2 = flowOf(1,2,3)
//
//    flow1.combine(flow2) { a, b ->
//        "$a$b"
//    }.collect {
//        println(it)
//    }
//}

//=========== flatMapConcat

//fun requestFlow(t: Int) : Flow<String> = flow {
//        emit("$t First")
//}
//
//fun main() = runBlocking {
//    val flow1 = flowOf(1,2,3)
//    flow1.flatMapConcat {
//        requestFlow(it)
//    }.collect {
//        println(it)
//    }
//}

//================= buffer example storage to use if collector is slow to collect values

//fun main() = runBlocking {
//    flow {
//        for(i in 1..9) {
//            delay(100)
//            emit(i)
//        }
//    } .buffer() // temp storage
//        .collect {
//            delay(800)
//            println(it)
//        }
//}


//============================

//fun main() = runBlocking<Unit> {
//
//    val sharedFlow = MutableSharedFlow<Int>()(replay = 1)
//
//    launch {
//        sharedFlow.emit(1)
//        delay(100)
//        sharedFlow.emit(2)
//    }
//
//    launch {
//        sharedFlow.collect {value ->
//            println("value of sharedflow $value")
//        }
//    }
//}