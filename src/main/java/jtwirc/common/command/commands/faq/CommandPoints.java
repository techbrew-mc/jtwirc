package jtwirc.common.command.commands.faq;

import jtwirc.TwircBot;
import jtwirc.common.command.CommandBase;
import jtwirc.common.threads.ViewerCommon;
import jtwirc.types.twitchMessage.TwitchMessage;
import jtwirc.types.users.TwitchUser;
import jtwirc.utils.Defaults;
import jtwirc.utils.MessageSending;

import java.util.*;

@SuppressWarnings({"ConstantConditions", "InfiniteLoopStatement"})
public class CommandPoints extends CommandBase
{

    private static String point = Defaults.getPointName();

    public static Thread points = new Thread()
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    if (Defaults.toggleStream)
                    {
                        System.out.println("Auto start giving out " + point);
                        try
                        {
                            CommandPoints.class.newInstance().autoTickPoints();
                            TwircBot.saveAllTheThings();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if (!Defaults.toggleStream)
                    {
                        System.out.println("TwircBot isn't toggled.");
                    }
                    sleep((Defaults.time * 1000 * 60));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                try
                {
                    sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };

    public CommandPoints()
    {
        try
        {
            ViewerCommon.updateViewers();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void autoTickPoints()
    {
        try
        {
            ViewerCommon.updateViewers();
            addAll(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addPoints(String user, Long amount, boolean auto)
    {
        if (!TwircBot.blackList.contains(user))
        {
            if (TwircBot.userList.get(user) != null)
            {
                TwircBot.userList.put(user, (TwircBot.userList.get(user) + amount));
                Defaults.totalPoints += amount;
            }
            else
            {
                TwircBot.userList.put(user, amount);
                Defaults.totalPoints += amount;
            }
        }
        else
        {
            if (!auto)
            {
                MessageSending.sendNormalMessage(user + " is on the blacklist. They can't gain " + Defaults.getPointName());
            }
        }
    }

    @Override
    public void channelCommand(TwitchUser user, TwitchMessage message)
    {
        super.channelCommand(user, message);
        if (args.length <= 1)
        {
            if (TwircBot.blackList.contains(user.getName()))
            {
                MessageSending.sendWhisper(user.getName().toLowerCase(), "Sorry, " + user.getName() + " you are blacklisted and are not eligible to gain any " + Defaults.getPointName() + ". Please contact a mod if you think this is not correct");
            }
            else
            {
                MessageSending.sendWhisper(user.getName(), "You have a total of " + getPoints(user.getName().toLowerCase()) + " " + Defaults.getPointName() + "!");
            }
        }
        else
        {
            if (args[1].equalsIgnoreCase("give"))
            {
                if (user.isMod() || user.isBroadcaster())
                {
                    if (args.length >= 3)
                    {
                        addPoints(args[2].toLowerCase(), Long.parseLong(args[3]), false);
                        if (!TwircBot.blackList.contains(args[2].toLowerCase()))
                        {
                            MessageSending.sendNormalMessage(args[3] + " " + Defaults.getPointName() + " have been added to " + args[2] + " total.");
                            TwircBot.log.info("Gave away " + args[3] + " points to " + args[2]);
                        }
                    }
                    else
                    {
                        MessageSending.sendNormalMessage("Incorrect syntax. Use : !" + Defaults.getPointName() + " give &lt;user&gt; &lt;amount&gt;");
                    }
                }
            }
            if (args[1].equalsIgnoreCase("clear"))
            {
                if (user.isMod() || user.isBroadcaster())
                {
                    if (args.length >= 2)
                    {
                        if (TwircBot.userList.get(args[2].toLowerCase()) != null)
                        {
                            TwircBot.userList.remove(args[2].toLowerCase());
                            MessageSending.sendNormalMessage(args[2] + " has been removed from the points list.");
                        }
                    }
                }
            }
            if (args[1].equalsIgnoreCase("check"))
            {
                if (user.isMod() || user.isBroadcaster())
                {
                    if (args.length >= 2)
                    {
                        MessageSending.sendNormalMessage(args[2] + " has a total of " + getPoints(args[2]) + " " + Defaults.getPointName());
                    }
                    else
                    {
                        MessageSending.sendNormalMessage("Incorrect syntax. Use : !" + Defaults.getPointName() + " check &lt;name&gt;");
                    }
                }
            }
            if (args[1].equalsIgnoreCase("giveall"))
            {
                if (user.isMod() || user.isBroadcaster())
                {
                    if (args.length >= 2)
                    {
                        addAll(Long.parseLong(args[2]));
                        MessageSending.sendNormalMessage("Everyone got " + args[2] + " " + Defaults.getPointName() + "!");
                        TwircBot.log.info("Gave away " + args[2] + " points to everyone");
                        TwircBot.saveAllTheThings();
                    }
                    else
                    {
                        MessageSending.sendNormalMessage("Incorrect syntax. Use : !" + Defaults.getPointName() + " giveall &lt;amount&gt;");
                    }
                }
            }
            if (args[1].equalsIgnoreCase("top"))
            {
                if (user.isSub() || user.isBroadcaster() || user.isMod())
                {
                    getTopList();
                }
            }
        }
    }

    private void getTopList()
    {
        Set<Map.Entry<String, Long>> set = TwircBot.userList.entrySet();
        List<Map.Entry<String, Long>> list = new ArrayList<>(set);
        Collections.sort(list, (a, b) -> b.getValue().compareTo(a.getValue()));


        String[] first = String.valueOf(list.get(0)).split("=");
        String[] second = String.valueOf(list.get(1)).split("=");
        String[] third = String.valueOf(list.get(2)).split("=");
        String[] fourth = String.valueOf(list.get(3)).split("=");
        String[] fifth = String.valueOf(list.get(4)).split("=");


        MessageSending.sendNormalMessage("1st. " + first[0] + " with " + first[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("2nd. " + second[0] + " with " + second[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("3rd. " + third[0] + " with " + third[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("4th. " + fourth[0] + " with " + fourth[1] + " " + Defaults.getPointName());
        MessageSending.sendNormalMessage("5th. " + fifth[0] + " with " + fifth[1] + " " + Defaults.getPointName());
    }

    private void addAll(long amount)
    {
        try
        {
            ViewerCommon.updateViewers();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < ViewerCommon.viewers.size(); i++)
        {
            if (!TwircBot.blackList.contains(ViewerCommon.viewers.get(i)))
            {
                addPoints(ViewerCommon.viewers.get(i), amount, false);
            }
        }
    }

    private String getPoints(String user)
    {
        if (TwircBot.userList.get(user) == null)
        {
            return "0";
        }
        else
        {
            return TwircBot.userList.get(user).toString();
        }
    }
}