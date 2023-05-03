package MIKU.fin.module;


import MIKU.fin.utils.FileUtil;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 *  可序列化的收藏夹数据
 *  @author TritiumQ
 */
public class SerializableFavoritesData implements Serializable
{
	private String[] favorites;
	public String[] getFavorites() {
		return favorites;
	}
	
	public void setFavorites(String[] favorites) {
		this.favorites = favorites;
	}
	
	public SerializableFavoritesData()
	{
		favorites = new String[0];
	}
	public SerializableFavoritesData(ImageFile[] favorites)
	{
		this.favorites = new String[favorites.length];
		for (int i = 0; i < favorites.length; i++)
		{
			this.favorites[i] = favorites[i].getRawFile().getAbsolutePath();
		}
	}
	
	/**
	 * 添加一个收藏
	 * @param file
	 */
	public void add(ImageFile file)
	{
		String[] newFavorites = new String[favorites.length + 1];
		System.arraycopy(favorites, 0, newFavorites, 0, favorites.length);
		newFavorites[favorites.length] = file.getRawFile().getAbsolutePath();
		favorites = newFavorites;
	}
	
	/**
	 * 添加多个收藏
	 * @param files
	 */
	public void addAll(ImageFile[] files)
	{
		for (ImageFile file : files)
		{
			add(file);
		}
	}
	
	/**
	 * 移除一个收藏
	 * @param file
	 */
	public void remove(ImageFile file)
	{
		List<String> list = new LinkedList<>(List.of(favorites));
		list.remove(file.getRawFile().getAbsolutePath());
		favorites = list.toArray(new String[0]);
	}
	
	/**
	 * 移除多个收藏
	 * @param files
	 */
	public void removeAll(ImageFile[] files)
	{
		for (ImageFile file : files)
		{
			remove(file);
		}
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("FavoritesData:\n");
		for (String favorite : favorites)
		{
			sb.append(favorite).append("\n");
		}
		return sb.toString();
	}
}
