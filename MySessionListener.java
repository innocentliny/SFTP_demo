package ftp;

import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.session.SessionListener;

public class MySessionListener implements SessionListener
{

  @Override
  public void sessionCreated(Session session)
  {
    System.out.println("MySessionListener sessionCreated(); username=" + session.getUsername());
  }

  @Override
  public void sessionEvent(Session session, Event event)
  {
    System.out.println("MySessionListener sessionEvent(); username=" + session.getUsername() + "; event=" + event.name());
  }

  @Override
  public void sessionException(Session session, Throwable t)
  {
    System.out.println("MySessionListener sessionException(); username=" + session.getUsername() + "; Throwable=" + t.getMessage());
  }

  @Override
  public void sessionClosed(Session session)
  {
    System.out.println("MySessionListener sessionClosed(); username=" + session.getUsername());
  }

}
