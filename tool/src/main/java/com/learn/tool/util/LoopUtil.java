package com.learn.tool.util;

/**
 * 循环获取全部数据
 */
public class LoopUtil {

    public static void loop(LoopFunction loopFunction, int maxRound) {
        while (maxRound-- > 0) {
            if (!loopFunction.proc()) {
                break;
            }
        }
    }

    public interface LoopFunction {
        boolean proc();
    }

}

