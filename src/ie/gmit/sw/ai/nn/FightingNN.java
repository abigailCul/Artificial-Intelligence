package ie.gmit.sw.ai.nn;

import ie.gmit.sw.ai.nn.activator.*;

//Class contains the training data, expected output and actions 
public class FightingNN {

	private final double[][] data = { //Health, Weapon, Anger level
			{ 2, 0, 0 }, { 2, 0, 0 }, { 2, 0, 1 }, { 2, 0, 1 }, { 2, 1, 0 },
			{ 2, 1, 0 }, { 2, 1, 2}, { 1, 0, 0 }, { 1, 0, 0 }, { 1, 0, 1 }, { 1, 0, 1 }, 
			{ 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 1 }, 
			{ 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 0 },{ 0, 1, 1 },{ 0, 1, 2 } };

	private final double[][] expected = { //Panic, Attack, Hide
			{ 0.0, 0.0, 1.0}, { 0.0, 0.0, 1.0 }, { 1.0, 0.0, 0.0 }, { 1.0, 0.0, 0.0 }, 
			{ 0.0, 0.0, 0.0}, { 1.0, 0.0, 0.0},{ 0.0, 1.0, 0.0}, { 0.0, 0.0, 1.0 }, { 0.0, 0.0, 0.0 }, 
			{ 1.0, 0.0, 0.0}, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 0.0 }, 
			{ 0.0, 0.0, 1.0}, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 0.0}, { 0.0, 1.0, 0.0 }, 
			{ 0.0, 1.0, 0.0 }, { 0.0, 0.0, 0.0}, { 0.0, 0.0, 1.0}, { 0.0, 0.0, 1.0} };
	
	private NeuralNetwork nn = null;
	public FightingNN() {
		
	}

	public void train() {
		nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 3, 3, 3);
		Trainator trainer = new BackpropagationTrainer(nn);
		trainer.train(data, expected, 0.2, 10000);
	}

	public int action(double health, double weapon, double angerLevel) throws Exception{

		
		double[] params = {health, weapon, angerLevel};

        double[] result = nn.process(params);

        for(double val : result){
            System.out.println(val);
        }

		int choice = (Utils.getMaxIndex(result) + 1);


		return choice;
	}

}