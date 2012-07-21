package com.gamezgalaxy.GGS.API;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Method;
public abstract class Event {
	private EventList events = new EventList();
	
	private String name;
	
	public Event() { }
	
	public EventList getEventList() {
		return events;
	}
}