package gov.ca.cwds.data.persistence.cms;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class Base62Test {

  @Test
  public void testCharList() throws Exception {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 9; i++) {
      sb.append(i);
    }
    for (char c = 'A'; c <= 'Z'; c++) {
      sb.append(c);
    }
    for (char c = 'a'; c <= 'z'; c++) {
      sb.append(c);
    }
    assertEquals(sb.toString(), Base62.ALPHABET);
  }

  @Test
  public void testStringFromInt() throws Exception {
    int n = 0;
    String str = "6JaY2";
    char[] chars = str.toCharArray();
    n += Base62.ALPHABET.indexOf(chars[0]) * (int) Math.pow(62, 4);
    n += Base62.ALPHABET.indexOf(chars[1]) * (int) Math.pow(62, 3);
    n += Base62.ALPHABET.indexOf(chars[2]) * (int) Math.pow(62, 2);
    n += Base62.ALPHABET.indexOf(chars[3]) * (int) Math.pow(62, 1);
    n += Base62.ALPHABET.indexOf(chars[4]) * (int) Math.pow(62, 0);
    assertEquals(str, Base62.fromBase10(n));
  }

  @Test
  public void testIntegerFromString() throws Exception {
    assertEquals(2393, Base62.toBase10("cb"));
  }

  @Test
  public void testFromZero() {
    assertEquals("a", Base62.fromBase10(0));
    assertEquals("1", Base62.fromBase10(1));
    assertEquals("2", Base62.fromBase10(2));
    assertEquals("3", Base62.fromBase10(3));
  }
}

