package com.honor.utils;

import java.util.Collection;
import com.badlogic.gdx.utils.Array;

public class ListUtils {

  public static <T extends Object> Array<T> toArray(Collection<T> originalList) {
    Array<T> gdxList = new Array<>();
    originalList.forEach(obj -> gdxList.add(obj));
    return gdxList;
  }
  
}
