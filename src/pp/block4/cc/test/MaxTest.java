package pp.block4.cc.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import pp.iloc.Assembler;
import pp.iloc.Simulator;
import pp.iloc.eval.Machine;
import pp.iloc.model.Program;

public class MaxTest {

	@Test
	public void complieTest() throws Exception {
		Assembler assembler = Assembler.instance();
		Program p = assembler.assemble(new File("max.iloc"));
		Program q = assembler.assemble(p.prettyPrint());
		assertEquals(p, q);
	}
	
	@Test
	public void runTest() throws Exception {
		Assembler assembler = Assembler.instance();
		Program p = assembler.assemble(new File("max.iloc"));
		Simulator sim = new Simulator(p);
		Machine vm = sim.getVM();
		vm.init("a", 1337, 538, 31415, 29979, 0, 1, 271828, 3, 7, -1);
		vm.setNum("alength", 10);
		sim.run();
		int result = vm.getReg("r_max");
		assertEquals(271828, result);
	}
	
	/*
	 * De muliplicatie met 4 is omdat een integer 4 bytes is,
	 * dus 4 addressen lang in het geheugen.	 * 
	 */
	
}
