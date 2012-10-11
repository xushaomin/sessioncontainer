package org.sessioncontainer.client.service;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NodeAddress {
	private final InetSocketAddress[] addressArray;
	public NodeAddress(String addressStr){
		Set<InetSocketAddress> addressSet = new HashSet<InetSocketAddress>();
		String[] ipAddresses = addressStr.split(";");
		for(String ipAddress : ipAddresses){
			String[] strArrayTmp = ipAddress.split(":");
			if (strArrayTmp.length > 1) {
				addressSet.add(new InetSocketAddress(strArrayTmp[0], Integer.parseInt(strArrayTmp[1])));
			}
		}
		this.addressArray = addressSet.toArray(new InetSocketAddress[addressSet.size()]);
	}
	public InetSocketAddress[] getAddressArray() {return addressArray;}
}
