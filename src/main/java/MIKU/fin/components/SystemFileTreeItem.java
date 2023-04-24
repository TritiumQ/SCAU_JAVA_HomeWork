package MIKU.fin.components;


import MIKU.fin.utils.FileUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Objects;

/**
 * 文件目录树Item类
 * @author TritiumQ
 */
public class SystemFileTreeItem extends TreeItem<String>
{
	private boolean isInitialized = false;
	private final File file;
	
	public SystemFileTreeItem(File file)
	{
		super(FileUtil.getFileSystemName(file), new ImageView(FileUtil.getFileSystemIcon(file)));
		this.file = file;
	}
	
	/**
	 * 重写getChildren(), 展开时加载子文件夹
	 */
	@Override
	public ObservableList<TreeItem<String>> getChildren()
	{
		ObservableList<TreeItem<String>> children = super.getChildren();
		Platform.runLater(()->{
			if(!this.isInitialized && this.isExpanded())
			{
				this.isInitialized = true;
				for(File f : Objects.requireNonNull(file.listFiles()))
				{
					if(f.isDirectory())
						children.add(new SystemFileTreeItem(f));
				}
			}
		});
		return children;
	}
	
	@Override
	public boolean isLeaf()
	{
		return !file.isDirectory();
	}
	
	public File getFile() { return file; }
}