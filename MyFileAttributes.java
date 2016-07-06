package ftp;

import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

public class MyFileAttributes implements PosixFileAttributes
{
  private final FileTime lastModifiedTime;
  private final FileTime lastAccessTime;
  private final FileTime creationTime;
  private final boolean isRegularFile;
  private final boolean isDirectory;
  private final long size;
  private final Set<PosixFilePermission> permissions;


  public MyFileAttributes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime creationTime, boolean isRegularFile, boolean isDirectory, long size,
      Set<PosixFilePermission> permissions)
  {
    this.lastModifiedTime = lastModifiedTime;
    this.lastAccessTime = lastAccessTime;
    this.creationTime = creationTime;
    this.isRegularFile = isRegularFile;
    this.isDirectory = isDirectory;
    this.size = size;
    this.permissions = permissions;
  }

  @Override
  public FileTime lastModifiedTime()
  {
    return lastModifiedTime;
  }

  @Override
  public FileTime lastAccessTime()
  {
    return lastAccessTime;
  }

  @Override
  public FileTime creationTime()
  {
    return creationTime;
  }

  @Override
  public boolean isRegularFile()
  {
    return isRegularFile;
  }

  @Override
  public boolean isDirectory()
  {
    return isDirectory;
  }

  @Override
  public long size()
  {
    return size;
  }

  @Override
  public Set<PosixFilePermission> permissions()
  {
    return permissions;
  }

  @Override
  public boolean isSymbolicLink()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isOther()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Object fileKey()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserPrincipal owner()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GroupPrincipal group()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("My3FileAttributes [lastModifiedTime=")
           .append(lastModifiedTime)
           .append(", lastAccessTime=")
           .append(lastAccessTime)
           .append(", creationTime=")
           .append(creationTime)
           .append(", isRegularFile=")
           .append(isRegularFile)
           .append(", isDirectory=")
           .append(isDirectory)
           .append(", size=")
           .append(size)
           .append(", permissions=")
           .append(permissions)
           .append("]");
    return builder.toString();
  }


}