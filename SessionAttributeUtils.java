package ftp;

import org.apache.sshd.common.AttributeStore.AttributeKey;
import org.apache.sshd.common.session.Session;

public class SessionAttributeUtils
{
  private static final AttributeKey<Long> KEY_ACCOUNT_ID = new AttributeKey<>();

  public static Long getAccountId(Session session)
  {
    return session.getAttribute(KEY_ACCOUNT_ID);
  }

  public static void setAccountId(Session session, long accountId)
  {
    session.setAttribute(KEY_ACCOUNT_ID, accountId);
  }
}
