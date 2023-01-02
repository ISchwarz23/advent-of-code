package aoc2022.day21

object Day21 {

    fun part1(monkeyNameToOperationStr: Map<String, String>): Long {
        return parseJob(monkeyNameToOperationStr["root"]!!, monkeyNameToOperationStr).value!!
    }

    fun part2(monkeyNameToOperationStr: Map<String, String>): Long {
        val monkeys = monkeyNameToOperationStr.toMutableMap()

        // override "root" operation (x == y, if x - y == 0)
        val rootParts = monkeys["root"]!!.split(" ")
        monkeys["root"] = "${rootParts[0]} - ${rootParts[2]}"

        // set "humn" operation as variable
        monkeys["humn"] = Job.YellVariableNumber.toString()

        // resolve "humn"
        val rootJob = parseJob(monkeys["root"]!!, monkeys)
        return resolveVariable(0, rootJob)
    }
}

private fun resolveVariable(operationResult: Long, job: Job): Long {
    if (job is Job.YellNumber) return job.value
    if (job is Job.YellVariableNumber) return operationResult

    val mathOperationJob = job as Job.YellMathOperationResult
    return if (mathOperationJob.first.containsVariable) {
        val newResult =
            mathOperationJob.operation.inverseOperation.execute(operationResult, mathOperationJob.second.value!!)
        resolveVariable(newResult, mathOperationJob.first)
    } else {
        if (mathOperationJob.operation.isAssociative) {
            val newResult =
                mathOperationJob.operation.inverseOperation.execute(operationResult, mathOperationJob.first.value!!)
            resolveVariable(newResult, mathOperationJob.second)
        } else {
            val newResult = mathOperationJob.operation.execute(mathOperationJob.first.value!!, operationResult)
            resolveVariable(newResult, mathOperationJob.second)
        }
    }
}

private fun parseJob(job: String, monkeys: Map<String, String>): Job {
    val jobParts = job.split(" ")
    if (jobParts.size == 1) {
        return if (jobParts[0] == Job.YellVariableNumber.toString())
            Job.YellVariableNumber
        else
            Job.YellNumber(jobParts[0].toLong())
    }
    if (jobParts.size == 3) {
        val firstJob = parseJob(monkeys[jobParts[0]]!!, monkeys)
        val operation = jobParts[1].toMathOperation()
        val secondJob = parseJob(monkeys[jobParts[2]]!!, monkeys)
        return Job.YellMathOperationResult(firstJob, secondJob, operation)
    }
    error("Unknown job format '$job'")
}


enum class MathOperation(
    val execute: (first: Long, second: Long) -> Long,
    val stringRepresentation: String,
    val isAssociative: Boolean
) {

    ADD({ first: Long, second: Long -> first + second }, "+", true),
    SUBTRACT({ first: Long, second: Long -> first - second }, "-", false),
    MULTIPLY({ first: Long, second: Long -> first * second }, "*", true),
    DIVIDE({ first: Long, second: Long -> first / second }, "/", false);

    val inverseOperation: MathOperation
        get() {
            return when (this) {
                ADD -> SUBTRACT
                SUBTRACT -> ADD
                MULTIPLY -> DIVIDE
                DIVIDE -> MULTIPLY
            }
        }
}

fun String.toMathOperation(): MathOperation {
    return when (this) {
        MathOperation.ADD.stringRepresentation -> MathOperation.ADD
        MathOperation.SUBTRACT.stringRepresentation -> MathOperation.SUBTRACT
        MathOperation.MULTIPLY.stringRepresentation -> MathOperation.MULTIPLY
        MathOperation.DIVIDE.stringRepresentation -> MathOperation.DIVIDE
        else -> error("Unknown operation '$this'")
    }
}

sealed interface Job {

    val value: Long?
    val containsVariable: Boolean

    object YellVariableNumber : Job {

        override val value: Long? = null
        override val containsVariable: Boolean = true

        override fun toString(): String {
            return "?"
        }
    }

    class YellNumber(override val value: Long) : Job {

        override val containsVariable: Boolean = false

        override fun toString(): String {
            return "$value"
        }
    }

    class YellMathOperationResult(val first: Job, val second: Job, val operation: MathOperation) : Job {

        override val containsVariable: Boolean by lazy { value == null }

        override val value: Long? by lazy {
            if (first.value != null && second.value != null)
                operation.execute(first.value!!, second.value!!)
            else
                null
        }

        override fun toString(): String {
            return "($first ${operation.stringRepresentation} $second)"
        }
    }

}