package com.adrianhoelzl.ninedarter

/**
 * A darts run, i.e., a sequence of scored fields.
 *
 * @property thrownFields the fields that are scored during the run
 */
inline class DartsRun(
	private val thrownFields: List<DartsField>
) {

	companion object {
		/**
		 * Determine a maximal run of a given [length], i.e., a run that contains only triple-20 fields and finishes
		 * with the inner bull's eye.
		 *
		 * @param length the length of the run
		 * @return a maximal run of the given [length]
		 */
		@OptIn(ExperimentalUnsignedTypes::class)
		private fun determineMaximalRun(length: UInt): DartsRun {
			if (length == 0u) {
				return emptyDartsRun()
			}

			val thrownFields = TripleField(20).repeat(length - 1u) + InnerBullsEye
			return DartsRun(thrownFields)
		}
	}

	/**
	 * Check whether the run is a potential prefix of a nine-darter run. Note that only **real** prefixes, i.e.,
	 * prefixes that are not identical to a nine-darter run, are considered.
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	fun isPotentialRealPrefixOfNineDarterRun(): Boolean {
		if (isOverthrown() || isFinished() || moreThanNineDartsThrown()) {
			return false
		}

		// check whether the leg can be finished by a maximal suffix
		val maximalSuffix = determineMaximalRun(9u - thrownFields.size.toUInt())
		return score() + maximalSuffix.score() >= POINTS_TO_SCORE
	}

	/**
	 * Check whether the run is finished, i.e., the points of the scored fields sum up to 501 and the last field is a
	 * finishing field.
	 */
	private fun isFinished(): Boolean {
		// there has to be a last field, otherwise empty list
		val lastField = thrownFields.lastOrNull() ?: return false

		return score() == POINTS_TO_SCORE && lastField.isFinishingField()
	}

	/**
	 * Check whether more than nine darts have been thrown.
	 */
	private fun moreThanNineDartsThrown() = thrownFields.size > 9

	/**
	 * Check whether the run represents an overthrow.
	 */
	private fun isOverthrown() = score() > POINTS_TO_SCORE

	/**
	 * Check whether the run represents a nine-darter, i.e., it is finished and only nine darts were needed.
	 */
	fun isNineDarter() = isFinished() && thrownFields.size == 9

	/**
	 * The sum of all scores in the run.
	 */
	private fun score() = thrownFields.sumBy { it.points }

	operator fun plus(field: DartsField) = DartsRun(thrownFields + field)

	override fun toString() = thrownFields.toString()

}

/**
 * Return an empty darts run.
 */
fun emptyDartsRun() = DartsRun(emptyList())