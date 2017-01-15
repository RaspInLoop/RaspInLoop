package org.raspinloop.config;

import java.util.Collection;



public interface BoardExtentionHardware extends HardwareConfig{

	public BoardExtentionHardware setParent(BoardHardware sd);
	
	public BoardHardware getParentComponent();

	public Collection<Pin> getUsedPins();

}