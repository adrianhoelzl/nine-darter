import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

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
private fun determineAllNineDarterRuns(): Flow<DartsRun> = determineAllNineDarterRunsWithPrefix(emptyDartsRun())


/**
 * Determine all nine-darter runs that start with the given [dartsRun].
 *
 * @param dartsRun the darts run used as a prefix
 * @return a flow of all nine-darter runs starting with the [dartsRun]
 */
@FlowPreview
private fun determineAllNineDarterRunsWithPrefix(dartsRun: DartsRun): Flow<DartsRun> = flow {
	// emit the run, if it is a nine-darter
	if (dartsRun.isNineDarter()) {
		emit(dartsRun)
		return@flow
	}

	// cancel the calculation if no nine-darter can be reached with the current prefix
	if (!dartsRun.isPotentialRealPrefixOfNineDarterRun()) {
		return@flow
	}

	// otherwise, descend one level into the tree
	val childrenRuns = ALL_FIELDS.map { dartsRun + it }
	emitAll(childrenRuns.asFlow().flatMapMerge {
		determineAllNineDarterRunsWithPrefix(it)
	})
}


@FlowPreview
fun main() {
	val allNineDarterRuns = determineAllNineDarterRuns()
	runBlocking {
		allNineDarterRuns.collectIndexed { index, value ->
			println("${index + 1}: $value")
		}
	}
}