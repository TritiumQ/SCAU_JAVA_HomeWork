package MIKU.fin.utils;

import MIKU.fin.module.ImageFile;
import MIKU.fin.module.SerializableFavoritesData;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 收藏夹工具类
 * @author TritiumQ
 */
public class FavoritesUtils
{
	/**
	 * 默认收藏夹保存路径
	 */
	public static final String DEFAULT_SAVE_PATH = "Favorites.dat";
	
	/**
	 * 初始化收藏夹
	 */
	private static void intiFavorites()
	{
		try
		{
			File file = new File(DEFAULT_SAVE_PATH);
			if(!file.exists())
			{
				file.createNewFile();
				SerializableFavoritesData data = new SerializableFavoritesData(new ImageFile[0]);
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEFAULT_SAVE_PATH));
				oos.writeObject(data);
				oos.close();
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取收藏夹对象
	 * @return
	 */
	public static SerializableFavoritesData getFavorites()
	{
		try{
			File file = new File(DEFAULT_SAVE_PATH);
			if(!file.exists())
			{
				intiFavorites();
				return new SerializableFavoritesData(new ImageFile[0]);
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DEFAULT_SAVE_PATH));
			SerializableFavoritesData data = (SerializableFavoritesData) ois.readObject();
			System.out.println(data);
			ois.close();
			return data;
		}catch (Exception e)
		{
			e.printStackTrace();
			return new SerializableFavoritesData(new ImageFile[0]);
		}
		
	}
	
	/**
	 * 保存收藏夹对象
	 * @param data
	 */
	public static void saveFavorites(SerializableFavoritesData data)
	{
		try
		{
			LinkedHashSet<String> set = new LinkedHashSet<>(List.of(data.getFavorites()));
			System.out.println(set);
			data.setFavorites(set.toArray(new String[0]));
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEFAULT_SAVE_PATH));
			oos.writeObject(data);
			oos.close();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取收藏夹中的图片文件
	 * @return
	 */
	
	public static ImageFile[] getFavoritesAsImageFile()
	{
		try{
			File file = new File(DEFAULT_SAVE_PATH);
			if(!file.exists())
			{
				intiFavorites();
				return new ImageFile[0];
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DEFAULT_SAVE_PATH));
			SerializableFavoritesData data = (SerializableFavoritesData) ois.readObject();
			System.out.println(data);
			ois.close();
			List<ImageFile> list = new ArrayList<>();
			for (String path : data.getFavorites())
			{
				list.add(new ImageFile(path));
			}
			return list.toArray(new ImageFile[0]);
		}catch (Exception e)
		{
			e.printStackTrace();
			return new ImageFile[0];
		}
		
	}
	
	/**
	 * 添加收藏
	 * @param imageFiles
	 */
	public static void addFavorites(ImageFile[] imageFiles)
	{
		try
		{
			SerializableFavoritesData data = getFavorites();
			data.addAll(imageFiles);
			saveFavorites(data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 移除收藏
	 * @param imageFiles
	 */
	public static void removeFavorites(ImageFile[] imageFiles)
	{
		try
		{
			SerializableFavoritesData data = getFavorites();
			data.removeAll(imageFiles);
			saveFavorites(data);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
