import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int X = sc.nextInt();
      int D = sc.nextInt();
      int M = sc.nextInt();
      int[] P = new int[M];
      int[] L = new int[M];
      int[] R = new int[M];
      for (int i = 0; i < M; ++i) {
        P[i] = sc.nextInt() - 1;
        L[i] = sc.nextInt();
        R[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(N, K, X, D, P, L, R)));
    }

    sc.close();
  }

  @SuppressWarnings("unchecked")
  static int solve(int N, int K, int X, int D, int[] P, int[] L, int[] R) {
    List<Integer>[] inMeetings = new List[D + 1];
    for (int i = 0; i < inMeetings.length; ++i) {
      inMeetings[i] = new ArrayList<>();
    }
    List<Integer>[] outMeetings = new List[D + 1];
    for (int i = 0; i < outMeetings.length; ++i) {
      outMeetings[i] = new ArrayList<>();
    }
    for (int i = 0; i < P.length; ++i) {
      inMeetings[L[i]].add(i);
      outMeetings[R[i]].add(i);
    }

    int result = Integer.MAX_VALUE;
    int[] freqs = new int[N];
    SortedMap<Integer, Integer> lowerFreqToCount = new TreeMap<>();
    lowerFreqToCount.put(0, K);
    int lowerFreqSum = 0;
    SortedMap<Integer, Integer> upperFreqToCount = new TreeMap<>();
    if (K != N) {
      upperFreqToCount.put(0, N - K);
    }
    for (int endTime = 0; endTime <= D; ++endTime) {
      int beginTime = endTime - X;

      if (beginTime >= 0) {
        for (int outMeeting : outMeetings[beginTime]) {
          lowerFreqSum =
              update(
                  lowerFreqSum,
                  lowerFreqToCount,
                  upperFreqToCount,
                  freqs[P[outMeeting]],
                  freqs[P[outMeeting]] - 1);
          --freqs[P[outMeeting]];
        }

        result = Math.min(result, lowerFreqSum);
      }

      for (int inMeeting : inMeetings[endTime]) {
        lowerFreqSum =
            update(
                lowerFreqSum,
                lowerFreqToCount,
                upperFreqToCount,
                freqs[P[inMeeting]],
                freqs[P[inMeeting]] + 1);
        ++freqs[P[inMeeting]];
      }
    }

    return result;
  }

  static int update(
      int lowerFreqSum,
      SortedMap<Integer, Integer> lowerFreqToCount,
      SortedMap<Integer, Integer> upperFreqToCount,
      int oldFreq,
      int newFreq) {
    if (oldFreq <= lowerFreqToCount.lastKey()) {
      updateMap(lowerFreqToCount, oldFreq, -1);
      lowerFreqSum -= oldFreq;

      if (newFreq < oldFreq
          || upperFreqToCount.isEmpty()
          || upperFreqToCount.firstKey() >= newFreq) {
        updateMap(lowerFreqToCount, newFreq, 1);
        lowerFreqSum += newFreq;
      } else {
        int freq = upperFreqToCount.firstKey();
        updateMap(upperFreqToCount, freq, -1);
        updateMap(lowerFreqToCount, freq, 1);
        lowerFreqSum += freq;

        updateMap(upperFreqToCount, newFreq, 1);
      }
    } else {
      updateMap(upperFreqToCount, oldFreq, -1);

      if (newFreq > oldFreq || lowerFreqToCount.lastKey() <= newFreq) {
        updateMap(upperFreqToCount, newFreq, 1);
      } else {
        int freq = lowerFreqToCount.lastKey();
        updateMap(lowerFreqToCount, freq, -1);
        lowerFreqSum -= freq;
        updateMap(upperFreqToCount, freq, 1);

        updateMap(lowerFreqToCount, newFreq, 1);
        lowerFreqSum += newFreq;
      }
    }

    return lowerFreqSum;
  }

  static void updateMap(SortedMap<Integer, Integer> freqToCount, int freq, int delta) {
    freqToCount.put(freq, freqToCount.getOrDefault(freq, 0) + delta);
    freqToCount.remove(freq, 0);
  }
}