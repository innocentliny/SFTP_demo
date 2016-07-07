package ftp;

import java.nio.file.attribute.GroupPrincipal;

import org.apache.sshd.server.subsystem.sftp.PrincipalBase;

public class MyGroupPrincipal extends PrincipalBase implements GroupPrincipal
{
  public MyGroupPrincipal(String name)
  {
    super(name);
    // TODO Auto-generated constructor stub
  }

}
