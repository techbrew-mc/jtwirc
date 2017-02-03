package com.gikk.twirk.enums;

/**
 * Enum for representing different types of users, as labeled by Twitch. <br>
 * DEFAULT in this case means that no information was given, and it can be assumed that a user
 * with DEFAULT user type is a normal user, without any special privileges is this channel.<br><br>
 * <p>
 * USER_TYPE comes with a value. OWNER = 9, MOD = 2, GLOBAL_MOD = ADMIN = STAFF = 1, DEFAULT = 0.<br>
 * These values can be used to make sure that only users of a certain type can do something.<br><br>
 * <p>
 * For example:<br>
 * <pre><code>if( user.USER_TYPE.value &gt;= USER_TYPE.MOD.value )</code>
 * 	<code>doSomething();</code></pre>
 */
public enum USER_TYPE
{
    /**
     * Type for the owner of the channel (i.e. the user with the
     * same user name as the channel's name. This type has the highest value, 9
     */
    OWNER(9),
    /**
     * Type for mods in the channel. This type has the second highest value, 2
     */
    MOD(2),
    /**
     * Type for Twitch's global mods. This type has a value of 1
     */
    GLOBAL_MOD(1),
    /**
     * Type for Twitch's admins. This type has a value of 1
     */
    ADMIN(1),
    /**
     * Type for Twitch's staff members. This type has a value of 1
     */
    STAFF(1),
    /**
     * Type for users that does not fit into any other classes. This type has a value of 0
     */
    DEFAULT(0);

    /**
     * This types value. Useful for regulating which kind of users should trigger / can perform
     * certain actions. <br><br>
     * <p>
     * For example:<br>
     * <pre><code>if( user.USER_TYPE.value == USER_TYPE.MOD.value )</code>
     * 	<code>doSomething();</code></pre>
     */
    public final int value;

    USER_TYPE(int value)
    {
        this.value = value;
    }
}