package jtwirc.types.subscriberEvent;

import jtwirc.enums.SUB_EVENT;
import jtwirc.types.twitchMessage.DefaultTwitchMessageBuilder;
import jtwirc.types.twitchMessage.TwitchMessage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSubscriberEvent
{
    private static final String SUB_NOTICE = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #gikkman :Gikkman just subscribed!";
    private static final String RESUB_NOTICE = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #gikkman :Slikkman subscribed for 12 months in a row!";
    private static final String RESUB_AWAY = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #gikkman :13 viewers resubscribed while you were away!";

    private static final String SUB_HOST = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #target :Likkman just subscribed to target!";
    private static final String RESUB_HOST = ":twitchnotify!twitchnotify@twitchnotify.tmi.twitch.tv PRIVMSG #target :Trikkman subscribed to target for 2 months in a row!";

    @Test
    public void test()
    {
        testEvent(SUB_NOTICE, SUB_EVENT.NEW_SUB, "Gikkman", 0);
        testEvent(RESUB_NOTICE, SUB_EVENT.RESUB, "Slikkman", 12);
        testEvent(RESUB_AWAY, SUB_EVENT.RESUB_AWAY, "", 13);
        testEvent(SUB_HOST, SUB_EVENT.HOST_NEW, "Likkman", 0);
        testEvent(RESUB_HOST, SUB_EVENT.HOST_RESUB, "Trikkman", 2);
    }

    private static void testEvent(String EVENT, SUB_EVENT EVENT_TYPE, String subscriber, int value)
    {
        TwitchMessage message = new DefaultTwitchMessageBuilder().build(EVENT);
        SubscriberEvent subEvent = new DefaultSubscriberEventBuilder().build(message);

        assertTrue("Got: " + subEvent.getEventType() + " Expected: " + EVENT_TYPE, subEvent.getEventType() == EVENT_TYPE);
        assertTrue("Got: " + subEvent.getSubscriber() + " Expected: " + subscriber, subEvent.getSubscriber().equals(subscriber));
        assertTrue("Got: " + subEvent.getValue() + " Expected: " + value, subEvent.getValue() == value);
        assertTrue("Got: " + subEvent.getRaw() + " Expected: " + EVENT, EVENT.equals(subEvent.getRaw()));
    }


}
