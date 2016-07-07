package ftp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.sshd.common.file.util.ImmutableList;

import ftp.v4.My4Path;

public class MyFileResource
{
  public static final MyFileResource INSTANCE = new MyFileResource();
  private static final String ROOT_NAME = "/";
  private static final String FOLDER_1_NAME = "/folder1";
  private static final String FOLDER_2_NAME = "/folder2";
  private static final String FILE_NAME = "/file";
  private static final long FOLDER_SIZE = 0L;
  private final List<Path> rootSubResources = new ArrayList<>();//模擬某用戶儲存在DB的目錄檔案清單
  private final Map<String, MyFileAttributes> fileAttributes = new HashMap<>();//模擬儲存在DB的目錄檔案屬性
  private Path fakeFile = null;

  private MyFileResource()
  {
    try
    {
      fakeFile = Files.createTempFile("test_", ".txt");
      fakeFile.toFile().deleteOnExit();//恩～沒用處。
      Files.write(fakeFile, Arrays.asList(new String[]{"1"}), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
      System.out.println("MyFileResource fake file=" + fakeFile.toString());

      fileAttributes.put(ROOT_NAME, createFileAttributes(true, FOLDER_SIZE));
      fileAttributes.put(ROOT_NAME, createFileAttributes(true, FOLDER_SIZE));
      fileAttributes.put(FOLDER_1_NAME, createFileAttributes(true, FOLDER_SIZE));
      fileAttributes.put(FOLDER_2_NAME, createFileAttributes(true, FOLDER_SIZE));
      fileAttributes.put(FILE_NAME, createFileAttributes(false, Files.size(fakeFile)));
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
  }

  public Iterator<Path> getSubresources(long accountId, Path path)
  {
    My4Path myPath = (My4Path)path;
    List<Path> paths = new ArrayList<>();
    switch(path.toString())
    {
      case "/":
        if(rootSubResources.isEmpty())
        {
          rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{"folder1"})));
          rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{"folder2"})));
          rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{"file"})));
        }
        paths = rootSubResources;
        break;
    }

    return paths.iterator();
  }

  //取得目錄或檔案的相關屬性（如：大小、時間等等）
  public MyFileAttributes readAttribute(Path path) throws IOException
  {
    MyFileAttributes attr = fileAttributes.get(path.toString());
    String pathString = path.toString();
    if(FILE_NAME.equals(pathString))//模擬檔案上傳後，檔案大小被變更。
    {
      FileTime currentLastModifiedTime = Files.getLastModifiedTime(fakeFile);
      if(!attr.lastAccessTime().equals(currentLastModifiedTime))
      {
        attr = new MyFileAttributes(currentLastModifiedTime, currentLastModifiedTime, attr.creationTime(), attr.isRegularFile(), attr.isDirectory(), Files.size(fakeFile), attr.permissions());
        this.fileAttributes.put(pathString, attr);
      }
    }
    return attr;
  }

  private static MyFileAttributes createFileAttributes(boolean isFolder, long size)
  {
    FileTime creationTime, lastAccessTime, lastModifiedTime;
    creationTime = lastAccessTime = lastModifiedTime = FileTime.fromMillis(System.currentTimeMillis());

    boolean isDirectory;
    boolean isRegularFile;

    if(isFolder)
    {
        size = 0L;
        isDirectory = true;
        isRegularFile = false;
    }
    else
    {
       isDirectory = false;
       isRegularFile = true;
    }
    Set<PosixFilePermission> permissions = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);
    return new MyFileAttributes(lastModifiedTime, lastAccessTime, creationTime, isRegularFile, isDirectory, size, permissions);
  }

  //此範例僅支援在根目錄下建立目錄
  public void createDirectory(Path path) throws IOException
  {
    My4Path myPath = (My4Path)path;
    String pathString = myPath.toString();
    this.rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{pathString.substring(1)})));
    this.fileAttributes.put(pathString, createFileAttributes(true, FOLDER_SIZE));
  }

  //刪除目錄或檔案
  public void delete(Path path) throws IOException
  {
    My4Path myPath = (My4Path)path;
    System.out.println("MyFileResource delete from rootSubResources? " + this.rootSubResources.remove(myPath));
    this.fileAttributes.remove(myPath.toString());
  }

//此範例僅支援在根目錄下建立檔案
  public FileChannel newFileChannel(Path path, Set<? extends OpenOption> options) throws IOException
  {
    System.out.println("MyFileResource newFileChannel(); path=" + path + "; open options=" + options);

    My4Path myPath = (My4Path)path;
    String pathString = myPath.toString();

    if(!this.fileAttributes.containsKey(pathString))//表示是新檔案
    {
      this.rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{pathString.substring(1)})));
      this.fileAttributes.put(pathString, createFileAttributes(false, System.currentTimeMillis()));
    }

    if(options.contains(StandardOpenOption.WRITE))
    {
      return (FileChannel)Channels.newChannel(new FileOutputStream(fakeFile.toFile()));
    }

    if(options.contains(StandardOpenOption.READ))
    {
      return (FileChannel)Channels.newChannel(new FileInputStream(fakeFile.toFile()));//下載測試用
    }

    throw new IOException("Only new file channel for read or write.");
  }

  //對目錄或檔案做更名或搬移動作。此範例僅印出log。
  public void move(Path source, Path target) throws IOException
  {
    System.out.println("My4FileSystemProvider move(); source=" + source + "; target=" + target);
//    My4Path mySourcePath = (My4Path)source;
//    My4Path myTargetPath = (My4Path)target;
//    String targetPathString = myTargetPath.toString();
//    this.rootSubResources.add(new My4Path(mySourcePath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{targetPathString.substring(1)}), mySourcePath.getUserInfo()));
//    this.fileAttributes.put(targetPathString, this.fileAttributes.get(mySourcePath.toString()));
  }
}