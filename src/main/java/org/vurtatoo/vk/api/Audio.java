package org.vurtatoo.vk.api;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Audio implements Serializable {
	
    private static final long serialVersionUID = 7L;
    public long aid;
    public long owner_id;
    public String artist;
    public String title;
    public long duration;
    public String url;
    public Long lyrics_id;

    /**
     * id: 307551282,
owner_id: 203249956,
artist: 'На Ямайку!',
title: 'БЕЗ БИЛЕТА',
duration: 180,
url: 'https://psv4.vk.m...ZhcH4JCc87tHMtWm8',
genre_id: 3
     * @param o
     * @return
     * @throws NumberFormatException
     * @throws JSONException
     */
    public static Audio parse(JSONObject o) throws NumberFormatException, JSONException{
        Audio audio = new Audio();
        audio.aid = Long.valueOf(String.valueOf(o.get("id")));
        audio.owner_id = Long.valueOf(String.valueOf(o.get("owner_id")));
        audio.artist = Api.unescape(o.optString("artist"));
        audio.title = Api.unescape(o.optString("title"));
        audio.duration = Long.valueOf(String.valueOf(o.get("duration")));
        audio.url = o.optString("url", null);
        
        String tmp=o.optString("lyrics_id");
        if(tmp!=null && !tmp.equals(""))//otherwise lyrics_id=null 
            audio.lyrics_id = Long.parseLong(tmp);
        return audio;
    }
}