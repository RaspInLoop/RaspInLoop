package org.raspinloop.config;

import java.util.Collection;



public interface BoardExtentionHardware extends HardwareProperties{

	public BoardExtentionHardware setParent(BoardHardware sd);
	
	public BoardHardware getParentComponent();

	public Collection<Pin> getUsedPins();

}