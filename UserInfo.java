package ftp;

public class UserInfo
{
  public static final String NAME = "UserInfo";
  private final long accountId;
  private final String username;

  public UserInfo(long accountId, String username)
  {
    this.accountId = accountId;
    this.username = username;
  }

  public long getAccountId()
  {
    return accountId;
  }

  public String getUsername()
  {
    return username;
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("UserInfo [accountId=").append(accountId).append(", username=").append(username).append("]");
    return builder.toString();
  }
}
