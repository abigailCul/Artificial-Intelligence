package ie.gmit.sw.ai.fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class FightFuzzy {
	
	public double PlayerHealth(double weapon , double enemy) {
		FIS fis = FIS.load("resources/FCL/fight.fcl", true); //Load and parse the FCL
		
		fis.setVariable("weapon", weapon); //Apply a value to a variable
		fis.setVariable("enemy", enemy);
		fis.evaluate(); //Execute the fuzzy inference engine
		return fis.getVariable("life").getValue(); //Output end result
	}

}
