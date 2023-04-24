package MIKU.fin.module;


import MIKU.fin.utils.FileUtil;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class SerializableFavoritesData implements Serializable
{
	private List<String> list;
	
	public SerializableFavoritesData()
	{
		try{
			File f = new File(FileUtil.PATH_APP_DATA+"\\ImageViewer\\FavoritesList");
			f.createNewFile();
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SerializableFavoritesData data = (SerializableFavoritesData) ois.readObject();
			if(data != null)
				list = data.list;
			else
				list = new LinkedList<>();
			ois.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void add(File imageFile)
	{
		list.add(imageFile.getAbsolutePath());
	}
	public void remove(File imageFile)
	{
		list.remove(imageFile.getAbsolutePath());
	}
	
	public void save()
	{
		try{
			File f = new File(FileUtil.PATH_APP_DATA+"\\ImageViewer\\FavoritesList");
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
