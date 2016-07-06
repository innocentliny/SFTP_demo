package ftp.v4;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.apache.sshd.common.file.util.BasePath;
import org.apache.sshd.common.file.util.ImmutableList;

import ftp.MyFileAttributes;
import ftp.UserInfo;

public class My4Path extends BasePath<My4Path, My4FileSystem>
{
  private final UserInfo userInfo;
  private MyFileAttributes fileAttributes;

  public My4Path(My4FileSystem fileSystem, String root, ImmutableList<String> names, UserInfo userInfo)
  {
    this(fileSystem, root, names, userInfo, null);
  }

  public My4Path(My4FileSystem fileSystem, String root, ImmutableList<String> names, UserInfo userInfo, MyFileAttributes fileAttributes)
  {
    super(fileSystem, root, names);
    this.userInfo = userInfo;
    this.fileAttributes = fileAttributes;
    System.out.println("My4Path constructor; UserInfo=" + userInfo);
    System.out.println("My4Path constructor; My4FileAttributes=" + fileAttributes);
  }

  public String getRootString()
  {
    return this.root;
  }

  @Override
  public Path toRealPath(LinkOption... options) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4Path toRealPath(LinkOption... options)");
    return null;
  }

  public UserInfo getUserInfo()
  {
    return this.userInfo;
  }

  public void setFileAttributes(MyFileAttributes fileAttributes)
  {
    this.fileAttributes = fileAttributes;
  }

  public MyFileAttributes getFileAttributes()
  {
    return this.fileAttributes;
  }
}
