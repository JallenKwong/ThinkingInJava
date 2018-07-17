package com.lun.concurrency.performance;

//: concurrency/ListComparisons.java
// {Args: 1 10 10} (Fast verification check during build)
// Rough comparison of thread-safe List performance.
import java.util.concurrent.*;

import com.lun.util.CountingIntegerList;

import java.util.*;

abstract class ListTest extends Tester<List<Integer>> {
  ListTest(String testId, int nReaders, int nWriters) {
    super(testId, nReaders, nWriters);
  }
  class Reader extends TestTask {
    long result = 0;
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          result += testContainer.get(index);
    }
    void putResults() {
      readResult += result;
      readTime += duration;
    }
  }
  class Writer extends TestTask {
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          testContainer.set(index, writeData[index]);
    }
    void putResults() {
      writeTime += duration;
    }
  }
  void startReadersAndWriters() {
    for(int i = 0; i < nReaders; i++)
      exec.execute(new Reader());
    for(int i = 0; i < nWriters; i++)
      exec.execute(new Writer());
  }
}

class SynchronizedArrayListTest extends ListTest {
  List<Integer> containerInitializer() {
    return Collections.synchronizedList(
      new ArrayList<Integer>(
        new CountingIntegerList(containerSize)));
  }
  SynchronizedArrayListTest(int nReaders, int nWriters) {
    super("Synched ArrayList", nReaders, nWriters);
  }
}

class CopyOnWriteArrayListTest extends ListTest {
  List<Integer> containerInitializer() {
    return new CopyOnWriteArrayList<Integer>(
      new CountingIntegerList(containerSize));
  }
  CopyOnWriteArrayListTest(int nReaders, int nWriters) {
    super("CopyOnWriteArrayList", nReaders, nWriters);
  }
}

public class ListComparisons {
  public static void main(String[] args) {
    Tester.initMain(args);
    new SynchronizedArrayListTest(10, 0);
    new SynchronizedArrayListTest(9, 1);
    new SynchronizedArrayListTest(5, 5);
    new CopyOnWriteArrayListTest(10, 0);
    new CopyOnWriteArrayListTest(9, 1);
    new CopyOnWriteArrayListTest(5, 5);
    Tester.exec.shutdown();
  }
} /* Output: (Sample)
Type                             Read time     Write time
Synched ArrayList 10r 0w        4525942247              0
Synched ArrayList 10r 0w        4536699392              0
Synched ArrayList 10r 0w        4361824008              0
Synched ArrayList 10r 0w        4835209347              0
Synched ArrayList 10r 0w        4896043667              0
Synched ArrayList 10r 0w        5137473543              0
Synched ArrayList 10r 0w         651633076              0
Synched ArrayList 10r 0w        4650074197              0
Synched ArrayList 10r 0w        4540708749              0
Synched ArrayList 10r 0w        4423824539              0
Synched ArrayList 9r 1w         3686030078      455381988
readTime + writeTime =          4141412066
Synched ArrayList 9r 1w         3641856838      587412028
readTime + writeTime =          4229268866
Synched ArrayList 9r 1w         3703607395      540698483
readTime + writeTime =          4244305878
Synched ArrayList 9r 1w         3204727150      572129147
readTime + writeTime =          3776856297
Synched ArrayList 9r 1w         4073243258      403915320
readTime + writeTime =          4477158578
Synched ArrayList 9r 1w         3614012060      529392622
readTime + writeTime =          4143404682
Synched ArrayList 9r 1w         4257300998      671960119
readTime + writeTime =          4929261117
Synched ArrayList 9r 1w         4221662843      602973627
readTime + writeTime =          4824636470
Synched ArrayList 9r 1w         3244420343      555571753
readTime + writeTime =          3799992096
Synched ArrayList 9r 1w         4089069717      271288317
readTime + writeTime =          4360358034
Synched ArrayList 5r 5w         1310624256     2346558067
readTime + writeTime =          3657182323
Synched ArrayList 5r 5w         1129110412      894257396
readTime + writeTime =          2023367808
Synched ArrayList 5r 5w         2010507686     1423767563
readTime + writeTime =          3434275249
Synched ArrayList 5r 5w         2454259923     1757277640
readTime + writeTime =          4211537563
Synched ArrayList 5r 5w         2142587516     2510353954
readTime + writeTime =          4652941470
Synched ArrayList 5r 5w         2551946855     2428409813
readTime + writeTime =          4980356668
Synched ArrayList 5r 5w         1924263152     2618208257
readTime + writeTime =          4542471409
Synched ArrayList 5r 5w         2167792923     1569884504
readTime + writeTime =          3737677427
Synched ArrayList 5r 5w         1778993149     2370790265
readTime + writeTime =          4149783414
Synched ArrayList 5r 5w         2283551989     2370381682
readTime + writeTime =          4653933671

CopyOnWriteArrayList 10r 0w      168646022              0
CopyOnWriteArrayList 10r 0w       96321052              0
CopyOnWriteArrayList 10r 0w       96286147              0
CopyOnWriteArrayList 10r 0w       97172611              0
CopyOnWriteArrayList 10r 0w       98519500              0
CopyOnWriteArrayList 10r 0w      103441497              0
CopyOnWriteArrayList 10r 0w      100678423              0
CopyOnWriteArrayList 10r 0w      113322448              0
CopyOnWriteArrayList 10r 0w       99801715              0
CopyOnWriteArrayList 10r 0w      106348293              0
CopyOnWriteArrayList 9r 1w       129540555      133314307
readTime + writeTime =           262854862
CopyOnWriteArrayList 9r 1w        85410943       71634587
readTime + writeTime =           157045530
CopyOnWriteArrayList 9r 1w       132068025       79406404
readTime + writeTime =           211474429
CopyOnWriteArrayList 9r 1w       110474683       75584914
readTime + writeTime =           186059597
CopyOnWriteArrayList 9r 1w       101389853       80056237
readTime + writeTime =           181446090
CopyOnWriteArrayList 9r 1w       164770635       92077633
readTime + writeTime =           256848268
CopyOnWriteArrayList 9r 1w        95487458      104530198
readTime + writeTime =           200017656
CopyOnWriteArrayList 9r 1w        98308534       88276675
readTime + writeTime =           186585209
CopyOnWriteArrayList 9r 1w       125598953       85595216
readTime + writeTime =           211194169
CopyOnWriteArrayList 9r 1w       142843139      111153258
readTime + writeTime =           253996397
CopyOnWriteArrayList 5r 5w        73357209     1900643827
readTime + writeTime =          1974001036
CopyOnWriteArrayList 5r 5w        54237951     1252454966
readTime + writeTime =          1306692917
CopyOnWriteArrayList 5r 5w       225598800     1392446745
readTime + writeTime =          1618045545
CopyOnWriteArrayList 5r 5w        61249065     1124323412
readTime + writeTime =          1185572477
CopyOnWriteArrayList 5r 5w        49956036     1193302717
readTime + writeTime =          1243258753
CopyOnWriteArrayList 5r 5w        52670346     1201316298
readTime + writeTime =          1253986644
CopyOnWriteArrayList 5r 5w        68330498     1362425592
readTime + writeTime =          1430756090
CopyOnWriteArrayList 5r 5w        56557538     1200457552
readTime + writeTime =          1257015090
CopyOnWriteArrayList 5r 5w        65781983     1257803514
readTime + writeTime =          1323585497
CopyOnWriteArrayList 5r 5w        70762497     1145548207
readTime + writeTime =          1216310704

*/
