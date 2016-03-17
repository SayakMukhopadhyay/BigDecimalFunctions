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

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class BigDecimalFunctionsTest {

	private MathContext mc;

	public BigDecimalFunctionsTest() {
		mc = new MathContext(32);
	}

	public void printReport(Object actual, Object expected) {
		System.out.println("----------------------------------------------------");
		System.out.println("Required Value   : " + expected);
		System.out.println("Calculated Value : " + actual);
		System.out.println("----------------------------------------------------");
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideExp")
	public void testExp(String param, String answer) {
		BigDecimal actual = BigDecimalFunctions.exp(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing exp function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "providePowLong")
	public void testPowLong(String param1, String param2, String answer) {
		BigDecimal actual = BigDecimalFunctions.pow(new BigDecimal(param1), Long.parseLong(param2), mc);
		BigDecimal expected = new BigDecimal(answer);
		System.out.println("Testing long power function with parameter as " + param1 + param2);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideLn")
	public void testLn(String param, String answer) {
		BigDecimal actual = BigDecimalFunctions.ln(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing ln function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "providePowBigDecimal")
	public void testPowBigDecimal(String param1, String param2, String answer) {
		BigDecimal actual = BigDecimalFunctions.pow(new BigDecimal(param1), new BigDecimal(param2), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing BigDecimal power function with parameter as " + param1 + param2);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideSqrt")
	public void testSqrt(String param, String answer) {
		BigDecimal actual = BigDecimalFunctions.sqrt(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);
		System.out.println("Testing sqrt function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideRoot")
	public void testRoot(String param1, String param2, String answer) {
		BigDecimal actual = BigDecimalFunctions.root(new BigDecimal(param1), Long.parseLong(param2), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing root function with parameter as " + param1 + param2);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideSin")
	public void testSin(String param, String answer) {

		BigDecimal actual = BigDecimalFunctions.sin(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing sin function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}
	
	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideCos")
	public void testCos(String param, String answer) {

		BigDecimal actual = BigDecimalFunctions.cos(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing cos function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}
	
	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideTan")
	public void testTan(String param, String answer) {

		BigDecimal actual = BigDecimalFunctions.tan(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing tan function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}
	
	@Test
	@Parameters(source = BigDecimalFunctionsTestProvider.class, method = "provideFactorial")
	public void testFactorial(String param, String answer) {

		BigDecimal actual = BigDecimalFunctions.factorial(new BigDecimal(param), mc);
		BigDecimal expected = new BigDecimal(answer);

		System.out.println("Testing factorial function with parameter as " + param);
		printReport(actual, expected);
		assertEquals(expected, actual);
	}
}
