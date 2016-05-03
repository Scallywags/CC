package pp.block2.cc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import pp.block2.cc.Calculator;
import pp.block2.cc.ParseException;

public class CalculatorTest {
	
	private final Calculator calc = new Calculator();

	@Test
	public void testAllTheThings() {
		outcome(44, "8+9*4");
		outcome(3, "8-5");
		outcome(-128, "-((3-5)^(2+4*2)/8)");
		outcome(256, "2^2^3");
		fails("/");
		fails("5*");
		fails("^7");
	}

	void outcome(int expected, String expression) {
		try {
			assertEquals(expected, calc(expression));
		} catch (ParseException e) {
			fail();
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
