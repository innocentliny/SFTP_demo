package ftp.v4;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.sshd.common.file.util.BaseFileSystem;
import org.apache.sshd.common.file.util.ImmutableList;

import ftp.MyFileAttributes;
import ftp.UserInfo;

public class My4FileSystem extends BaseFileSystem<My4Path>
{
  private final UserInfo userInfo;
  private final static MyFileAttributes ROOT_FILE_ATTRIBUTES = new MyFileAttributes(FileTime.fromMillis(System.currentTimeMillis()),
                                                                                      FileTime.fromMillis(System.currentTimeMillis()),
                                                                                      FileTime.fromMillis(System.currentTimeMillis()),
                                                                                      false,
                                                                                      true,
                                                                                      0L,
                                                                                      EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ));

  public My4FileSystem(My4FileSystemProvider fileSystemProvider, UserInfo userInfo)
  {
    super(fileSystemProvider);
    this.userInfo = userInfo;
    System.out.println("My4FileSystem constructor");
    // TODO Auto-generated constructor stub
  }

  @Override
  protected My4Path create(String root, ImmutableList<String> names)
  {
    System.out.println("My4FileSystem create(); root=" + root + " ; names=" + names);
    System.out.println("My4FileSystem create(); UserInfo=" + userInfo);
//    MyFileAttributes fileAttributes = null;
//    if("/".equals(root) && GenericUtils.isEmpty(names))
//    {
//      fileAttributes = ROOT_FILE_ATTRIBUTES;
//    }
//    else if(!GenericUtils.isEmpty(names))
//    {
//      if(names.get(names.size() - 1).equals("folder3") || names.get(names.size() - 1).equals("test.txt"))//模擬新建目錄與新建檔案
//      {
//        fileAttributes = null;
//      }
//      else if(names.get(names.size() - 1).startsWith("folder"))
//      {
//        fileAttributes = createMy4FileAttributes(true);
//      }
//      else
//      {
//        fileAttributes = createMy4FileAttributes(false);
//      }
//    }
    return new My4Path(this, root, names, this.userInfo);
  }

  @Override
  public void close() throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystem close()");
  }

  @Override
  public boolean isOpen()
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystem isOpen()");
    return true;
  }

  @Override
  public Set<String> supportedFileAttributeViews()
  {
    System.out.println("My4FileSystem supportedFileAttributeViews()");
    return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("posix")));
  }

  @Override
  public UserPrincipalLookupService getUserPrincipalLookupService()
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystem getUserPrincipalLookupService()");
    throw new UnsupportedOperationException("My4FileSystem getUserPrincipalLookupService()");
//    return null;
  }
//  public MyFileAttributes createMy4FileAttributes(boolean isFolder)
//  {
//    return createMy4FileAttributes(isFolder, System.currentTimeMillis());
//  }

//  public MyFileAttributes createMy4FileAttributes(boolean isFolder, long size)
//  {
//    FileTime creationTime = FileTime.fromMillis(System.currentTimeMillis());
//    FileTime lastAccessTime = FileTime.fromMillis(System.currentTimeMillis());
//    FileTime lastModifiedTime = FileTime.fromMillis(System.currentTimeMillis());
//
//    boolean isDirectory;
//    boolean isRegularFile;
//
//    if(isFolder)
//    {
//        size = 0L;
//        isDirectory = true;
//        isRegularFile = false;
//    }
//    else
//    {
//       isDirectory = false;
//       isRegularFile = true;
//    }
//    Set<PosixFilePermission> permissions = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ);
//    return new MyFileAttributes(lastModifiedTime, lastAccessTime, creationTime, isRegularFile, isDirectory, size, permissions);
//  }
}
