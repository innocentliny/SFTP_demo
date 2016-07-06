package ftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
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
  private final List<Path> rootSubResources = new ArrayList<>();
  private final Map<String, MyFileAttributes> fileAttributes = new HashMap<>();

  private MyFileResource()
  {
    fileAttributes.put(ROOT_NAME, createFileAttributes(true, FOLDER_SIZE));
    fileAttributes.put(ROOT_NAME, createFileAttributes(true, FOLDER_SIZE));
    fileAttributes.put(FOLDER_1_NAME, createFileAttributes(true, FOLDER_SIZE));
    fileAttributes.put(FOLDER_2_NAME, createFileAttributes(true, FOLDER_SIZE));
    fileAttributes.put(FILE_NAME, createFileAttributes(false, System.currentTimeMillis()));
  }

  public Iterator<Path> getSubresources(Path path)
  {
    My4Path myPath = (My4Path)path;
    List<Path> paths = new ArrayList<>();
    switch(path.toString())
    {
      case "/":
        if(rootSubResources.isEmpty())
        {
          rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{"folder1"}), myPath.getUserInfo()));
          rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{"folder2"}), myPath.getUserInfo()));
          rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{"file"}), myPath.getUserInfo()));
        }
        paths = rootSubResources;
        break;
    }

    return paths.iterator();
  }

  public MyFileAttributes readAttribute(Path path)
  {
    MyFileAttributes attr = fileAttributes.get(path.toString());
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
    this.rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{pathString.substring(1)}), myPath.getUserInfo()));
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
  public FileChannel newFileChannel(Path path) throws IOException
  {
    System.out.println("MyFileResource newFileChannel(); path=" + path);
    My4Path myPath = (My4Path)path;
    String pathString = myPath.toString();
    this.rootSubResources.add(new My4Path(myPath.getFileSystem(), ROOT_NAME, new ImmutableList<String>(new String[]{pathString.substring(1)}), myPath.getUserInfo()));
    this.fileAttributes.put(pathString, createFileAttributes(false, System.currentTimeMillis()));

    return (FileChannel)Channels.newChannel(new FileOutputStream(Files.createTempFile("test", ".txt", null).toFile()));
  }

  //對目錄或檔案做更名或搬移動作。此範例僅印出log
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