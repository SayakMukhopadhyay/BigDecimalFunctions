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
	public void testExp() {
		BigDecimal actual = BigDecimalFunctions.exp(BigDecimal.valueOf(5), mc);
		BigDecimal expected = new BigDecimal("148.41315910257660342111558004055");
		System.out.println("Testing exp function with parameter as 5");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	public void testPowLong() {
		BigDecimal actual = BigDecimalFunctions.pow(BigDecimal.valueOf(2.5), 5, mc);
		BigDecimal expected = new BigDecimal("97.65625");
		System.out.println("Testing long power function with parameter as 2.5, 5");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	public void testLn() {
		BigDecimal actual = BigDecimalFunctions.ln(BigDecimal.valueOf(5), mc);
		BigDecimal expected = new BigDecimal("1.6094379124341003746007593332262");
		System.out.println("Testing ln function with parameter as 5");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	public void testPowBigDecimal() {
		BigDecimal actual = BigDecimalFunctions.pow(BigDecimal.valueOf(2.5), BigDecimal.valueOf(2.5), mc);
		BigDecimal expected = new BigDecimal("9.8821176880261854124965423263522");
		System.out.println("Testing BigDecimal power function with parameter as 2.5, 2.5");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	public void testSqrt() {
		BigDecimal actual = BigDecimalFunctions.sqrt(BigDecimal.valueOf(5), mc);
		BigDecimal expected = new BigDecimal("2.2360679774997896964091736687313");
		System.out.println("Testing sqrt function with parameter as 5");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	public void testRoot() {
		BigDecimal actual = BigDecimalFunctions.root(BigDecimal.valueOf(5), 3, mc);
		BigDecimal expected = new BigDecimal("1.7099759466766969893531088725439");
		System.out.println("Testing root function with parameter as 5, 3");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}

	@Test
	public void testSin() {
		BigDecimal actual = BigDecimalFunctions.sin(BigDecimal.valueOf(1.5), mc);
		BigDecimal expected = new BigDecimal("0.99749498660405443094172337114149");
		System.out.println("Testing sin function with parameter as 1.5");
		printReport(actual, expected);
		assertEquals(expected, actual);
	}
}
