package org.vurtatoo.vk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.vurtatoo.vk.api.Audio;
import org.vurtatoo.vk.exception.KException;

//Аудиозаписи
public interface VkAudio {


	/**
	 * owner_id	идентификатор владельца аудиозаписей (пользователь или сообщество).

    Обратите внимание, идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-" — например, owner_id=-1 соответствует идентификатору сообщества ВКонтакте API (club1) 


int (числовое значение), по умолчанию идентификатор текущего пользователя
album_id	идентификатор альбома с аудиозаписями.
int (числовое значение)
audio_ids	Идентификаторы аудиозаписей, информацию о которых необходимо вернуть.
список положительных чисел, разделенных запятыми
need_user	1 — возвращать информацию о пользователях, загрузивших аудиозапись.
флаг, может принимать значения 1 или 0
offset	смещение, необходимое для выборки определенного количества аудиозаписей. По умолчанию — 0.
положительное число
count	количество аудиозаписей, информацию о которых необходимо вернуть. Максимальное значение — 6000.
положительное число

	
	*/
	
	/**
	 * 
	 * @param uid
	 * @param gid
	 * @param album_id
	 * @param aids
	 * @param captcha_key
	 * @param captcha_sid
	 * @return Возвращает список аудиозаписей пользователя или сообщества.
	 * @throws IOException
	 * @throws JSONException
	 * @throws KException
	 */
	public ArrayList<Audio> getAudio(Long uid, Long gid, Long album_id, Collection<Long> aids, String captcha_key, String captcha_sid) throws IOException, JSONException, KException;
	
	
	public Object getById();
//	Возвращает информацию об аудиозаписях.
//
	public Object getLyrics();
//	Возвращает текст аудиозаписи.
//
	public Object search();
//	Возвращает список аудиозаписей в соответствии с заданным критерием поиска.
//
	public Object getUploadServer();
//	Возвращает адрес сервера для загрузки аудиозаписей.
//
	public Object save();
//	Сохраняет аудиозаписи после успешной загрузки.
//
	public Object add();
//	Копирует аудиозапись на страницу пользователя или группы.
//
	public Object delete();
//	Удаляет аудиозапись со страницы пользователя или сообщества.
//
	public Object edit();
//	Редактирует данные аудиозаписи на странице пользователя или сообщества.
//
	public Object reorder();
//	Изменяет порядок аудиозаписи, перенося ее между аудиозаписями, идентификаторы которых переданы параметрами after и before.
//
	public Object restore();
//	Восстанавливает аудиозапись после удаления.
//
	public Object getAlbums();
//	Возвращает список альбомов аудиозаписей пользователя или группы.
//
	public Object addAlbum();
//	Создает пустой альбом аудиозаписей.
//
	public Object editAlbum();
//	Редактирует название альбома аудиозаписей.
//
	public Object deleteAlbum();
//	Удаляет альбом аудиозаписей.
//
	public Object moveToAlbum();
//	Перемещает аудиозаписи в альбом.
//
	public Object setBroadcast();
//	Транслирует аудиозапись в статус пользователю или сообществу.
//
	public Object getBroadcastList();
//	Возвращает список друзей и сообществ пользователя, которые транслируют музыку в статус.
//
	public Object getRecommendations();
//	Возвращает список рекомендуемых аудиозаписей на основе списка воспроизведения заданного пользователя или на основе одной выбранной аудиозаписи.
//
	public Object getPopular();
//	Возвращает список аудиозаписей из раздела "Популярное".
//
	public Object getCount();
//	Возвращает количество аудиозаписей пользователя или сообщества.
}
