package pp.block4.cc.test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import pp.iloc.Assembler;
import pp.iloc.Simulator;
import pp.iloc.eval.Machine;
import pp.iloc.model.Program;

public class FibTest {
	
	@Test
	public void testRegToReg() throws Exception {
		for (int n = 2; n < 45; n++) {
			testFib(n);
		}
		System.out.println(testFib(45));
		System.out.println(testFib(46));
	}
	
	private int testFib(int n) throws Exception {
		int expected = fib(n);
		
		Assembler assembler = Assembler.instance();
		Program p = assembler.assemble(new File("fibreg.iloc"));
		Simulator sim = new Simulator(p);
		Machine vm = sim.getVM();
		vm.setNum("n", n);
		vm.setNum("z", 0);
		sim.run();
		int fib = vm.getReg("r_z");
		assertEquals(expected, fib);
		
		assembler = Assembler.instance();
		p = assembler.assemble(new File("fibmem.iloc"));
		sim = new Simulator(p);
		vm = sim.getVM();
		vm.setSize(16);
		vm.setNum("n", n);
		vm.setNum("x", 0);
		vm.setNum("y", 4);
		vm.setNum("z", 8);
		sim.run();
		fib = vm.getReg("r_z");
		assertEquals(expected, fib);
		
		return expected;
	}
	
	private int fib(int n) {
		int x = 1;
		int y = 1;
		int z = 1;
				
		while (n > 1) {
			z = y + x;
			x = y;
			y = z;
			n--;
		}
		return z;
	}

}
