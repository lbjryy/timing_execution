package com.sourceSoft.digitalProjct.util;

import java.security.SecureRandom;
import java.util.UUID;

public class Identities
{
  private static SecureRandom random = new SecureRandom();

  public static String uuid()
  {
    return UUID.randomUUID().toString();
  }

  public static String uuid2()
  {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static long randomLong()
  {
    return Math.abs(random.nextLong());
  }

  public static long random7Int()
  {
    return Math.abs(random.nextInt(9999999) + 1);
  }

  public static String randomBase62(int length)
  {
    byte[] randomBytes = new byte[length];
    random.nextBytes(randomBytes);
    return Encodes.encodeBase62(randomBytes);
  }
}