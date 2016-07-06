package ftp.v3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.sshd.common.file.util.ImmutableList;

import ftp.UserInfo;

public class My3DirectoryStream implements DirectoryStream<Path>
{
  private final My3Path path;
  private final UserInfo userInfo;

  public My3DirectoryStream(Path path, UserInfo userInfo)
  {
    this.path = (My3Path)path;
    this.userInfo = userInfo;
  }

  @Override
  public void close() throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3DirectoryStream close()");
  }

  @Override
  public Iterator<Path> iterator()
  {
    System.out.println("My3DirectoryStream iterator();實際取得該目錄下所有目錄檔案的資訊; path=" + path);
    List<Path> paths = new ArrayList<>();
    String rootString = path.getRoot().toString();

    if("/".equals(path.toString()))
    {
      paths.add(new My3Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"folder1"}), this.userInfo, path.getFileSystem().createMy3FileAttributes(true)));
      paths.add(new My3Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"folder2"}), this.userInfo, path.getFileSystem().createMy3FileAttributes(true)));
      paths.add(new My3Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"file"}), this.userInfo, path.getFileSystem().createMy3FileAttributes(false)));
    }
    else if("/folder1".equals(path.toString()) || "/folder2".equals(path.toString()))
    {
      String uuid = UUID.randomUUID().toString();
      paths.add(new My3Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"folder_" + uuid}), this.userInfo, path.getFileSystem().createMy3FileAttributes(true)));
      paths.add(new My3Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"file_" + uuid}), this.userInfo, path.getFileSystem().createMy3FileAttributes(false)));
    }
    return paths.iterator();
  }
}
