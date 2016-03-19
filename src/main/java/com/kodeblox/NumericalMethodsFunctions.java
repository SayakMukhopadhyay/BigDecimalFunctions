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

public class NumericalMethodsFunctions {

	/**
	 * Calculates <code>e<sup>exponent</sup></code> using Taylor series
	 * expansion. The result is rounded according to the passed context
	 * <code>mc</code>. One must give values close to <code>1</code> for
	 * <code>exponent</code>, otherwise the series may not converge.
	 *
	 * @param exponent
	 *            the value to which <code>e</code> is raised.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>e<sup>exponent</sup></code>
	 */
	protected static BigDecimal expTaylorSeries(BigDecimal exponent, MathContext mc) {
		BigDecimal lastSum;
		BigDecimal currentSum = BigDecimal.ZERO;
		BigDecimal numerator = BigDecimal.ONE;
		BigDecimal denominator = BigDecimal.ONE;
		BigDecimal term;

		int i = 0;
		do {
			i++;
			lastSum = currentSum;

			// term = x ^ n / n!
			term = numerator.divide(denominator, mc);
			currentSum = currentSum.add(term, mc);
			numerator = numerator.multiply(exponent, mc);
			denominator = denominator.multiply(BigDecimal.valueOf(i), mc);
		} while (lastSum.compareTo(currentSum) != 0);

		return currentSum;
	}

	/**
	 * Calculates <code>log<sub>e</sub> value</code> using the Newton-Raphson
	 * method. The result is rounded according to the passed context
	 * <code>mc</code>.
	 *
	 * @param value
	 *            calculates log of this value.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>log<sub>e</sub> value</code>
	 */
	protected static BigDecimal lnNewtonRaphson(BigDecimal value, MathContext mc) {

		// Newton Raphson equation for logarithm is:

		// y = y - (e ^ y - value) / e ^ y
		BigDecimal denominator;
		BigDecimal numerator;
		BigDecimal exp;
		BigDecimal lastTerm;
		BigDecimal y = value;
		BigDecimal reductionTerm;

		do {
			lastTerm = y;
			// exp = e ^ y
			exp = BigDecimalFunctions.exp(y, mc);

			// numerator = exp - value

			// which can be written as,

			// numerator = e ^ y - value
			numerator = exp.subtract(value, mc);
			denominator = exp;

			// reductionTerm = (e ^ y - value) / e ^ y
			reductionTerm = numerator.divide(denominator, mc);

			// y = y - (e ^ y - value) / e ^ y
			y = y.subtract(reductionTerm, mc);
		} while (y.compareTo(lastTerm) != 0);

		return y;
	}

	/**
	 * Calculates the <code>exponent<sup>th</sup></code> root of
	 * <code>base</code> using Newton-Raphson method. The result is rounded
	 * according to the passed context <code>mc</code>.
	 *
	 * @param base
	 *            the number to be rooted.
	 * @param exponent
	 *            the order of rooting
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>base<sup>(1/exponent)</sup></code>
	 */
	protected static BigDecimal rootNewtonRaphson(BigDecimal base, long exponent, MathContext mc) {

		// This follows the Newton-Raphson method
		// Here Newton Raphson equation for root is:

		// y = (y * (exponent - 1) + base / y ^ (exponent - 1)) / exponent
		BigDecimal y = base;
		BigDecimal power = BigDecimal.valueOf(exponent);
		BigDecimal lastTerm;
		BigDecimal powerMinusOne = power.subtract(BigDecimal.ONE, mc);
		do {
			lastTerm = y;

			// y = y * (exponent - 1) + base / y ^ (exponent - 1)
			y = y.multiply(powerMinusOne, mc)
					.add(base.divide(BigDecimalFunctions.pow(y, powerMinusOne.longValue(), mc), mc));

			// y = (y * (exponent - 1) + base / y ^ (exponent - 1)) / exponent
			y = y.divide(power, mc);
		} while (y.compareTo(lastTerm) != 0);
		return y;
	}

	/**
	 * Calculates the <code>sine</code> of an angle in <code>radians</code>
	 * using Taylor series expansion. The result is rounded according to the
	 * passed context <code>mc</code>.
	 * 
	 * @param angle
	 *            the angle in radians.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>sin (angle)</code>
	 */
	protected static BigDecimal sinTaylorSeries(BigDecimal angle, MathContext mc) {
		BigDecimal lastSum;
		BigDecimal currentSum = BigDecimal.ZERO;
		BigDecimal numerator = angle;
		BigDecimal denominator = BigDecimal.ONE;
		BigDecimal term;
		int i = 1;
		do {
			i = i + 2;
			lastSum = currentSum;

			// term = x ^ (2 * n + 1) / (2 * n + 1)!
			term = numerator.divide(denominator, mc);
			currentSum = currentSum.add(term, mc);

			// Negating as every alternative term is negative
			numerator = numerator.multiply(angle, mc).multiply(angle, mc).negate();
			denominator = denominator.multiply(BigDecimal.valueOf(i), mc).multiply(BigDecimal.valueOf(i - 1), mc);
		} while (lastSum.compareTo(currentSum) != 0);

		return currentSum;
	}

	/**
	 * Calculates the square root of <code>value</code> using Newton-Raphson.
	 * The result is rounded according to the passed context <code>mc</code>.
	 *
	 * @param value
	 *            the number to be rooted.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>value<sup>(1/2)</sup></code>
	 */
	protected static BigDecimal sqrtNewtonRaphson(BigDecimal value, MathContext mc) {

		// This follows the Newton-Raphson method
		// Here Newton Raphson equation for root is:

		// y = (y + value / y) / 2
		BigDecimal y = value;
		BigDecimal lastTerm;
		do {
			lastTerm = y;

			// y = (y + value / y) / 2
			y = y.add(value.divide(y, mc), mc).divide(BigDecimal.valueOf(2), mc);
		} while (y.compareTo(lastTerm) != 0);
		return y;
	}

	/**
	 * Calculates the <code>cosine</code> of an angle in <code>radians</code>
	 * using Taylor series expansion. The result is rounded according to the
	 * passed context <code>mc</code>.
	 * 
	 * @param angle
	 *            the angle in radians.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>cos (angle)</code>
	 */
	public static BigDecimal cosTaylorSeries(BigDecimal angle, MathContext mc) {
		BigDecimal lastSum;
		BigDecimal currentSum = BigDecimal.ZERO;
		BigDecimal numerator = BigDecimal.ONE;
		BigDecimal denominator = BigDecimal.ONE;
		BigDecimal term;
		int i = 0;
		do {
			i = i + 2;
			lastSum = currentSum;

			// term = x ^ (2 * n + 1) / (2 * n + 1)!
			term = numerator.divide(denominator, mc);
			currentSum = currentSum.add(term, mc);

			// Negating as every alternative term is negative
			numerator = numerator.multiply(angle, mc).multiply(angle, mc).negate();
			denominator = denominator.multiply(BigDecimal.valueOf(i), mc).multiply(BigDecimal.valueOf(i - 1), mc);
		} while (lastSum.compareTo(currentSum) != 0);

		return currentSum;
	}

	/**
	 * Calculates the <code>tangent</code> of an angle in <code>radians</code>
	 * using values for sine and cosine. The result is rounded according to the
	 * passed context <code>mc</code>.
	 * 
	 * @param angle
	 *            the angle in radians.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>tan (angle)</code>
	 */
	public static BigDecimal tanCompute(BigDecimal angle, MathContext mc) {
		return sinTaylorSeries(angle, mc).divide(cosTaylorSeries(angle, mc), mc);
	}

	/**
	 * Calculates the <code>arcsine</code> of a value in <code>radians</code>
	 * using arctan of the value. The result is rounded according to the passed
	 * context <code>mc</code>.
	 * 
	 * @param value
	 *            the number whose arcsine is to be found.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>sin<sup>-1</sup>(value)</code>
	 */
	public static BigDecimal arcsinCompute(BigDecimal value, MathContext mc) {

		return BigDecimalFunctions.arctan(
				value.divide(BigDecimalFunctions.sqrt(BigDecimal.ONE.subtract(value.multiply(value, mc), mc), mc), mc),
				mc);
	}

	/**
	 * Calculates the <code>arccosine</code> of a value in <code>radians</code>
	 * using arctan of the value. The result is rounded according to the passed
	 * context <code>mc</code>.
	 * 
	 * @param value
	 *            the number whose arccosine is to be found.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>cos<sup>-1</sup>(value)</code>
	 */
	public static BigDecimal arccosCompute(BigDecimal value, MathContext mc) {

		return BigDecimalFunctions.arctan(
				BigDecimalFunctions.sqrt(BigDecimal.ONE.subtract(value.multiply(value, mc), mc), mc).divide(value, mc),
				mc);
	}

	/**
	 * Calculates the <code>arctangent</code> of a value in <code>radians</code>
	 * using Taylor series expansion. The result is rounded according to the
	 * passed context <code>mc</code>.
	 * 
	 * @param value
	 *            the number whose arctangent is to be found.
	 * @param mc
	 *            rounding mode and precision for the result of this operation.
	 * @return <code>tan<sup>-1</sup>(value)</code>
	 */
	public static BigDecimal arctanTaylorSeries(BigDecimal value, MathContext mc) {
		BigDecimal lastSum;
		BigDecimal currentSum = BigDecimal.ZERO;
		BigDecimal numerator = value;
		BigDecimal denominator = BigDecimal.ONE;
		BigDecimal term;
		int i = 0;
		do {
			i++;
			lastSum = currentSum;

			// term = x ^ (2 * n + 1) / (2 * n + 1)!
			term = numerator.divide(denominator, mc);
			currentSum = currentSum.add(term, mc);

			// Negating as every alternative term is negative
			numerator = numerator.multiply(value, mc).multiply(value, mc).negate();
			denominator = BigDecimal.valueOf(2 * i + 1);
		} while (lastSum.compareTo(currentSum) != 0);

		return currentSum;
	}

}
