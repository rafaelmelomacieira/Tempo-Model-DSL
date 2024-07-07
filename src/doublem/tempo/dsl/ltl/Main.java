package doublem.tempo.dsl.ltl;
import java.io.File;

import doublem.tempo.utils.FileManagement;
import rwth.i2.ltl2ba4j.DottyWriter;
import rwth.i2.ltl2ba4j.LTL2BA4J;
import rwth.i2.ltl2ba4j.formula.IFormula;
import rwth.i2.ltl2ba4j.formula.IFormulaFactory;
import rwth.i2.ltl2ba4j.formula.impl.FormulaFactory;
import rwth.i2.ltl2ba4j.model.ITransition;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new File("").getAbsolutePath());
		
		/*IFormulaFactory factory = new FormulaFactory();
		IFormula formula = factory.G(
		                     factory.And(
		                        factory.Proposition("prop1"),
		                        factory.Not(
		                                factory.Proposition("prop2")
		                        )
		                     )
		                   );*/
		/*for(ITransition t: LTL2BA4J.formulaToBA("[]<>(p -> b)  <-> (b U c)")) {
		    System.out.println(t.getLabels());
		}*/
		//[]( ~(TXSDPKGSDING && TXWRPKGLEN) )
		String ltlf = "[](p1 || p2)";
		System.out.println(DottyWriter.automatonToDot(LTL2BA4J.formulaToBA(ltlf)));
		FileManagement.createDotFile("TESTE", "./dotFiles", DottyWriter.automatonToDot(LTL2BA4J.formulaToBA(ltlf)), true);
		FileManagement.createImageFile("TESTE", "./dotFiles", FileManagement.ImageType.PNG, true);
		/*System.out.println(DottyWriter.automatonToDot(LTL2BA4J.formulaToBA("(b U c)")));
		System.out.println(DottyWriter.automatonToDot(LTL2BA4J.formulaToBA("([]<>(p -> b)) && (b U c)")));*/
		//LTL2BA4J.setGraphFactory(new MyGraphFactory());
		
		BAGraphFactory devCGraphFactory = new BAGraphFactory();
		BAGraph devCGraph = new BAGraph();
		
		
	}

}
