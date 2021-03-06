package jtwirc.common.command.commands.util;

import jtwirc.Twirc;
import jtwirc.TwircBot;
import jtwirc.common.command.CommandBase;
import jtwirc.types.twitchMessage.TwitchMessage;
import jtwirc.types.users.TwitchUser;
import jtwirc.utils.MessageSending;

public class CommandExit extends CommandBase
{
    private static void exit()
    {
        new Thread("App-exit")
        {
            @Override
            public void run()
            {
                System.exit(0);
            }
        }.start();
    }

    @Override
    public void channelCommand(TwitchUser user, TwitchMessage message)
    {
        super.channelCommand(user, message);
        if (user.isMod() || user.isBroadcaster())
        {
            MessageSending.sendNormalMessage("Shutting down the bot.");
            System.out.println("Shutting down.");
            TwircBot.bots.forEach(Twirc::close);
            System.out.println("Shut down!");
            exit();
        }
    }
}
