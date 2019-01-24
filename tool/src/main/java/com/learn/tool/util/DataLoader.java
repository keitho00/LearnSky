package com.learn.tool.util;


import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 数据加载
 */
class DataLoader {

    private static final int LIMIT = 1000;
    private static final int MAX_ROUND = 100000;

    static <R> List<R> load(Function<StartIdQuery, List<R>> queryFunc, Predicate<R> predicate,
                            long startId, Function<R, Long> idFunc) {

        List<R> list = Lists.newArrayList();

        StartIdQuery query = StartIdQuery.builder().startId(startId).limit(LIMIT).build();
        LoopUtil.loop(() -> {
            List<R> loopList = queryFunc.apply(query);

            if (CollectionUtils.isEmpty(loopList)) {
                return false;
            }

            loopList.stream().filter(predicate).forEach(list::add);

            query.setStartId(idFunc.apply(Iterables.getLast(loopList)));

            return LIMIT == CollectionUtils.size(loopList);
        }, MAX_ROUND);

        return list;
    }

    @Data
    @Builder
    public static class StartIdQuery {

        private long startId;
        private int limit;

    }
}
