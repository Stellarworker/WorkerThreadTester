package com.stellarworker.workerthreadtester.workerthread

private const val ZERO_LONG = 0L
private const val OPERATION_CLEAR = 0
private const val OPERATION_ADD = 1
private const val ERROR_MESSAGE = "Invalid operation type!"

data class Task(
    val code: () -> Unit,
    var done: Boolean = false
)

class WorkerThread {

    private var exitFlag = true
    private val taskList = mutableListOf<Task>()

    fun start() {
        exitFlag = false
        Thread {
            runInsideThread()
        }.start()
    }

    fun stop() {
        exitFlag = true
    }

    fun post(delay: Long = ZERO_LONG, code: () -> Unit) {
        if (!exitFlag) {
            if (delay > ZERO_LONG) {
                modifyList(OPERATION_ADD) { Thread.sleep(delay) }
            }
            modifyList(OPERATION_ADD, code)
        }
    }

    private fun runInsideThread() {
        while (true) {
            taskList.forEach { task ->
                if (!task.done) {
                    task.code.invoke()
                    task.done = true
                }
            }
            if (exitFlag) break
        }
        modifyList(OPERATION_CLEAR)
    }

    @Synchronized
    private fun modifyList(operationType: Int, code: () -> Unit = {}) {
        when (operationType) {
            OPERATION_CLEAR -> taskList.clear()
            OPERATION_ADD -> taskList.add(Task(code, false))
            else -> throw IllegalArgumentException(ERROR_MESSAGE)
        }
    }
}