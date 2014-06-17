package org.dreamhead.shop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkAPI {
	
	private String access_token;
	private String expires_in;
	private String user_id;
	private Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	
	String appID;
	String appSecret;
	String uriWebApp;
	
	
	public VkAPI(String appID, String appSecret ,String uriWebApp) {
		this.appID = appID;
		this.appSecret = appSecret;
		this.uriWebApp = uriWebApp;
	}
	
	/**
	 * v4.99
	 * Для вызова этого метода Ваше приложение должно иметь следующие права: wall и friends.
	 * filters	перечисленные через запятую названия списков новостей, которые необходимо получить. В данный момент поддерживаются следующие списки новостей:
	 * 	 post — новые записи со стен
	 * 	 photo — новые фотографии
	 * 	 photo_tag — новые отметки на фотографиях
	 * 	 wall_photo — новые фотографии на стенах
	 * 	 friend — новые друзья
	 * 	 note — новые заметки
	 * Если параметр не задан, то будут получены все возможные списки новостей.
	 * 
	 * allow_group_comments	1 — возвращать информацию о комментариях от имени сообщества.
	 * start_time	время в формате unixtime, начиная с которого следует получить новости для текущего пользователя.
	 * end_time	время в формате unixtime, до которого следует получить новости для текущего пользователя. Если параметр не задан, то он считается равным текущему времени.
	 * max_photos	Максимальное количество фотографий, информацию о которых необходимо вернуть. По умолчанию 5.
	 * 
	 * source_ids	перечисленные через запятую иcточники новостей, новости от которых необходимо получить.
	 * Идентификаторы пользователей можно указывать в форматах <uid> или u<uid> где <uid> — идентификатор друга пользователя.
	 * Идентификаторы сообществ можно указывать в форматах -<gid> или g<gid> где <gid> — идентификатор сообщества.
	 * Помимо этого параметр может принимать строковые значения:
	 *         friends - список друзей пользователя
	 *         groups - список групп, на которые подписан текущий пользователь
	 *         pages - список публичных страниц, на который подписан тeкущий пользователь
	 *         following - список пользователей, на которых подписан текущий пользователь
	 *         list{идентификатор списка новостей} - список новостей. Вы можете найти все списки новостей пользователя используя метод newsfeed.getLists
	 *     Если параметр не задан, то считается, что он включает список всех друзей и групп пользователя, за исключением скрытых источников, которые можно получить методом newsfeed.getBanned.
	 * from	значение, полученное в поле new_from при последней загрузке новостей. Помогает избавляться от дубликатов при реализации автоподгрузки.
	 * offset	указывает, начиная с какого элемента в данном промежутке времени необходимо получить новости. По умолчанию 0. Для автоподгрузки Вы можете использовать возвращаемый данным методом параметр new_offset.
	 * count	указывает, какое максимальное число новостей следует возвращать, но не более 100. По умолчанию 50.
	 * @return Ответ JSON STRING
	 */
	public String newsfeedGet(
			List<String> filters,
			int allow_group_comments,
			long start_time,
			long end_time,
			int max_photos,
			List<String> source_ids,
			String from,
			int offset,
			int count
			) {
		URIBuilder uriBuilder = getDefaultURIBuilder();
		uriBuilder.setPath("/method/newsfeed.get");
		uriBuilder.setParameter("access_token",access_token);
		uriBuilder.setParameter("user_id",user_id);
		
		
		if ((!filters.isEmpty()) || (filters != null)) {
			StringBuilder filters_parameters = new StringBuilder();
			for (String string : filters) {
				filters_parameters.append(string + ",");
			}
			filters_parameters.deleteCharAt(filters_parameters.length() - 1 );
			uriBuilder.setParameter("filters", filters_parameters.toString());
		}
		
		try {
			return getResponse(new HttpPost(uriBuilder.build()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
	 * Parse
	 * https://oauth.vk.com/blank.html#access_token=33e3c7e1f3b4e4f062d1e99c932db7846f5af1ad1ad51e86a3b74b70d3e3ba092343933bd6f95e92d933c&expires_in=86400&user_id=203249956
     * notify   (+1) 	Пользователь разрешил отправлять ему уведомления.
     * friends  (+2) 	Доступ к друзьям.
     * photos   (+4) 	Доступ к фотографиям.
     * audio    (+8) 	Доступ к аудиозаписям.  
     * video    (+16) 	Доступ к видеозаписям.
     * docs     (+131072) 	Доступ к документам.
     * notes    (+2048) 	Доступ заметкам пользователя.
     * pages    (+128) 	Доступ к wiki-страницам.
     * status   (+1024) 	Доступ к статусу пользователя.
     * wall     (+8192) 	Доступ к обычным и расширенным методам работы со стеной.
     * groups   (+262144) 	Доступ к группам пользователя.
     * messages (+4096) 	(для Standalone-приложений) Доступ к расширенным методам работы с сообщениями.
     * notifications(+524288) 	Доступ к оповещениям об ответах пользователю.
     * stats    (+1048576) 	Доступ к статистике групп и приложений пользователя, администратором которых он является.
     * offline  (+65536) 	Доступ к API в любое время со стороннего сервера (при использовании этой опции параметр expires_in, возвращаемый вместе с access_token, содержит 0 — токен бессрочный). 
	 * @param maybe
     * scope=notify,friends,photos,audio,video,docs,notes,pages,status,wall,groups,messages,notifications,stats,offline
	 */
	public Boolean parseURI(String maybe) {
		try {
			access_token = maybe.substring(maybe.indexOf("access_token") + 13,
					maybe.indexOf("&"));

			int expires_in_start = maybe.indexOf("expires_in");
			expires_in = maybe.substring(expires_in_start + 11,
					maybe.indexOf("&", expires_in_start));

			user_id = maybe.substring(maybe.indexOf("user_id") + 8, maybe.length());
			return true;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return false;
		}
	}


	
	

    
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@SuppressWarnings({ "unchecked" })
	private String getResponse(HttpGet httpget) {
        HttpClient httpclient = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();        
        try {
        	String responseBody = httpclient.execute(httpget, handler);
            logger.debug("getResponse(HttpGet httpget)\n" + httpget.getURI() + "\n" + responseBody);
            return responseBody;
        } catch (IOException ex) {
        	logger.error("getResponse(HttpGet httpget)\n" + httpget.getURI() + "\n" + ex.getMessage());
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("succex", false);
        	return jsonObject.toJSONString();
        }
    }

	@SuppressWarnings("unchecked")
	private String getResponse(HttpPost httppost) {
        HttpClient httpclient = new DefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();        
        try {
        	String responseBody = httpclient.execute(httppost, handler);
            logger.info("getResponse(HttpPost httppost)\n" + httppost.getURI() + "\n" + responseBody);
            return responseBody;
        } catch (IOException ex) {
        	logger.error("getResponse(HttpPost httppost)\n" + httppost.getURI() + "\n" + ex.getMessage());
        	JSONObject jsonObject = new JSONObject();
        	jsonObject.put("succex", false);
        	return jsonObject.toJSONString();
        }
    }

	public String autorizeWebSite(String code) {
		try {
			URIBuilder uriBuilder = new URIBuilder();
			uriBuilder.setScheme("https");
			uriBuilder.setHost("oauth.vk.com/");
			uriBuilder.setPath("access_token");
			uriBuilder.setParameter("client_id",appID);
			uriBuilder.setParameter("client_secret",appSecret);
			uriBuilder.setParameter("code",code);
			uriBuilder.setParameter("redirect_uri",uriWebApp);
			URI a = uriBuilder.build();
			logger.info("URI : "+  a.toString());
			HttpPost httpPost = new HttpPost(a);
			return getResponse(httpPost);
		} catch (Exception ex) {
			return ex.getLocalizedMessage();
		}
	}
	
	public static URIBuilder getDefaultURIBuilder() {
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme("https");
		uriBuilder.setHost("api.vk.com");
		return uriBuilder;
	}
}