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
		Assembler assembler = Assembler.instance();
		Program p = assembler.assemble(new File("fibreg.iloc"));
		Simulator sim = new Simulator(p);
		Machine vm = sim.getVM();
		vm.setNum("n", 31);
		vm.setNum("z", 0);
		sim.run();
		int fib = vm.getReg("r_z");
		assertEquals(2178309, fib);
	}
	
	@Test
	public void testMemToMem() throws Exception {
		Assembler assembler = Assembler.instance();
		Program p = assembler.assemble(new File("fibmem.iloc"));
		Simulator sim = new Simulator(p);
		Machine vm = sim.getVM();
		vm.setSize(16);
		vm.setNum("n", 31);
		vm.setNum("x", 0);
		vm.setNum("y", 4);
		vm.setNum("z", 8);
		sim.run();
		int fib = vm.getReg("r_z");
		assertEquals(2178309, fib);
	}

}
