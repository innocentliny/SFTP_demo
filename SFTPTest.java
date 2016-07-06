package ftp;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.file.FileSystemFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.apache.sshd.server.subsystem.sftp.UnsupportedAttributePolicy;

public class SFTPTest
{

  public static void main(String[] args) throws InterruptedException
  {
    SshServer server = SshServer.setUpDefaultServer();

    //Port
//    server.setHost("192.168.150.154");
    server.setPort(22);

    //Factory
    SftpSubsystemFactory subsystemFactory = new SftpSubsystemFactory.Builder()
                                                .withUnsupportedAttributePolicy(UnsupportedAttributePolicy.Warn)
//                                                .withExecutorService(Executors.newSingleThreadExecutor())// If null then a single-threaded ad-hoc service is used.
                                                .withShutdownOnExit(true).build();
    subsystemFactory.addSftpEventListener(new MySftpEventListener());
    List<NamedFactory<Command>> subsystemFactories = new ArrayList<NamedFactory<Command>>();
    subsystemFactories.add(subsystemFactory);
    server.setSubsystemFactories(subsystemFactories);

    //KeyPairProvider
    SimpleGeneratorHostKeyProvider keyProvider = new SimpleGeneratorHostKeyProvider(Paths.get("z:/hostkey.ser"));
    keyProvider.setAlgorithm(KeyUtils.RSA_ALGORITHM);
    server.setKeyPairProvider(keyProvider);

    //PasswordAuthenticator
//    server.setPasswordAuthenticator(AcceptAllPasswordAuthenticator.INSTANCE);
    server.setPasswordAuthenticator(MyPasswordAuthenticator.INSTANCE);

    //FileSystem
    FileSystemFactory fileSystemFactory = new MyFileSystemFactory();
    server.setFileSystemFactory(fileSystemFactory);

    for(FileSystemProvider p : FileSystemProvider.installedProviders())
    {
      System.out.println("Installed file system provider: " + p);
    }

    try
    {
      server.getProperties().put(SshServer.IDLE_TIMEOUT, 0L);
      server.getProperties().put(SshServer.AUTH_TIMEOUT, 0L);
      for(String key : server.getProperties().keySet())
      {
        System.out.println("SshServer property: " + key + " = " + server.getProperties().get(key));
      }

      server.addSessionListener(new MySessionListener());

      server.start();
      System.out.println("SFTP server started...");
      Thread.sleep(Long.MAX_VALUE);
    }
    catch( IOException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
