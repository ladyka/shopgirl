package org.vurtatoo.vk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.vurtatoo.vk.api.Audio;
import org.vurtatoo.vk.api.Params;
import org.vurtatoo.vk.exception.KException;

public class VkAudioImplementation implements VkAudio {
	
	

	private VkApi vkApi;
	
	public VkAudioImplementation(VkApiSettings apiSettings) {
		vkApi = new VkApi(apiSettings);
	}

	@Override
    public ArrayList<Audio> getAudio(Long uid, Long gid, Long album_id, Collection<Long> aids, String captcha_key, String captcha_sid) throws IOException, JSONException, KException{
        Params params = new Params("audio.get");
        if(uid!=null)
            params.put("owner_id", uid);
        if(gid!=null)
            params.put("owner_id", -gid);
        params.put("audio_ids", vkApi.arrayToString(aids));//не документировано - возможно уже не работает - возможно нужно использовать audio.getById
        if (album_id != null) {
        	params.put("album_id", album_id);        	
        }
        vkApi.addCaptchaParams(captcha_key, captcha_sid, params);
        JSONObject root = vkApi.sendRequest(params);
        JSONObject response=root.optJSONObject("response");
        JSONArray array=response.optJSONArray("items");
        return parseAudioList(array);
    }

	@Override
	public Object getById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLyrics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object search() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getUploadServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object save() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object edit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object reorder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object restore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAlbums() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object addAlbum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object editAlbum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteAlbum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object moveToAlbum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setBroadcast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBroadcastList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRecommendations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPopular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCount() {
		// TODO Auto-generated method stub
		return null;
	}
	
    public static ArrayList<Audio> parseAudioList(JSONArray array)
            throws JSONException {
        ArrayList<Audio> audios = new ArrayList<Audio>();
        if (array != null) {
            for(int i = 0; i<array.length(); ++i) { //get(0) is integer, it is audio count
                JSONObject o = (JSONObject)array.get(i); 
                try {
                	audios.add(Audio.parse(o));
                } catch (Exception ex) {
                	LoggerFactory.getLogger("").error(ex.getLocalizedMessage());
                }
                
            }
        }
        return audios;
    }

}
