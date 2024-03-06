package com.lukaslechner.coroutineusecasesonandroid

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// flowOn can be used to run heavy operations on a background thread, while collecting the results on the main thread:
//fun main() = runBlocking<Unit> {
//    flow {
//        for(i in 1..3) {
//            // lets do something useful here
//            delay(100)
//            emit(i)
//        }
//    }.map {value ->
//        println("This is map thread ${Thread.currentThread().name}")
//        value * value
//    }.flowOn(Dispatchers.Default)
//        .collect {value ->
//            println("This received value ${value}")
//            println("This collect using ${Thread.currentThread().name}")
//        }
//}


// shared flow example -------------
//Hot flows, on the other hand, emit data independently of the presence of subscribers. They are useful when you want to broadcast updates to multiple subscribers, and you're okay with subscribers missing out on previous emissions if they weren't collected in time

//fun CoroutineScope.gettingHotFlow() : SharedFlow<Int> {
//    val sharedFlow = MutableSharedFlow<Int>(replay = 4)
//
//   launch {
//        for(i in 1..6) {
//            delay(250)
//            println("Emitted $i")
//            sharedFlow.emit(i)
//
//        }
//    }
//    return sharedFlow
//}


// fun main() = runBlocking<Unit> {
//    val sharedFlow = gettingHotFlow()
//     launch {
//         sharedFlow.collect { value ->
//             println("Collector 1 received: $value")
//         }
//     }
//
//
//     launch {
//         delay(4000)
//         sharedFlow.collect { value ->
//             println("Collector 2 received $value")
//         }
//     }
//
//     delay(1000)
//}

//Cold flows are those that do not start emitting data until there is a subscriber.

//fun getColdFLow() : Flow<Int> = flow {
//
//    for(i in 1..3) {
//        emit(i)
//    }
//}
//
//fun main() = runBlocking {
//    val coldFlow = getColdFLow()
//    coldFlow.collect {value ->
//        println("collected value $value")
//    }
//    delay(5000)
//    coldFlow.collect {
//        println("collector 2 collected value $it")
//    }
//}


//=========== fun state flow example

//fun main() = runBlocking<Unit> {
//
//    val stateFlow = MutableStateFlow("Initial flow")
//
//    launch {
//        stateFlow.value = "New value"
//    }
//
//    stateFlow.collect {
//        println("the value is $it")
//    }
//}


//fun main() = runBlocking {
//
//    val flow1 = flowOf(1,2,3,4).onEach { delay(100) }
//    val flow2 = flowOf("A","B","C","D").onEach { delay(100) }
//
//    val mergeFlow = merge(flow1, flow2)
//
//    mergeFlow.collect {
//        println("Merged flow $it")
//    }
//}

//fun main() = runBlocking {
//    val flow = flowOf(flowOf(1,2,3), flowOf("A","B","C"))
//
//    val flatMapMergeFlow  = flow.flatMapMerge { it }
//
//    flatMapMergeFlow.collect {
//        println("The value of $it")
//    }
//}

//============= flatMapMerge
//suspend fun performFlatMapMerge() {
//    val parentFlow = flowOf(flowOf(1, 2, 3), flowOf("A", "B", "C"))
//
//    parentFlow.flatMapMerge { it }
//        .collect { value ->
//            println("FlatMapMerge: $value at ${System.currentTimeMillis()}")
//        }
//}
//
//fun main() = runBlocking<Unit> {
//    performFlatMapMerge()
//}

//================== stateInOperator

//fun main() = runBlocking<Unit> {
//
//    val coldFlow = flow {
//        emit("Hello")
//        delay(1000)
//        emit("World")
//    }
//
//    val hotFlow = coldFlow.shareIn(this, SharingStarted.Lazily, 5)
//
//    launch {
//        hotFlow.collect {
//            println("Collect 1 $it")
//        }
//    }
//
//    launch {
//        delay(8000)
//        hotFlow.collect {
//            println("Collect 2 $it")
//        }
//    }
//}

fun main() = runBlocking<Unit> {

    val coldFlow = flow {
        emit(1)
        delay(1000)
        emit(2)
    }

    val stateInFlow = coldFlow.stateIn(this, SharingStarted.WhileSubscribed(), 0)

   val job = launch {
        stateInFlow.collect {
            println(it)
        }
    }

    delay(1500)
    println("The latest value in state flow ${stateInFlow.value}")
    job.cancel()
}



//fun main() = runBlocking {
//    val coldFlow = flow {
//        emit(1)
//        delay(1000)
//        emit(2)
//    }
//
//    val stateFlow = coldFlow.stateIn(
//        scope = this,
//        started = SharingStarted.WhileSubscribed(),
//        initialValue = 0
//    )
//
//    println("Initial value: ${stateFlow.value}")
//
//    val job = launch {
//        stateFlow.collect { value ->
//            println("Collector received: $value")
//        }
//    }
//
//    delay(1500) // Wait for flow to emit values
//    println("Latest value after collection: ${stateFlow.value}")
//    job.cancel()
//}
