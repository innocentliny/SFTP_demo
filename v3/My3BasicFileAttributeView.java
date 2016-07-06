package ftp.v3;

import java.nio.file.attribute.FileAttributeView;

public class My3BasicFileAttributeView implements FileAttributeView
{

  @Override
  public String name()
  {
    // TODO Auto-generated method stub
    System.out.println("My3BasicFileAttributeView name()");
    return "basic";
  }

//  @Override
//  public My3BasicFileAttributes readAttributes() throws IOException
//  {
//    // TODO Auto-generated method stub
//    System.out.println("My3BasicFileAttributeView readAttributes()");
//    return new My3BasicFileAttributes();
//  }
//
//  @Override
//  public void setTimes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime) throws IOException
//  {
//    // TODO Auto-generated method stub
//    System.out.println("My3BasicFileAttributeView setTimes()");
//  }

}
