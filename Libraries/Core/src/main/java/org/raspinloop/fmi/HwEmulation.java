package org.raspinloop.fmi;

import java.util.List;
import java.util.Map;

import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;

/**
 * 
 *	HwEmulation is the interface used by the simulation tool.
 *	<br> to get the description (model)
 *	<br> to get the ID 
 *	<br> to initialize, reset and terminate the simulation
 *	<br> and to read and write variable (setBoolean, setInteger,..., getBoolean, getInteger,...)
 *
 */
public interface HwEmulation {

	/**
	 * Each hardware emulation has a GUID. this GUId is present in modelDescription.xml (bundled in FMU) 
	 * 
	 */
	String getHWGuid();
	
	String getType();
	
	public List<Fmi2ScalarVariable> getModelVariables();
	
	/**
	 * Informs the FMU to enter Initialization Mode.
	 * Before calling this function , all variables with attribute <ScalarVariable initial ="exact" or "approx"> can be set with the
	 * "SetXXX" functions (the ScalarVariable attributes are defined in the ModelDescription File, 
	 * see section 2.2.7). Setting other variables is not allowed.
	 * @return succeed or not
	 */
	boolean enterInitialize();

	
	/**
	 * Informs the FMU to exit Initialization Mode.	
	 * @return succeed or not
	 */
	boolean exitInitialize();

	/**
	 * Informs the FMU that the simulation run is terminated. After calling this function, the final
	 * values of all variables can be inquired with the GetXXX(..) functions.
	 */
	void terminate();

	/**
	 * Is called by the environment to reset the FMU after a simulation run. The FMU goes into the
	 * same state as when it is created. All variables have their default
	 * values. Before starting a new run, enterInitialization have to be called.
	 */
	void reset();
	
	/**
	 * Get actual values of variables by providing their variable references. [These functions are
	 * especially used to get the actual values of output variables if a model is connected with other
	 * models.
	 * @param refs : list of variable reference
	 * @return Real values in the same order than refs 
	 */
	List<Double> getReal(List<Integer> refs);

	/**
	 * Get actual values of variables by providing their variable references. [These functions are
	 * especially used to get the actual values of output variables if a model is connected with other
	 * models.
	 * @param refs : list of variable reference
	 * @return int values in the same order than refs 
	 */
	List<Integer> getInteger(List<Integer> refs);
	
	/**
	 * Get actual values of variables by providing their variable references. [These functions are
	 * especially used to get the actual values of output variables if a model is connected with other
	 * models.
	 * @param refs : list of variable reference
	 * @return Boolean values in the same order than refs 
	 */
	List<Boolean> getBoolean(List<Integer> refs);

	/**
	 * Set parameters, inputs, start values and re-initialize caching of variables that depend on these variables
	 * @param ref_values mapped value/reference collection
	 * @return succeed or not 
	 */
	boolean  setReal(Map<Integer, Double> ref_values);
	
	/**
	 * Set parameters, inputs, start values and re-initialize caching of variables that depend on these variables
	 * @param ref_values mapped value/reference collection
	 * @return succeed or not 
	 */
	boolean  setInteger(Map<Integer, Integer> ref_values);
	
	/**
	 * Set parameters, inputs, start values and re-initialize caching of variables that depend on these variables
	 * @param ref_values mapped value/reference collection
	 * @return succeed or not 
	 */
	boolean  setBoolean(Map<Integer, Boolean> ref_values);


	
	/**
	 * Returns true, if the slave wants to terminate the simulation. 
	 * Can be called after doStep(...) returned Discard. 
	 * Use fmi2LastSuccessfulTime to determine the time	instant at which the slave terminated.
	*/
	boolean isTerminated();
	
}
