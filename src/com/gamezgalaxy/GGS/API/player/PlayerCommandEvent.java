package com.gamezgalaxy.GGS.API.player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gamezgalaxy.GGS.API.Cancelable;
import com.gamezgalaxy.GGS.API.EventList;
import com.gamezgalaxy.GGS.server.Player;

public class PlayerCommandEvent extends PlayerEvent implements Cancelable {

	private static EventList events = new EventList();
	
	private boolean _canceled; 
	
	private List<String> command;
		
	private String orginalmessage;
	
	public PlayerCommandEvent(Player who, String message) {
		super(who);
		this.orginalmessage = message;
		this.command = this.parseCommand(message);
	}
	
	/**
	 * Parses a command
	 * 
	 * @url http://stackoverflow.com/a/366532/1509091 - Thanks!
	 * @param command
	 * @return
	 */
	public List<String> parseCommand(String command)
	{
		List<String> params = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(command);
		while (regexMatcher.find()) {
			if (regexMatcher.group(1) != null) {
				// Add double-quoted string without the quotes
				params.add(regexMatcher.group(1));
			} else if (regexMatcher.group(2) != null) {
				// Add single-quoted string without the quotes
				params.add(regexMatcher.group(2));
			} else {
				// Add unquoted word
				params.add(regexMatcher.group());
			}
		}
		return params;
	}
	
	@Override
	public EventList getEvents() {
		return events;
	}
	
	public static EventList getEventList() {
		return events;
	}
		
	public String getOrginalMessage() {
		return orginalmessage;
	}
	
	public List<String> getArgs(){
		return this.command;
	}
	
	public void setCommand(List<String> command) {
		this.command = command;
	}

	@Override
	public boolean isCancelled() {
		return _canceled;
	}

	@Override
	public void Cancel(boolean cancel) {
		_canceled = cancel;
	}
}