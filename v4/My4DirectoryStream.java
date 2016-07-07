package ftp.v4;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;

import ftp.MyFileResource;

public class My4DirectoryStream implements DirectoryStream<Path>
{
  private final My4Path path;
  private final long accountId;

  public My4DirectoryStream(long accountId, Path path)
  {
    this.accountId = accountId;
    this.path = (My4Path)path;
  }

  @Override
  public void close() throws IOException
  {
    System.out.println("My4DirectoryStream close()");
  }

  @Override
  public Iterator<Path> iterator()
  {
    System.out.println("My4DirectoryStream iterator();實際取得該目錄下所有目錄檔案的資訊; accountId=" + accountId + "; path=" + path);
    return MyFileResource.INSTANCE.getSubresources(accountId, path);
  }
}
