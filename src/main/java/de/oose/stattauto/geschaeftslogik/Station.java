package de.oose.stattauto.geschaeftslogik;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

public class Station {
	
	private final String name;

	public Station(String name) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(name));
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
