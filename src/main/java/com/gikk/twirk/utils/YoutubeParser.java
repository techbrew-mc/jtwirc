package com.gikk.twirk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeParser
{

    private final static String reg = "(?:youtube(?:-nocookie)?\\.com/(?:[^/\\n\\s]+/\\S+/|(?:v|e(?:mbed)?)/|\\S*?[?&]v=)|youtu\\.be/)([a-zA-Z0-9_-]{11})";

    public static String getVideoId(String videoUrl)
    {
        if (videoUrl == null || videoUrl.trim().length() <= 0)
        {
            return null;
        }

        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }
}
