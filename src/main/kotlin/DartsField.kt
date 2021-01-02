/**
 * A field of a darts board.
 *
 * @property points the points the field is worth
 */
sealed class DartsField(val points: Int) {
	abstract override fun toString(): String
}

/**
 * A regular field of a darts board.
 *
 * @param value the value of the sector
 * @param factor the factor used for multiplying the sector value
 */
sealed class RegularField(
	value: Int,
	factor: Int
) : DartsField(points = value * factor)

/**
 * A regular field that is neither in the double ring nor in the triple ring.
 *
 * @property value the value of the sector
 */
class SingleField(private val value: Int) : RegularField(value, 1) {
	override fun toString() = value.toString()
}

/**
 * A field in the double ring.
 *
 * @property value the value of the sector
 */
class DoubleField(private val value: Int) : RegularField(value, 2) {
	override fun toString() = "D${value}"
}

/**
 * A field in the triple ring.
 *
 * @property value the value of the sector
 */
class TripleField(private val value: Int) : RegularField(value, 3) {
	override fun toString() = "T${value}"
}

/**
 * The outer bull's eye.
 */
object OuterBullsEye : DartsField(25) {
	override fun toString() = "OBE"
}

/**
 * The inner bull's eye.
 */
object InnerBullsEye : DartsField(50) {
	override fun toString() = "IBE"
}


/**
 * The values of the sectors.
 */
private val SECTOR_VALUES = 1..20

/**
 * All fields of a darts board.
 */
val ALL_FIELDS = SECTOR_VALUES.flatMap {
	setOf(SingleField(it), DoubleField(it), TripleField(it))
}.union(setOf(InnerBullsEye, OuterBullsEye))

/**
 * Whether a darts field is a finishing field.
 */
fun DartsField.isFinishingField() = this is InnerBullsEye || this is DoubleField

