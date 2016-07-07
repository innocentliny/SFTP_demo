package ftp.v4;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
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
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import ftp.MyFileAttributes;
import ftp.MyFileResource;

public class My4FileSystemProvider extends FileSystemProvider
{
  private final Map<URI, My4FileSystem> fileSystems = Collections.synchronizedMap(new WeakHashMap<URI, My4FileSystem>());
  private Long accountId;

  @Override
  public String getScheme()
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider getScheme()");
    return "aws";
  }

  @Override
  public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException
  {
    System.out.println("My4FileSystemProvider newFileSystem(); uri=" + uri);
    //TODO 判斷uri的schema是不是一樣？
    if(fileSystems.containsKey(uri))
    {
      System.out.println("throw new FileSystemAlreadyExistsException()");
      throw new FileSystemAlreadyExistsException();
    }

    accountId = (Long)env.get("accountId");
    System.out.println("My4FileSystemProvider newFileSystem(); accountId=" + accountId);

    My4FileSystem fileSystem = new My4FileSystem(this);
    fileSystems.put(uri, fileSystem);
    return fileSystem;
  }

  @Override
  public FileSystem getFileSystem(URI uri)
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider getFileSystem(); uri=" + uri);
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
    System.out.println("My4FileSystemProvider getPath(); uri=" + uri + " ; uri.getSchemeSpecificPart()=" + uri.getSchemeSpecificPart());
    return null;
  }

  @Override
  public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider newByteChannel()");
    return null;
  }

  @Override
  public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException
  {
    System.out.println("My4FileSystemProvider newDirectoryStream(); dir=" + dir + " ; dir instanceof My4Path?" + (dir instanceof My4Path) + " ; filter=" + filter);
    return new My4DirectoryStream(this.accountId, dir);
  }

  @Override
  public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException
  {
    MyFileResource.INSTANCE.createDirectory(dir);
//    System.out.println("My4FileSystemProvider createDirectory();實際建立目錄後，取得屬性並設給該目錄物件; path=" + dir + "; attrs=" + attrs + "; attrs.length=" + attrs.length);
//    MyFileAttributes fileAttributes = new MyFileAttributes(FileTime.fromMillis(System.currentTimeMillis()),
//                                                             FileTime.fromMillis(System.currentTimeMillis()),
//                                                             FileTime.fromMillis(System.currentTimeMillis()),
//                                                             false,
//                                                             true,
//                                                             0L,
//                                                             EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE));
//    ((My4Path)dir).setFileAttributes(fileAttributes);
  }

  @Override
  public void delete(Path path) throws IOException
  {
    System.out.println("My4FileSystemProvider delete()");
    MyFileResource.INSTANCE.delete(path);
  }

  @Override
  public void copy(Path source, Path target, CopyOption... options) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider copy()");
  }

  @Override
  public void move(Path source, Path target, CopyOption... options) throws IOException
  {
    System.out.println("My4FileSystemProvider move()");
    MyFileResource.INSTANCE.move(source, target);
  }

  @Override
  public boolean isSameFile(Path path, Path path2) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider isSameFile()");
    return false;
  }

  @Override
  public boolean isHidden(Path path) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider isHidden()");
    return false;
  }

  @Override
  public FileStore getFileStore(Path path) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider getFileStore()");
    return null;
  }

  @Override
  public void checkAccess(Path path, AccessMode... modes) throws IOException
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider checkAccess(); path=" + path);
    for(AccessMode mode : modes)
    {
      System.out.println("My4FileSystemProvider checkAccess(); AccessMode = " + mode.name());
    }
  }

  @Override
  public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options)
  {
    // TODO Auto-generated method stub
    System.out.println("My4FileSystemProvider getFileAttributeView()");
//    return (V)new My4BasicFileAttributeView();
    return null;
  }

  @Override
  public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException
  {
    System.out.println("My4FileSystemProvider readAttributes(Path path, Class<A> type, LinkOption... options); path=" + path + " ; type=" + type);
    for(LinkOption linkOption : options)
    {
      System.out.println("My4FileSystemProvider readAttributes(Path path, Class<A> type, LinkOption... options); link option=" + linkOption);
    }

    MyFileAttributes fileAttributes = MyFileResource.INSTANCE.readAttribute(path);
    if(fileAttributes == null)
    {
      throw new NoSuchFileException("Can't read attributes from " + path);
    }

    return (A)fileAttributes;
//    return (A)new My4FileAttributes();
//    return null;
//    throw new UnsupportedOperationException();
  }

  @Override
  public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException
  {
    System.out.println("My4FileSystemProvider readAttributes(Path path, String attributes, LinkOption... options); path=" + path + " ;attributes=" + attributes + "; options=" + options);
    MyFileAttributes fileAttributes = MyFileResource.INSTANCE.readAttribute(path);
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
    map.put("owner", fileAttributes.owner());
    map.put("group", fileAttributes.group());
    return map;
  }

  @Override
  public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException
  {
    System.out.println("My4FileSystemProvider setAttribute(); path=" + path + "; attribute=" + attribute + "; value=" + value);
  }

  @Override
  public FileChannel newFileChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException
  {
    System.out.println("My4FileSystemProvider newFileChannel(); path=" + path + "; options=" + options + "; attrs.length=" + attrs.length);
    for(FileAttribute attr : attrs)
    {
      System.out.println("My4FileSystemProvider newFileChannel(); attr=" + attr);
    }

    return MyFileResource.INSTANCE.newFileChannel(path, options);
//    My4Path my4Path = (My4Path)path;
//    if(my4Path.getFileAttributes() == null)//模擬新建檔案後，給個假的檔案屬性。
//    {
//      my4Path.setFileAttributes(my4Path.getFileSystem().createMy4FileAttributes(false, 0L));
//    }
//    return (FileChannel)Channels.newChannel(new FileOutputStream("z:/test.txt"));
//    return super.newFileChannel(path, options, attrs);
  }

}
