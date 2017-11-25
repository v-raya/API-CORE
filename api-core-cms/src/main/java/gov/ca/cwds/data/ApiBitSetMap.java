package gov.ca.cwds.data;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gov.ca.cwds.data.std.ApiMarker;

public class ApiBitSetMap<T extends Serializable> implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private final Map<BitSet, T> map = new ConcurrentHashMap<>();

  public ApiBitSetMap() {
    // nichts, nada, нічого
  }

  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  public T get(Object key) {
    return map.get(key);
  }

  public T put(BitSet key, T value) {
    return map.put(key, value);
  }

}
