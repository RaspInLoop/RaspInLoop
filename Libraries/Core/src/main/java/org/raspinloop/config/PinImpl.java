package org.raspinloop.config;

import java.util.EnumSet;


public class PinImpl implements  Pin{

    private String provider;
	private int address;
	private String name;
	private EnumSet<PinMode> supportedPinMode;
	private EnumSet<PinPullResistance> supportedResistance;
	private EnumSet<PinEdge> supportedEdges;
	 
     public PinImpl(String providerName, int address, String name, EnumSet<PinMode> modes, EnumSet<PinPullResistance> resistance, EnumSet<PinEdge> edges) {
		 super();
			this.provider = providerName;
			this.name = name;
			this.address = address;
			this.supportedPinMode = modes;
			this.supportedResistance = resistance;
			this.supportedEdges = edges;
		
     }
	   
	 public PinImpl(String provider,  int address, String name, EnumSet<PinMode> modes ){
		 this(provider, address, name, modes, EnumSet.noneOf(PinPullResistance.class),  EnumSet.noneOf(PinEdge.class) );
	 }
	 
	public PinImpl(String provider, String name, int address) {
		this(provider,  address, name, EnumSet.noneOf(PinMode.class));
	}

	public PinImpl(Pin v) {
		this(v.getProvider(),  v.getName(),  v.getAddress());
	}

	public PinImpl() {	
		this("","",0);
	}
	


@Override
	public String toString() {
		return "PinImpl [name=" + name + ", provider=" + provider + ", address=" + address + "]";
	}

@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + address;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	PinImpl other = (PinImpl) obj;
	if (address != other.address)
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	return true;
};
	
	
	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#getProvider()
	 */
	@Override
	public String getProvider() {		
		return provider;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#getAddress()
	 */
	@Override
	public int getAddress() {		
		return address;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#getSupportedPinModes()
	 */
	@Override
	public EnumSet<PinMode> getSupportedPinModes() {		
		return supportedPinMode;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#getSupportedPinPullResistance()
	 */
	@Override
	public EnumSet<PinPullResistance> getSupportedPinPullResistance() {
		return supportedResistance;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#setProvider(java.lang.String)
	 */
	@Override
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#setAddress(int)
	 */
	@Override
	public void setAddress(int address) {
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.config.SerializablePin#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}




}
