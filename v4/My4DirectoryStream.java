package ftp.v4;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;

import ftp.MyFileResource;

public class My4DirectoryStream implements DirectoryStream<Path>
{
  private final My4Path path;

  public My4DirectoryStream(Path path)
  {
    this.path = (My4Path)path;
  }

  @Override
  public void close() throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4DirectoryStream close()");
  }

  @Override
  public Iterator<Path> iterator()
  {
    System.out.println("My4DirectoryStream iterator();實際取得該目錄下所有目錄檔案的資訊; path=" + path);
    return MyFileResource.INSTANCE.getSubresources(path);
//    List<Path> paths = new ArrayList<>();
//    String rootString = path.getRootString();
//
//    if("/".equals(path.toString()))
//    {
//      paths.add(new My4Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"folder1"}), this.userInfo, path.getFileSystem().createMy4FileAttributes(true)));
//      paths.add(new My4Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"folder2"}), this.userInfo, path.getFileSystem().createMy4FileAttributes(true)));
//      paths.add(new My4Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"file"}), this.userInfo, path.getFileSystem().createMy4FileAttributes(false)));
//    }
//    else if("/folder1".equals(path.toString()) || "/folder2".equals(path.toString()))
//    {
//      String uuid = UUID.randomUUID().toString();
//      paths.add(new My4Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"folder_" + uuid}), this.userInfo, path.getFileSystem().createMy4FileAttributes(true)));
//      paths.add(new My4Path(path.getFileSystem(), rootString, new ImmutableList<String>(new String[]{"file_" + uuid}), this.userInfo, path.getFileSystem().createMy4FileAttributes(false)));
//    }
//    return paths.iterator();
  }
}
