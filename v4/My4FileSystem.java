package ftp.v4;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.sshd.common.file.util.BaseFileSystem;
import org.apache.sshd.common.file.util.ImmutableList;

public class My4FileSystem extends BaseFileSystem<My4Path>
{
  public My4FileSystem(My4FileSystemProvider fileSystemProvider)
  {
    super(fileSystemProvider);
    System.out.println("My4FileSystem constructor()");
  }

  @Override
  protected My4Path create(String root, ImmutableList<String> names)
  {
    System.out.println("My4FileSystem create(); root=" + root + " ; names=" + names);
    return new My4Path(this, root, names);
  }

  @Override
  public void close() throws IOException
  {
    System.out.println("My4FileSystem close()");
  }

  @Override
  public boolean isOpen()
  {
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
    System.out.println("My4FileSystem getUserPrincipalLookupService()");
    throw new UnsupportedOperationException("My4FileSystem getUserPrincipalLookupService()");
  }
}
