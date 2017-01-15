package org.raspinloop.standalone;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.raspinloop.fmi.CSHandler;
import org.raspinloop.fmi.Handler;
import org.raspinloop.fmi.HandlerRunner;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.Type;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;

public class TimeSequencer implements HandlerRunner {

	final static Logger logger = Logger.getLogger(TimeSequencer.class);



	final private String guid;

	final private double stepIncrement;

	final private double timeRatio;

	private CSHandler handler;


	private List<Fmi2ScalarVariable> variables;


	private double duration;


	private Map<Integer, String> realVars = new HashMap<>();
	private Map<Integer, String> intVars = new HashMap<>();
	private Map<Integer, String> boolVars = new HashMap<>();
	private Map<Integer, String> stringVars = new HashMap<>();
	
	private Map<Integer, Double> realValues = new HashMap<>();
	private Map<Integer, Integer> intValues = new HashMap<>();
	private Map<Integer, Boolean> boolValues = new HashMap<>();
	

	public TimeSequencer(String guid, double stepIncrement, double timeRatio, double duration) {		
		this.guid = guid;
		
		this.stepIncrement = stepIncrement;
		this.timeRatio = timeRatio;
		this.duration = duration;					
	}

	private void buildVariablesSets(List<Fmi2ScalarVariable> variables2) {
		for (Fmi2ScalarVariable fmi2ScalarVariable : variables) {
			if ("output".equalsIgnoreCase(fmi2ScalarVariable.getCausality())) { //we get only output
				Map<Integer, String> container;
				if (fmi2ScalarVariable.getReal()!= null)
					container = realVars;
				else if (fmi2ScalarVariable.getInteger()!= null)
					container = intVars;
				else if (fmi2ScalarVariable.getBoolean()!= null)
					container = boolVars;
				else
					container = stringVars;
				
				container.put( (int)fmi2ScalarVariable.getValueReference(), fmi2ScalarVariable.getName());
			}
		}
	}

	@Override
	public void run() {
		Instance instance;
		try {
			instance = handler.instanciate("standAlone Instance", Type.CoSimulation, guid, "", true, true);
			if (instance == null)
				return;
		} catch (TException e1) {
			return;
		}
		variables = handler.getHwEmulationFactory().get(instance).getModelVariables();
		buildVariablesSets(variables);
		try {			
			handler.setupExperiment(instance, false, 0.0, new Date().getTime() / 1000.0, false, 0.0);
			handler.enterInitializationMode(instance);
			handler.exitInitializationMode(instance);
			double currentCommunicationPoint = 0;
			double communicationStepSize = stepIncrement;
			logger.info("Simulated Time: Duration["+duration+" s], StepIncrement["+stepIncrement+" s], Ratio["+timeRatio+"]");
			while (currentCommunicationPoint < duration) {				
				Thread.sleep((long)(1000* timeRatio * stepIncrement));
				currentCommunicationPoint +=  stepIncrement;
				handler.doStep(instance, currentCommunicationPoint, communicationStepSize, false);
				getAndPrintOutputChanges(instance, currentCommunicationPoint );			
			}
			handler.terminate(instance);			
		} catch (TException  | InterruptedException e) {
			logger.error(e);
		} finally {
			try {
				handler.freeInstance(instance);
			} catch (TException e) {
			}
		}

	}

	private void getAndPrintOutputChanges(Instance instance, double time) throws TException {
		
			ArrayList<Integer> refs = new ArrayList<Integer>(realVars.keySet());
			List<Double> reals = handler.getReal(instance, refs);
			for (int i = 0; i < reals.size(); i++) {
				if (! reals.get(i).equals(realValues.get(refs.get(i)))){
					realValues.put(refs.get(i), reals.get(i) );
					logger.info("Value for "+realVars.get(refs.get(i))+" @"+String.format("%1$,.2f", time)+": "+ reals.get(i));
				}
			}
			
			refs = new ArrayList<Integer>(intVars.keySet());
			List<Integer> ints = handler.getInteger(instance, refs);
			for (int i = 0; i < ints.size(); i++) {
				if (! ints.get(i).equals(intValues.get(refs.get(i)))){
					intValues.put(refs.get(i), ints.get(i) );
					logger.info("Value for "+intVars.get(refs.get(i))+" @"+String.format("%1$,.2f", time)+": "+ ints.get(i));
				}
			}
			
			refs = new ArrayList<Integer>(boolVars.keySet());
			List<Boolean> bools = handler.getBoolean(instance, refs);
			for (int i = 0; i < bools.size(); i++) {
				if (! bools.get(i).equals(boolValues.get(refs.get(i)))){
					boolValues.put(refs.get(i), bools.get(i) );
					logger.info("Value for "+boolVars.get(refs.get(i))+" @"+String.format("%1$,.2f", time)+": "+ bools.get(i));
				}
			}
	}

	@Override
	public void setHandle(Handler handler) {
		this.handler =  handler.getCsHandler();
		
	}
}
