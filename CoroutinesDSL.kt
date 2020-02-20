fun startJob(
        parentScope: CoroutineScope,
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
) {
    parentScope.launch(coroutineContext) {
        supervisorScope {
            block()
        }
    }
}

suspend fun <T> startTask(
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> T
): T {
    return withContext(coroutineContext) {
        return@withContext block()
    }
}

fun <T> startTaskAsync(
        parentScope: CoroutineScope,
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return parentScope.async(coroutineContext) {
        return@async supervisorScope {
            return@supervisorScope block()
        }
    }
}

fun CoroutineScope.uiJob(block: suspend CoroutineScope.() -> Unit) {
    startJob(this, Dispatchers.Main, block)
}

fun CoroutineScope.backgroundJob(block: suspend CoroutineScope.() -> Unit) {
    startJob(this, Dispatchers.Default, block)
}

fun CoroutineScope.ioJob(block: suspend CoroutineScope.() -> Unit) {
    startJob(this, Dispatchers.IO, block)
}

suspend fun <T> uiTask(block: suspend CoroutineScope.() -> T): T {
    return startTask(Dispatchers.Main, block)
}

suspend fun <T> backgroundTask(block: suspend CoroutineScope.() -> T): T {
    return startTask(Dispatchers.Default, block)
}

suspend fun <T> ioTask(block: suspend CoroutineScope.() -> T): T {
    return startTask(Dispatchers.IO, block)
}

fun <T> CoroutineScope.uiTaskAsync(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return startTaskAsync(this, Dispatchers.Main, block)
}

fun <T> CoroutineScope.backgroundTaskAsync(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return startTaskAsync(this, Dispatchers.Default, block)
}

fun <T> CoroutineScope.ioTaskAsync(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return startTaskAsync(this, Dispatchers.IO, block)
}