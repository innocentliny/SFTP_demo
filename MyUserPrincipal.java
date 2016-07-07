package ftp;

import java.nio.file.attribute.UserPrincipal;

import org.apache.sshd.server.subsystem.sftp.PrincipalBase;

public class MyUserPrincipal extends PrincipalBase implements UserPrincipal
{

  public MyUserPrincipal(String name)
  {
    super(name);
    // TODO Auto-generated constructor stub
  }


}
