package jtwirc.types.hostTarget;

import jtwirc.enums.HOSTTARGET_MODE;
import jtwirc.types.AbstractType;

/**
 * Class for representing a HOSTTARGET from Twitch.<br><br>
 * <p>
 * A HOSTTARGET means that we started hosting another. HOSTTARGET comes in two forms: START and STOP. <ul>
 * <li>START - A host starts. <code>hoster</code> tells us who we started hosting and <code>viewerAmount</code> tells us how many viewers we had
 * <li>STOP - A host ended.  <code>hoster</code> tells us who we stopped and <code>viewerAmount</code> tells us how many viewers we gave
 * </ul>
 */
public interface HostTargetEvent extends AbstractType
{

    /**
     * Tells which MODE this HOSTTARGET has (START or STOP)
     *
     * @return This {@link HOSTTARGET_MODE}
     */
    HOSTTARGET_MODE getMode();

    /**
     * Tells us which channel we started hosting with this HOSTTARGET
     *
     * @return Name of channel we started hosting
     */
    String getTarget();

    /**
     * Tells us how many viewers this HOSTTARGET affected (MODE:START adds viewers, MODE:STOP removes viewers)
     *
     * @return Amount of viewers we contribute/contributed to the target
     */
    int getViewerCount();

}
