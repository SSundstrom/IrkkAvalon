package AvalonGame.ircframework.core.events;


public class OnDayChangedEvent extends IrcEvent {

	@Override
	public String getEventName() {
		return "DayChangedEvent";
	}

}
