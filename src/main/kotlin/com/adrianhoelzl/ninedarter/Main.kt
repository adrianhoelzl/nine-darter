package com.adrianhoelzl.ninedarter

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.runBlocking


@FlowPreview
fun main() {
	val allNineDarterRuns = determineAllNineDarterRuns()
	runBlocking {
		allNineDarterRuns.collectIndexed { index, value ->
			println("${index + 1}: $value")
		}
	}
}