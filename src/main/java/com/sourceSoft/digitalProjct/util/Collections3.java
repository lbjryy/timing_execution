package com.sourceSoft.digitalProjct.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class Collections3
{

  public static String convertToString(Collection collection, String separator)
  {
    return StringUtils.join(collection, separator);
  }

  public static String convertToString(Collection collection, String prefix, String postfix)
  {
    StringBuilder builder = new StringBuilder();
    for (Iterator i$ = collection.iterator(); i$.hasNext(); ) { Object o = i$.next();
      builder.append(prefix).append(o).append(postfix);
    }
    return builder.toString();
  }

  public static boolean isEmpty(Collection collection)
  {
    return (collection == null) || (collection.isEmpty());
  }

  public static boolean isNotEmpty(Collection collection)
  {
    return (collection != null) && (!collection.isEmpty());
  }

  public static <T> T getFirst(Collection<T> collection)
  {
    if (isEmpty(collection)) {
      return null;
    }

    return collection.iterator().next();
  }

  public static <T> T getLast(Collection<T> collection)
  {
    if (isEmpty(collection)) {
      return null;
    }

    if ((collection instanceof List)) {
      List list = (List)collection;
      return (T) list.get(list.size() - 1);
    }

    Iterator iterator = collection.iterator();
    while (true) {
      Object current = iterator.next();
      if (!iterator.hasNext())
        return (T) current;
    }
  }

  public static <T> List<T> union(Collection<T> a, Collection<T> b)
  {
    List result = new ArrayList(a);
    result.addAll(b);
    return result;
  }

  public static <T> List<T> subtract(Collection<T> a, Collection<T> b)
  {
    List list = new ArrayList(a);
    for (Iterator i$ = b.iterator(); i$.hasNext(); ) { Object element = i$.next();
      list.remove(element);
    }

    return list;
  }

  public static <T> List<T> intersection(Collection<T> a, Collection<T> b)
  {
    List list = new ArrayList();

    for (Iterator i$ = a.iterator(); i$.hasNext(); ) { Object element = i$.next();
      if (b.contains(element)) {
        list.add(element);
      }
    }
    return list;
  }
}