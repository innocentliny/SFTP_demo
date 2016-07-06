package ftp;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.sshd.common.SshConstants;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;

public class MyPasswordAuthenticator implements PasswordAuthenticator
{
  public static final MyPasswordAuthenticator INSTANCE = new MyPasswordAuthenticator();
  private static final AtomicLong fakeAccountId = new AtomicLong(18203L);

  private MyPasswordAuthenticator()
  {}

  @Override
  public boolean authenticate(String username, String password, ServerSession session) throws PasswordChangeRequiredException
  {
    System.out.println("MyPasswordAuthenticator authenticate(); username=" + username + "; password=" + password);
    System.out.println("session.getActiveSessionCountForUser(" + username + ") = " + session.getActiveSessionCountForUser(username));

    try
    {
      if(!"anderson.lin@asuscloud.com".equals(username))
      {
        session.disconnect(SshConstants.SSH2_DISCONNECT_ILLEGAL_USER_NAME, "You're not Anderson.");
      }
      if(session.getActiveSessionCountForUser(username) >= 2)
      {
        session.disconnect(SshConstants.SSH2_DISCONNECT_TOO_MANY_CONNECTIONS, "連線太多嚕");
      }
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
    //Got user info = authenticated
    Long accountId = fakeAccountId.getAndIncrement();
    UserInfo userInfo = new UserInfo(accountId, username);
    SessionAttributeUtils.setUserInfo(session, userInfo);

    return true;
  }
}
