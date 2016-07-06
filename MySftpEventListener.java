package ftp;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import org.apache.sshd.common.util.GenericUtils;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.AbstractSftpEventListenerAdapter;
import org.apache.sshd.server.subsystem.sftp.DirectoryHandle;
import org.apache.sshd.server.subsystem.sftp.FileHandle;
import org.apache.sshd.server.subsystem.sftp.Handle;

public class MySftpEventListener extends AbstractSftpEventListenerAdapter
{
  @Override
  public void initialized(ServerSession session, int version) {

          System.out.println("MySftpEventListener " + "initialized(" + session + ") version: " + version);

  }

  @Override
  public void destroying(ServerSession session) {

          System.out.println("MySftpEventListener " + "destroying(" + session + ")");

  }

  @Override
  public void open(ServerSession session, String remoteHandle, Handle localHandle) {

          Path path = localHandle.getFile();
          System.out.println("MySftpEventListener " + "open(" + session + ")[" + remoteHandle + "] " + (Files.isDirectory(path) ? "directory" : "file") + " " + path);

  }

  @Override
  public void read(ServerSession session, String remoteHandle, DirectoryHandle localHandle, Map<String, Path> entries) {
      int numEntries = GenericUtils.size(entries);

      System.out.println("MySftpEventListener " + "read(" + session + ")[" + localHandle.getFile() + "] " + numEntries + " entries");


      if ((numEntries > 0)) {
          for (Map.Entry<String, Path> ee : entries.entrySet()) {
              System.out.println("MySftpEventListener " + "read(" + session + ")[" + localHandle.getFile() + "] " + ee.getKey() + " - " + ee.getValue());
          }
      }
  }

  @Override
  public void read(ServerSession session, String remoteHandle, FileHandle localHandle,
                   long offset, byte[] data, int dataOffset, int dataLen, int readLen) {

          System.out.println("MySftpEventListener " + "read(" + session + ")[" + localHandle.getFile() + "] offset=" + offset + ", requested=" + dataLen + ", read=" + readLen);

  }

  @Override
  public void write(ServerSession session, String remoteHandle, FileHandle localHandle,
                    long offset, byte[] data, int dataOffset, int dataLen) {

          System.out.println("MySftpEventListener " + "write(" + session + ")[" + localHandle.getFile() + "] offset=" + offset + ", requested=" + dataLen);

  }

  @Override
  public void blocking(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, long length, int mask) {

          System.out.println("MySftpEventListener " + "blocking(" + session + ")[" + localHandle.getFile() + "]"
                 + " offset=" + offset + ", length=" + length + ", mask=0x" + Integer.toHexString(mask));

  }

  @Override
  public void blocked(ServerSession session, String remoteHandle, FileHandle localHandle,
                      long offset, long length, int mask, Throwable thrown) {

          System.out.println("MySftpEventListener " + "blocked(" + session + ")[" + localHandle.getFile() + "]"
                  + " offset=" + offset + ", length=" + length + ", mask=0x" + Integer.toHexString(mask)
                  + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }

  @Override
  public void unblocking(ServerSession session, String remoteHandle, FileHandle localHandle, long offset, long length) {

          System.out.println("MySftpEventListener " + "unblocking(" + session + ")[" + localHandle.getFile() + "] offset=" + offset + ", length=" + length);

  }

  @Override
  public void unblocked(ServerSession session, String remoteHandle, FileHandle localHandle,
                        long offset, long length, Boolean result, Throwable thrown) {

          System.out.println("MySftpEventListener " + "unblocked(" + session + ")[" + localHandle.getFile() + "]"
                  + " offset=" + offset + ", length=" + length + ", result=" + result
                  + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }

  @Override
  public void close(ServerSession session, String remoteHandle, Handle localHandle) {

          Path path = localHandle.getFile();
          System.out.println("MySftpEventListener " + "close(" + session + ")[" + remoteHandle + "] " + (Files.isDirectory(path) ? "directory" : "file") + " " + path);

  }

  @Override
  public void creating(ServerSession session, Path path, Map<String, ?> attrs) {

//    System.out.println("MySftpEventListener " + "creating(" + session + ") " + (Files.isDirectory(path) ? "directory" : "file") + " " + path);
    System.out.println("MySftpEventListener " + "creating(" + session + ") " + path);

  }

  @Override
  public void created(ServerSession session, Path path, Map<String, ?> attrs, Throwable thrown) {

          System.out.println("MySftpEventListener " + "created(" + session + ") " + (Files.isDirectory(path) ? "directory" : "file") + " " + path
                 + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }

  @Override
  public void moving(ServerSession session, Path srcPath, Path dstPath, Collection<CopyOption> opts) {

          System.out.println("MySftpEventListener " + "moving(" + session + ")[" + opts + "]" + srcPath + " => " + dstPath);

  }

  @Override
  public void moved(ServerSession session, Path srcPath, Path dstPath, Collection<CopyOption> opts, Throwable thrown) {

          System.out.println("MySftpEventListener " + "moved(" + session + ")[" + opts + "]" + srcPath + " => " + dstPath
                  + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }

  @Override
  public void removing(ServerSession session, Path path) {

          System.out.println("MySftpEventListener " + "removing(" + session + ") " + path);

  }

  @Override
  public void removed(ServerSession session, Path path, Throwable thrown) {

          System.out.println("MySftpEventListener " + "removed(" + session + ") " + path
                + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }

  @Override
  public void linking(ServerSession session, Path source, Path target, boolean symLink) {

          System.out.println("MySftpEventListener " + "linking(" + session + ")[" + symLink + "]" + source + " => " + target);

  }

  @Override
  public void linked(ServerSession session, Path source, Path target, boolean symLink, Throwable thrown) {

          System.out.println("MySftpEventListener " + "linked(" + session + ")[" + symLink + "]" + source + " => " + target
                  + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }

  @Override
  public void modifyingAttributes(ServerSession session, Path path, Map<String, ?> attrs) {

          System.out.println("MySftpEventListener " + "modifyingAttributes(" + session + ") " + path + ": " + attrs);

  }

  @Override
  public void modifiedAttributes(ServerSession session, Path path, Map<String, ?> attrs, Throwable thrown) {

          System.out.println("MySftpEventListener " + "modifiedAttributes(" + session + ") " + path
                + ((thrown == null) ? "" : (": " + thrown.getClass().getSimpleName() + ": " + thrown.getMessage())));

  }
}
