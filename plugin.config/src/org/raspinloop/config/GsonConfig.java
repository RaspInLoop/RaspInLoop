package org.raspinloop.config;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class GsonConfig {

	

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
        	return skip;
        }

    }
	
	public GsonConfig(HardwareEnumerator enumerator) {		
		
		GsonBuilder builder = new GsonBuilder();

		Collection<HardwareConfig> boards = enumerator.buildListImplementing(BoardHardware.class);
		boolean delegateExisting = false;
		for (HardwareConfig hardwareConfig : boards) {
			if (hardwareConfig instanceof BoardHardwareDelegate){
				delegateExisting  = true;
			}
		}
		if (!delegateExisting)
			boards.add(new BoardHardwareDelegate());
		builder = registerImpl(boards, builder, BoardHardware.class);
	
		builder = registerImpl(enumerator.buildListImplementing(BoardExtentionHardware.class), builder, BoardExtentionHardware.class);
		builder = registerImpl(enumerator.buildListImplementing(UARTComponent.class), builder, UARTComponent.class);
		builder = registerImpl(enumerator.buildListImplementing(I2CComponent.class), builder, I2CComponent.class);
		builder = registerImpl(enumerator.buildListImplementing(SPIComponent.class), builder, SPIComponent.class);
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
