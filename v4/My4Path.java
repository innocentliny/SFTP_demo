package ftp.v4;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.apache.sshd.common.file.util.BasePath;
import org.apache.sshd.common.file.util.ImmutableList;

import ftp.MyFileAttributes;

public class My4Path extends BasePath<My4Path, My4FileSystem>
{
  private MyFileAttributes fileAttributes;

  public My4Path(My4FileSystem fileSystem, String root, ImmutableList<String> names)
  {
    this(fileSystem, root, names, null);
  }

  public My4Path(My4FileSystem fileSystem, String root, ImmutableList<String> names, MyFileAttributes fileAttributes)
  {
    super(fileSystem, root, names);
    this.fileAttributes = fileAttributes;
    System.out.println("My4Path constructor()");
  }

  public String getRootString()
  {
    return this.root;
  }

  @Override
  public Path toRealPath(LinkOption... options) throws IOException
  {
    System.out.println("My4Path toRealPath(LinkOption... options)");
    return null;
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
