package org.raspinloop.fmi.plugin.preferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.raspinloop.config.BoardExtentionHardware;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.BoardHardwareDelegate;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.config.I2CComponent;
import org.raspinloop.config.I2CParent;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.SPIComponent;
import org.raspinloop.config.SPIParent;
import org.raspinloop.config.UARTComponent;
import org.raspinloop.config.UARTParent;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class GsonConfig {

	
	final static Logger logger = Logger.getLogger(GsonConfig.class);

	private Gson gsonExt;

	public class PreventLoop implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }
        //Board and BoardExtenstion have mutual link, we have to break one of them 
        public boolean shouldSkipField(FieldAttributes f) {

        	boolean skip = 
             (BoardExtentionHardware.class.isAssignableFrom(f.getDeclaringClass()) && f.getName().equals("parent"))||
            		(UARTComponent.class.isAssignableFrom(f.getDeclaringClass()) && f.getName().equals("parent"))||
            		(I2CComponent.class.isAssignableFrom(f.getDeclaringClass()) && f.getName().equals("parent"))||
            		(SPIComponent.class.isAssignableFrom(f.getDeclaringClass()) && f.getName().equals("parent"));
        	logger.debug(f.getDeclaringClass().getSimpleName()+" - "+f.getName()+ (skip?"-":"+"));
        	return skip;
        }

    }
	
	public GsonConfig() {

		Logger.getRootLogger().setLevel(Level.OFF);
		ConsoleAppender console = new ConsoleAppender(); //create appender
		  //configure the appender
		  String PATTERN = "%d [%p|%c{1}] %m%n";
		  console.setLayout(new PatternLayout(PATTERN)); 
		  console.setThreshold(Level.FATAL);
		  console.activateOptions();
		  //add appender to any Logger (here is root)
		  Logger.getRootLogger().addAppender(console);
		
		GsonBuilder builder = new GsonBuilder();

		ArrayList<HardwareConfig> boards = HardwareUtils.buildHardwareListImplementing(BoardHardware.class);
		boards.add(new BoardHardwareDelegate());
		builder = registerImpl(boards, builder, BoardHardware.class);
	
		builder = registerImpl(HardwareUtils.buildHardwareListImplementing(BoardExtentionHardware.class), builder, BoardExtentionHardware.class);
		builder = registerImpl(HardwareUtils.buildHardwareListImplementing(UARTComponent.class), builder, UARTComponent.class);
		builder = registerImpl(HardwareUtils.buildHardwareListImplementing(I2CComponent.class), builder, I2CComponent.class);
		builder = registerImpl(HardwareUtils.buildHardwareListImplementing(SPIComponent.class), builder, SPIComponent.class);
		builder.registerTypeAdapter(Pin.class, new InstanceCreator<PinImpl>() {

			public PinImpl createInstance(Type type) {
				return new PinImpl();
			}
		});
		
		builder.setExclusionStrategies(new PreventLoop());
		
		gsonExt = builder.setPrettyPrinting().create();
	}

	@SuppressWarnings("unchecked")
	private <T> GsonBuilder registerImpl(Collection<HardwareConfig> objects, GsonBuilder builder, Class<T> type) {
		RuntimeTypeAdapterFactory<T> typeFactory = RuntimeTypeAdapterFactory.of(type, "java_type");

		for (HardwareConfig obj : objects) {
			if (type.isInstance(obj)) {
				logger.debug("registering " + obj.getClass().getName() + " as impl of " + type.getName());
				typeFactory.registerSubtype((Class<? extends T>)obj.getClass(), obj.getClass().getName());
			}
		}
		return builder.registerTypeAdapterFactory(typeFactory);
	}

	public String write(BoardHardware hd) {
		return gsonExt.toJson(hd, hd.getClass());
	}

	public BoardHardware read(String json) {
		BoardHardware board =  gsonExt.fromJson(json, BoardHardware.class);		
		restoreParentLink(board); // because parent field is ignored in json serialisation
		return board;
	}

	private void restoreParentLink(BoardHardware board) {
		for (BoardExtentionHardware extention : board.getComponents()) {
			extention.setParent(board);
			if (extention instanceof I2CParent)
				restoreParentLink((I2CParent)extention);
			if (extention instanceof UARTParent)
				restoreParentLink((UARTParent)extention);
			if (extention instanceof SPIParent)
				restoreParentLink((SPIParent)extention);
		}		
		if (board instanceof I2CParent)
			restoreParentLink((I2CParent)board);
		if (board instanceof UARTParent)
			restoreParentLink((UARTParent)board);
		if (board instanceof SPIParent)
			restoreParentLink((SPIParent)board);
	}
	
	private void restoreParentLink(SPIParent extention) {
		for (SPIComponent child : extention.getSPIComponent()) {
			if (child instanceof I2CParent)
				restoreParentLink((I2CParent)child);
			if (child instanceof UARTParent)
				restoreParentLink((UARTParent)child);
			if (child instanceof SPIParent)
				restoreParentLink((SPIParent)child);
		}		
	}

	private void restoreParentLink(UARTParent extention) {
		for (UARTComponent child : extention.getUARTComponent()) {
			if (child instanceof I2CParent)
				restoreParentLink((I2CParent)child);
			if (child instanceof UARTParent)
				restoreParentLink((UARTParent)child);
			if (child instanceof SPIParent)
				restoreParentLink((SPIParent)child);
		}		
	}

	private void restoreParentLink(I2CParent i2cParent){
		for (I2CComponent child : i2cParent.getI2cComponent()) {
			if (child instanceof I2CParent)
				restoreParentLink((I2CParent)child);
			if (child instanceof UARTParent)
				restoreParentLink((UARTParent)child);
			if (child instanceof SPIParent)
				restoreParentLink((SPIParent)child);
		}				
	}
}
