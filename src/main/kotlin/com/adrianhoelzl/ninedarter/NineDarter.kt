package com.adrianhoelzl.ninedarter

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
 * The points to score in a single leg.
 */
const val POINTS_TO_SCORE = 501


/**
 * Determine all nine-darter runs.
 *
 * @return a flow of all nine-darter runs
 */
@FlowPreview
fun determineAllNineDarterRuns(): Flow<DartsRun> = determineAllNineDarterRunsWithPrefix(emptyDartsRun())


/**
 * Determine all nine-darter runs that start with the given [dartsRun].
 *
 * @param dartsRun the darts run used as a prefix
 * @return a flow of all nine-darter runs starting with the [dartsRun]
 */
@FlowPreview
private fun determineAllNineDarterRunsWithPrefix(dartsRun: DartsRun): Flow<DartsRun> {
	// emit the run, if it is a nine-darter
	if (dartsRun.isNineDarter()) {
		return flowOf(dartsRun)
	}

	// cancel the calculation if no nine-darter can be reached with the current prefix
	if (!dartsRun.isPotentialRealPrefixOfNineDarterRun()) {
		return emptyFlow()
	}

	// otherwise, descend one level into the tree
	val childrenRuns = ALL_FIELDS.map { dartsRun + it }
	return childrenRuns.asFlow().flatMapMerge {
		determineAllNineDarterRunsWithPrefix(it)
	}
}