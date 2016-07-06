package ftp.v3;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import ftp.MyFileAttributes;
import ftp.UserInfo;

public class My3FileSystemProvider extends FileSystemProvider
{
  private final Map<URI, My3FileSystem> fileSystems = Collections.synchronizedMap(new WeakHashMap<URI, My3FileSystem>());

  @Override
  public String getScheme()
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider getScheme()");
    return "aws";
  }

  @Override
  public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException
  {
    System.out.println("My3FileSystemProvider newFileSystem(); uri=" + uri);
    //TODO 判斷uri的schema是不是一樣？
    if(fileSystems.containsKey(uri))
    {
      System.out.println("throw new FileSystemAlreadyExistsException()");
      throw new FileSystemAlreadyExistsException();
    }

    UserInfo userInfo = (UserInfo)env.get(UserInfo.NAME);
    System.out.println("My3FileSystemProvider newFileSystem(); UserInfo=" + userInfo);

    My3FileSystem fileSystem = new My3FileSystem(this, userInfo);
    fileSystems.put(uri, fileSystem);
    return fileSystem;
  }

  @Override
  public FileSystem getFileSystem(URI uri)
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider getFileSystem(); uri=" + uri);
    if(!fileSystems.containsKey(uri))
    {
      System.out.println("throw new FileSystemNotFoundException()");
      throw new FileSystemNotFoundException();
    }

    return fileSystems.get(uri);
  }

  @Override
  public Path getPath(URI uri)
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider getPath(); uri=" + uri + " ; uri.getSchemeSpecificPart()=" + uri.getSchemeSpecificPart());
    return null;
  }

  @Override
  public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider newByteChannel()");
    return null;
  }

  @Override
  public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider newDirectoryStream(); dir=" + dir + " ; dir instanceof My3Path?" + (dir instanceof My3Path) + " ; filter=" + filter);
    My3Path my3Path = (My3Path)dir;
    System.out.println("My3FileSystemProvider newDirectoryStream(); UserInfo=" + my3Path.getUserInfo());
    return new My3DirectoryStream(dir, my3Path.getUserInfo());
  }

  @Override
  public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException
  {
    System.out.println("My3FileSystemProvider createDirectory();實際建立目錄後，取得屬性並設給該目錄物件; path=" + dir + "; attrs=" + attrs + "; attrs.length=" + attrs.length);
    MyFileAttributes fileAttributes = new MyFileAttributes(FileTime.fromMillis(System.currentTimeMillis()),
                                                             FileTime.fromMillis(System.currentTimeMillis()),
                                                             FileTime.fromMillis(System.currentTimeMillis()),
                                                             false,
                                                             true,
                                                             0L,
                                                             EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE));
    ((My3Path)dir).setFileAttributes(fileAttributes);
  }

  @Override
  public void delete(Path path) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider delete()");
  }

  @Override
  public void copy(Path source, Path target, CopyOption... options) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider copy()");
  }

  @Override
  public void move(Path source, Path target, CopyOption... options) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider move()");
  }

  @Override
  public boolean isSameFile(Path path, Path path2) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider isSameFile()");
    return false;
  }

  @Override
  public boolean isHidden(Path path) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider isHidden()");
    return false;
  }

  @Override
  public FileStore getFileStore(Path path) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider getFileStore()");
    return null;
  }

  @Override
  public void checkAccess(Path path, AccessMode... modes) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider checkAccess(); path=" + path);
    for(AccessMode mode : modes)
    {
      System.out.println("My3FileSystemProvider checkAccess(); AccessMode = " + mode.name());
    }
  }

  @Override
  public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options)
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider getFileAttributeView()");
//    return (V)new My3BasicFileAttributeView();
    return null;
  }

  @Override
  public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException
  {
    System.out.println("My3FileSystemProvider readAttributes(Path path, Class<A> type, LinkOption... options); path=" + path + " ; type=" + type);
    for(LinkOption linkOption : options)
    {
      System.out.println("My3FileSystemProvider readAttributes(Path path, Class<A> type, LinkOption... options); link option=" + linkOption);
    }
    MyFileAttributes fileAttributes = ((My3Path)path).getFileAttributes();
    if(fileAttributes == null)
    {
      throw new NoSuchFileException("Can't read attributes from " + path);
    }

    return (A)fileAttributes;
//    return (A)new My3FileAttributes();
//    return null;
//    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException
  {
    System.out.println("My3FileSystemProvider readAttributes(Path path, String attributes, LinkOption... options); path=" + path + " ;attributes=" + attributes + "; options=" + options);
    MyFileAttributes fileAttributes = ((My3Path)path).getFileAttributes();
    Map<String, Object> map = new HashMap<>();
    map.put("creationTime", fileAttributes.creationTime());
    map.put("lastAccessTime", fileAttributes.lastAccessTime());
    map.put("lastModifiedTime", fileAttributes.lastModifiedTime());
    map.put("isSymbolicLink", fileAttributes.isSymbolicLink());
    map.put("isOther", fileAttributes.isOther());
    map.put("size", fileAttributes.size());
    map.put("isDirectory", fileAttributes.isDirectory());
    map.put("isRegularFile", fileAttributes.isRegularFile());
    map.put("permissions", fileAttributes.permissions());
    return map;
//    return null;
  }

  @Override
  public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My3FileSystemProvider setAttribute()");
  }

}
