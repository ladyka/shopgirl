package org.vurtatoo.vk;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vurtatoo.vk.api.Params;
import org.vurtatoo.vk.exception.KException;
import org.vurtatoo.vk.utils.Utils;
import org.vurtatoo.vk.utils.WrongResponseCodeException;

import android.util.Log;

public class VkApi {

    
    public  final String BASE_URL="https://api.vk.com/method/";
    public  final String API_VERSION="5.5";
    
    String access_token;
    String api_id;
    
    
    public  final String TAG="Kate.Api";
    
    public VkApi (VkApiSettings apiSettings) {
    	access_token = apiSettings.getAccess_token();
    	api_id = apiSettings.getAppID();
    }

    
    //TODO: it's not faster, even slower on slow devices. Maybe we should add an option to disable it. It's only good for paid internet connection.
     boolean enable_compression=true;
    
    
    public  <T> String arrayToString(Collection<T> items) {
        if(items==null)
            return null;
        String str_cids = "";
        for (Object item:items){
            if(str_cids.length()!=0)
                str_cids+=',';
            str_cids+=item;
        }
        return str_cids;
    }

    
    public   void addCaptchaParams(String captcha_key, String captcha_sid, Params params) {
        params.put("captcha_sid",captcha_sid);
        params.put("captcha_key",captcha_key);
    }


    private  void checkError(JSONObject root, String url) throws JSONException,KException {
        if(!root.isNull("error")){
            JSONObject error=root.getJSONObject("error");
            int code=error.getInt("error_code");
            String message=error.getString("error_msg");
            KException e = new KException(code, message, url); 
            if (code==14) {
                e.captcha_img = error.optString("captcha_img");
                e.captcha_sid = error.optString("captcha_sid");
            }
            if (code==17)
                e.redirect_uri = error.optString("redirect_uri");
            throw e;
        }
        if(!root.isNull("execute_errors")){
            JSONArray errors=root.getJSONArray("execute_errors");
            if(errors.length()==0)
                return;
            //only first error is processed if there are multiple
            JSONObject error=errors.getJSONObject(0);
            int code=error.getInt("error_code");
            String message=error.getString("error_msg");
            KException e = new KException(code, message, url); 
            if (code==14) {
                e.captcha_img = error.optString("captcha_img");
                e.captcha_sid = error.optString("captcha_sid");
            }
            if (code==17)
                e.redirect_uri = error.optString("redirect_uri");
            throw e;
        }
    }
    
    private final  int MAX_TRIES=3;
    public  JSONObject sendRequest(Params params, boolean is_post) throws IOException, JSONException, KException {
        String url = getSignedUrl(params, is_post);
        String body="";
        if(is_post)
            body=params.getParamsString();
        Log.i(TAG, "url="+url);
        if(body.length()!=0)
            Log.i(TAG, "body="+body);
        String response="";
        for(int i=1;i<=MAX_TRIES;++i){
            try{
                if(i!=1)
                    Log.i(TAG, "try "+i);
                response = sendRequestInternal(url, body, is_post);
                break;
            }catch(javax.net.ssl.SSLException ex){
                processNetworkException(i, ex);
            }catch(java.net.SocketException ex){
                processNetworkException(i, ex);
            }
        }
        Log.i(TAG, "response="+response);
        JSONObject root=new JSONObject(response);
        checkError(root, url);
        return root;
    }

    private  void processNetworkException(int i, IOException ex) throws IOException {
        ex.printStackTrace();
        if(i==MAX_TRIES)
            throw ex;
    }

    private String sendRequestInternal(String url, String body, boolean is_post) throws IOException {
        HttpURLConnection connection=null;
        try{
            connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setUseCaches(false);
            connection.setDoOutput(is_post);
            connection.setDoInput(true);
            connection.setRequestMethod(is_post?"POST":"GET");
            if(enable_compression)
                connection.setRequestProperty("Accept-Encoding", "gzip");
            if(is_post)
                connection.getOutputStream().write(body.getBytes("UTF-8"));
            int code=connection.getResponseCode();
            Log.i(TAG, "code="+code);
            //It may happen due to keep-alive problem http://stackoverflow.com/questions/1440957/httpurlconnection-getresponsecode-returns-1-on-second-invocation
            if (code==-1)
                throw new WrongResponseCodeException("Network error");
            //может стоит проверить на код 200
            //on error can also read error stream from connection.
            InputStream is = new BufferedInputStream(connection.getInputStream(), 8192);
            String enc=connection.getHeaderField("Content-Encoding");
            if(enc!=null && enc.equalsIgnoreCase("gzip"))
                is = new GZIPInputStream(is);
            String response=Utils.convertStreamToString(is);
            return response;
        }
        finally{
            if(connection!=null)
                connection.disconnect();
        }
    }
    
    private  String getSignedUrl(Params params, boolean is_post) {
        params.put("access_token", access_token);
        if(!params.contains("v"))
            params.put("v", API_VERSION);
        
        String args = "";
        if(!is_post)
            args=params.getParamsString();
        
        return BASE_URL+params.method_name+"?"+args;
    }
    
    public  String unescape(String text){
        if(text==null)
            return null;
        return text.replace("&amp;", "&").replace("&quot;", "\"").replace("<br>", "\n").replace("&gt;", ">").replace("&lt;", "<")
        .replace("<br/>", "\n").replace("&ndash;","-").trim();
        //Баг в API
        //amp встречается в сообщении, br в Ответах тип comment_photo, gt lt на стене - баг API, ndash в статусе когда аудио транслируется
        //quot в тексте сообщения из LongPoll - то есть в уведомлении
    }
    
    public  String unescapeWithSmiles(String text){
        return unescape(text)
                //May be useful to someone
                //.replace("\uD83D\uDE0A", ":-)")
                //.replace("\uD83D\uDE03", ":D")
                //.replace("\uD83D\uDE09", ";-)")
                //.replace("\uD83D\uDE06", "xD")
                //.replace("\uD83D\uDE1C", ";P")
                //.replace("\uD83D\uDE0B", ":p")
                //.replace("\uD83D\uDE0D", "8)")
                //.replace("\uD83D\uDE0E", "B)")
                //
                //.replace("\ud83d\ude12", ":(")  //F0 9F 98 92
                //.replace("\ud83d\ude0f", ":]")  //F0 9F 98 8F
                //.replace("\ud83d\ude14", "3(")  //F0 9F 98 94
                //.replace("\ud83d\ude22", ":'(")  //F0 9F 98 A2
                //.replace("\ud83d\ude2d", ":_(")  //F0 9F 98 AD
                //.replace("\ud83d\ude29", ":((")  //F0 9F 98 A9
                //.replace("\ud83d\ude28", ":o")  //F0 9F 98 A8
                //.replace("\ud83d\ude10", ":|")  //F0 9F 98 90
                //                           
                //.replace("\ud83d\ude0c", "3)")  //F0 9F 98 8C
                //.replace("\ud83d\ude20", ">(")  //F0 9F 98 A0
                //.replace("\ud83d\ude21", ">((")  //F0 9F 98 A1
                //.replace("\ud83d\ude07", "O:)")  //F0 9F 98 87
                //.replace("\ud83d\ude30", ";o")  //F0 9F 98 B0
                //.replace("\ud83d\ude32", "8o")  //F0 9F 98 B2
                //.replace("\ud83d\ude33", "8|")  //F0 9F 98 B3
                //.replace("\ud83d\ude37", ":X")  //F0 9F 98 B7
                //                           
                //.replace("\ud83d\ude1a", ":*")  //F0 9F 98 9A
                //.replace("\ud83d\ude08", "}:)")  //F0 9F 98 88
                //.replace("\u2764", "<3")  //E2 9D A4   
                //.replace("\ud83d\udc4d", ":like:")  //F0 9F 91 8D
                //.replace("\ud83d\udc4e", ":dislike:")  //F0 9F 91 8E
                //.replace("\u261d", ":up:")  //E2 98 9D   
                //.replace("\u270c", ":v:")  //E2 9C 8C   
                //.replace("\ud83d\udc4c", ":ok:")  //F0 9F 91 8C
                ;
    }


	public JSONObject sendRequest(Params params) throws JSONException, IOException, KException {
		return sendRequest(params, false);
	}
    

}
