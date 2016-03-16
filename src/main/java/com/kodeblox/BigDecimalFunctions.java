/*Copyright 2016 Sayak Mukhopadhyay

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.kodeblox;

import java.math.BigDecimal;
import java.math.MathContext;
import com.kodeblox.NumericalMethodsFunctions;

/**
 * Mathematical functions needed for BigDecimal
 */
public final class BigDecimalFunctions {

	public static final BigDecimal PI = new BigDecimal("3.141592653589793238462643383279503");

	/**
	 * Calculates <code>e<sup>exponent</sup></code>. The result is rounded
	 * according to the passed context <code>mc</code>.
	 *
	 * @param exponent
	 *            the value to which <code>e</code> is raised.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>e<sup>exponent</sup></code>
	 */
	public static BigDecimal exp(BigDecimal exponent, MathContext mc) {

		MathContext newMc = new MathContext(mc.getPrecision() + 3);

		// If the exponent is lesser than 0, negate the exponent, calculate
		// the value and find the reciprocal.
		// 1 / e ^(-exponent)
		if (exponent.compareTo(BigDecimal.ZERO) < 0) {
			return BigDecimal.ONE.divide(exp(exponent.negate(), newMc), newMc);
		}

		// If the exponent is 0, we all know that the result would be one.
		if (exponent.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ONE;
		}

		// If the exponent is greater than 0, control will come here.

		// Now to find the whole part of the exponent.
		// i.e. if the exponent is say 1.64, the whole part is 1.
		BigDecimal wholePart = getWhole(exponent);

		// Checking to see if the exponent is close to one.
		// If the whole part is 0 or 1, then the exponent is close to 1.
		// And if the exponent is close to 1, we can directly start the Taylor's
		// Series
		// without fear and the result is returned.
		if (wholePart.compareTo(BigDecimal.ZERO) == 0 || wholePart.compareTo(BigDecimal.ONE) == 0) {
			return NumericalMethodsFunctions.expTaylorSeries(exponent, newMc).round(mc);
		}

		// If the exponent is a larger number, control will come here.

		// The logic behind working with such numbers is that if a large number
		// is broken into its whole and fractional parts, then 1 +
		// fraction/whole
		// will always be near 1.Also,

		// e ^ x = (e ^ (1 + fraction/whole))^ whole

		// So, we first find the exponent near 1, raise e to that power then
		// again
		// raise the result to the integral power(since whole is an integer).

		// Now to find the fraction part of the exponent.
		// i.e. if the exponent is say 1.64, the fraction part is 0.64.
		BigDecimal fractionPart = getFraction(exponent);

		// newExponent = 1 + fractionPart / wholePart
		BigDecimal newExponent = BigDecimal.ONE.add(fractionPart.divide(wholePart, newMc), newMc);
		BigDecimal power = wholePart;

		// taylor = e ^ newExponent
		BigDecimal taylor = NumericalMethodsFunctions.expTaylorSeries(newExponent, newMc);
		BigDecimal longMaxValue = BigDecimal.valueOf(Long.MAX_VALUE);

		// In case the whole part is so large that it does not fit in long, then
		// the powers must be
		// done in multi steps using the following logic:

		// e ^ x = e ^ a * e ^ b * e ^ c
		// where, x = a + b + c

		// Thus, each time e will be powered by the maximum long and the
		// exponent will
		// be decreased by the same maximum until the exponent is lesser than
		// the maximum.
		BigDecimal result = BigDecimal.ONE;
		if (wholePart.compareTo(longMaxValue) > 0) {
			while (power.compareTo(longMaxValue) > 0) {
				result = result.multiply(pow(taylor, Long.MAX_VALUE, newMc), newMc);
				power = power.subtract(longMaxValue, newMc);
			}
		}

		// One multiplication was left, so the last multiplication is done here
		return result.multiply(pow(taylor, power.longValue(), newMc)).round(mc);
	}

	/**
	 * Calculates <code>base<sup>exponent</sup></code>. The result is rounded
	 * according to the passed context <code>mc</code>. This function
	 * specifically works on a <code>long exponent</code>.
	 *
	 * @param base
	 *            the value which is raised.
	 * @param exponent
	 *            the value to which the <code>base</code> is raised.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>base<sup>exponent</sup></code>
	 */
	public static BigDecimal pow(BigDecimal base, long exponent, MathContext mc) {

		// If the exponent is lesser than 0, negate the exponent, calculate
		// the value and find the reciprocal.
		// 1 / base ^(-exponent)
		if (exponent < 0) {
			return BigDecimal.ONE.divide(pow(base, -exponent, mc), mc);
		}

		BigDecimal result = BigDecimal.ONE;

		// A fast algorithm to compute powers.

		while (exponent > 0) {

			if ((exponent & 1) == 1) {
				result = result.multiply(base, mc);
			}
			base = base.multiply(base, mc);
			exponent >>= 1;
		}

		return result;
	}

	/**
	 * Returns the whole part of <code>number</code>.
	 *
	 * @param number
	 *            the number whose whole part is calculated.
	 * @return whole part of <code>number</code>
	 */
	public static BigDecimal getWhole(BigDecimal number) {
		return number.setScale(0, BigDecimal.ROUND_DOWN);
	}

	/**
	 * Returns the fractional part of <code>number</code>.
	 *
	 * @param number
	 *            the number whose fractional part is calculated.
	 * @return fractional part of <code>number</code>
	 */
	public static BigDecimal getFraction(BigDecimal number) {

		// Subtracts the whole part from the actual number.
		return number.subtract(getWhole(number));
	}

	/**
	 * Calculates <code>log<sub>e</sub> value</code>. The result is rounded
	 * according to the passed context <code>mc</code>.
	 *
	 * @param value
	 *            calculates log of this value.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>log<sub>e</sub> value</code>
	 * @throws IllegalArgumentException
	 *             if <code>value <= 0</code>.
	 */
	public static BigDecimal ln(BigDecimal value, MathContext mc) {
		// If the value is lesser than or equal to 0, logarithm cannot
		// be calculated. IllegalArgumentException thrown.
		if (value.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Log requires values greater than 0");
		}

		MathContext newMc = new MathContext(mc.getPrecision() + 3);
		// Calculates the number of digits in the significand.
		long wholeDigits = value.precision() - value.scale();
		final int THRESHOLD = 3;

		// If the number is within a threshold, simple Newton-Raphson is done.
		// But for a big number,
		// the number is first reduced to value ^ (1 / significandDigits). Then
		// the logarithm is calculated and then multiplied by significandDigits.

		// log value = significandDigits * log value ^ (1 / significandDigits).
		if (wholeDigits < THRESHOLD) {
			return NumericalMethodsFunctions.lnNewtonRaphson(value, newMc).round(mc);
		}
		return BigDecimal.valueOf(wholeDigits)
				.multiply(NumericalMethodsFunctions.lnNewtonRaphson(root(value, wholeDigits, newMc), newMc), newMc)
				.round(mc);
	}

	/**
	 * Calculates <code>base<sup>exponent</sup></code>. The result is rounded
	 * according to the passed context <code>mc</code>.
	 *
	 * @param base
	 *            the value which is raised.
	 * @param exponent
	 *            the value to which the <code>base</code> is raised.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>base<sup>exponent</sup></code>
	 */
	public static BigDecimal pow(BigDecimal base, BigDecimal exponent, MathContext mc) {

		// Check if the power is an integer
		// If so, then use the long exponent function which is faster
		if (getFraction(exponent).compareTo(BigDecimal.ZERO) == 0) {
			return pow(base, exponent.longValue(), mc);
		}

		MathContext newMc = new MathContext(mc.getPrecision() + 3);
		// The calculation is done as,
		// base^exponent = e ^ (exponent * ln base)

		// ln = ln base
		BigDecimal ln = ln(base, newMc);

		// exp = exponent * ln base
		BigDecimal exp = exponent.multiply(ln, newMc);

		// return e ^ (exponent * ln base)
		return exp(exp, newMc).round(mc);
	}

	/**
	 * Calculates the square root of <code>value</code>. The result is rounded
	 * according to the passed context <code>mc</code>.
	 *
	 * @param value
	 *            the number to be rooted.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>value<sup>(1/2)</sup></code>
	 */
	public static BigDecimal sqrt(BigDecimal value, MathContext mc) {
		MathContext newMc = new MathContext(mc.getPrecision() + 3);
		return NumericalMethodsFunctions.sqrtNewtonRaphson(value, newMc).round(mc);
	}

	/**
	 * Calculates the <code>exponent<sup>th</sup></code> root of
	 * <code>base</code>. The result is rounded according to the passed context
	 * <code>mc</code>.
	 *
	 * @param base
	 *            the number to be rooted.
	 * @param exponent
	 *            the order of rooting
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>base<sup>(1/exponent)</sup></code>
	 */
	public static BigDecimal root(BigDecimal base, long exponent, MathContext mc) {
		MathContext newMc = new MathContext(mc.getPrecision() + 3);
		return NumericalMethodsFunctions.rootNewtonRaphson(base, exponent, newMc).round(mc);
	}

	/**
	 * Calculates the <code>sine</code> of an angle in <code>radians</code>. The
	 * result is rounded according to the passed context <code>mc</code>.
	 * 
	 * @param angle
	 *            the angle in radians.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>sin (angle)</code>
	 */
	public static BigDecimal sin(BigDecimal angle, MathContext mc) {

		MathContext newMc = new MathContext(mc.getPrecision() + 3);

		// Checking to see if the entered angle is greater than 2 * PI
		// and reduce accordingly.
		if (angle.compareTo(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(2), newMc)) >= 0) {

			// angle = n * 2 * PI + reducedAngle
			// n = floor(angle / 2 * PI)
			long n = angle.divide(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(2), newMc), newMc).longValue();
			angle = angle.subtract(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(n * 2), newMc), newMc);
		}

		// All angle values to be reduced between 0 to PI / 2
		// for quick calculation.
		// Checking to see if the angle is greater than PI
		// If so reduce
		if (angle.compareTo(BigDecimalFunctions.PI) >= 0) {
			angle = angle.subtract(BigDecimalFunctions.PI, newMc).negate();
		}

		// Checking to see if the angle is greater than PI / 2
		if (angle.compareTo(BigDecimalFunctions.PI.divide(BigDecimal.valueOf(2), newMc)) > 0) {
			angle = angle.subtract(BigDecimalFunctions.PI, newMc).negate();
		}

		// Checking whether the angle is negative
		if (angle.compareTo(BigDecimal.ZERO) < 0) {

			// Since sin(-angle) = -sin(angle).
			return sin(angle.negate(), newMc).negate().round(mc);
		}

		// Returns 0 for 0 rads
		if (angle.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}

		return NumericalMethodsFunctions.sinTaylorSeries(angle, newMc).round(mc);
	}
	
	/**
	 * Calculates the <code>cosine</code> of an angle in <code>radians</code>. The
	 * result is rounded according to the passed context <code>mc</code>.
	 * 
	 * @param angle
	 *            the angle in radians.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>cos (angle)</code>
	 */
	public static BigDecimal cos(BigDecimal angle, MathContext mc) {

		MathContext newMc = new MathContext(mc.getPrecision() + 3);

		// Checking to see if the entered angle is greater than 2 * PI
		// and reduce accordingly.
		if (angle.compareTo(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(2), newMc)) >= 0) {

			// angle = n * 2 * PI + reducedAngle
			// n = floor(angle / 2 * PI)
			long n = angle.divide(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(2), newMc), newMc).longValue();
			angle = angle.subtract(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(n * 2), newMc), newMc);
		}

		// All angle values to be reduced between 0 to PI / 2
		// for quick calculation.
		// Checking to see if the angle is greater than PI
		// If so reduce
		if (angle.compareTo(BigDecimalFunctions.PI) >= 0) {
			angle = angle.subtract(BigDecimalFunctions.PI.multiply(BigDecimal.valueOf(2), newMc), newMc).negate();
		}

		// Checking to see if the angle is greater than PI / 2
		if (angle.compareTo(BigDecimalFunctions.PI.divide(BigDecimal.valueOf(2), newMc)) > 0) {
			angle = angle.subtract(BigDecimalFunctions.PI, newMc).negate();
			return NumericalMethodsFunctions.cosTaylorSeries(angle, newMc).negate().round(mc);
		}

		// Checking whether the angle is negative
		if (angle.compareTo(BigDecimal.ZERO) < 0) {

			// Since cos(-angle) = cos(angle).
			return cos(angle.negate(), newMc).round(mc);
		}

		// Returns 0 for 0 rads
		if (angle.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ONE;
		}

		return NumericalMethodsFunctions.cosTaylorSeries(angle, newMc).round(mc);
	}
}
