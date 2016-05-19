package com.jb.dissertation.preference;

import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.models.location.Location;

public interface Preference {
	public double getSuitability(Location loc, WebServiceController webServiceController) throws Exception;
}

