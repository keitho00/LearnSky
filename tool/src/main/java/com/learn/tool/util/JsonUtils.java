package com.learn.tool.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Lixiaochun
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper OM = new ObjectMapper();

    static {
        OM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Map<String, Object> toMap(String json) {
        return Try.of(() -> OM.<Map<String, Object>>readValue(json, new TypeReference<Map<String, Object>>() {
        })).get();
    }

    public static <T> T toBean(String json, TypeReference<T> typeReference) {
        return Try.of(() -> OM.<T>readValue(json, typeReference)).get();
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        return Try.of(() -> OM.readValue(json, clazz)).get();
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return Try.of(() -> OM.<List<T>>readValue(json, OM.getTypeFactory().constructCollectionLikeType(List.class, clazz))).get();
    }

    public static String toJson(Object obj) {
        return Try.of(() -> OM.writeValueAsString(obj)).get();
    }

    public static class Try {
        public static <T> Supplier<T> of(SupplierWithException<T> supplier) {
            return () -> {
                try {
                    return supplier.get();
                } catch (Exception e) {
                    log.error("error", e);
                    throw new RuntimeException(e);
                }
            };
        }

        @FunctionalInterface
        public interface SupplierWithException<T> {
            /**
             * get
             *
             * @return
             * @throws Exception
             */
            T get() throws Exception;
        }
    }

}
