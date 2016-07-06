package ftp;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Map;

import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.common.session.Session;

import ftp.v3.My3FileSystemProvider;

public class MyFileSystemFactory implements FileSystemFactory
{
  public MyFileSystemFactory()
  {
    System.out.println("MyFileSystemFactory()");
  }

  @Override
  public FileSystem createFileSystem(Session session) throws IOException
  {
    // 驗證帳密後，會進到此method
    System.out.println("MyFileSystemFactory createFileSystem(Session session)");
    System.out.println("MyFileSystemFactory session.getUsername() = " + session.getUsername());
    System.out.println("MyFileSystemFactory session.getAuthTimeout() = " + session.getAuthTimeout());
    System.out.println("MyFileSystemFactory session.getClientVersion() = " + session.getClientVersion());
    System.out.println("MyFileSystemFactory session.getIdleTimeout() = " + session.getIdleTimeout());
    System.out.println("MyFileSystemFactory session.getServerVersion() = " + session.getServerVersion());
    System.out.println("MyFileSystemFactory session.getCipherFactories() = " + session.getCipherFactories());
    System.out.println("MyFileSystemFactory session.getSessionId() = " + new String(session.getSessionId()));

    Map<String, Object> envMap = new HashMap<>();
    envMap.put(UserInfo.NAME, SessionAttributeUtils.getUserInfo(session));//Login info is set after authentication.

//    return new My3FileSystemProvider().newFileSystem(URI.create("aws://foo/bar"), Collections.<String, Object>emptyMap());
    return new My3FileSystemProvider().newFileSystem((URI)null, envMap);
  }
}