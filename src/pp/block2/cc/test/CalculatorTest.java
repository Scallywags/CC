package pp.block2.cc.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import pp.block2.cc.Calculator;
import pp.block2.cc.ParseException;

public class CalculatorTest {

	private final Calculator calc = new Calculator();

	@Test
	public void testAllTheThings() {
		compare(34, "18+16");
		compare(44, "8+9*4");
		compare(8, "((3-5)^(2+4*1)/8)");
		fails("/");
		fails("5*");
		fails("/7");
	}

	void compare(int expected, String expression) {
		try {
			assertEquals(expected, calc(expression));
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	void fails(String expression) {
		try {
			calc(expression);
		} catch (ParseException e) {
			//success
		}
	}

	private int calc(String expression) throws ParseException {
		return calc.calculate(expression).intValue();
	}

}
