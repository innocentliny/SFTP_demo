package ftp;

import org.apache.sshd.common.AttributeStore.AttributeKey;
import org.apache.sshd.common.session.Session;

public class SessionAttributeUtils
{
  private static final AttributeKey<UserInfo> USER_INFO_KEY = new AttributeKey<>();

  public static UserInfo getUserInfo(Session session)
  {
    return session.getAttribute(USER_INFO_KEY);
  }

  public static void setUserInfo(Session session, UserInfo loginInfo)
  {
    session.setAttribute(USER_INFO_KEY, loginInfo);
  }
}
