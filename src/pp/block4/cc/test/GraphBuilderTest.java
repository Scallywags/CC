package pp.block4.cc.test;

import java.io.File;

import pp.block4.cc.cfg.BottomUpCFGBuilder;
import pp.block4.cc.cfg.Graph;
import pp.block4.cc.cfg.TopDownCFGBuilder;

public class GraphBuilderTest {
	
	public static void main(String[] args) {
		System.out.println("BOTTOM UP");
		BottomUpCFGBuilder boUpB = new BottomUpCFGBuilder();
		Graph g = boUpB.build(new File("max.txt"));
		System.out.println(g);
		
		System.out.println("TOP DOWN");
		TopDownCFGBuilder topDB = new TopDownCFGBuilder();
		g = topDB.build(new File("max.txt"));
		System.out.println(g);
	}

}
