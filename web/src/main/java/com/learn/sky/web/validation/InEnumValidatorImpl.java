package com.learn.sky.web.validation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Slf4j
public class InEnumValidatorImpl implements ConstraintValidator<InEnum, Integer> {

    private List<Integer> valueList = Lists.newArrayList();

    @Override
    public void initialize(InEnum constraintAnnotation) {
        Class<? extends Enum> clazz = constraintAnnotation.clazz();
        String valueField = constraintAnnotation.valueField();
        Enum[] enums = clazz.getEnumConstants();

        Stream.of(enums).forEach(e -> {
            try {
                valueList.add(FieldUtils.getField(clazz, valueField, true).getInt(e));
            } catch (Exception ex) {
                log.error("error when getting enum value.", ex);
                throw new IllegalArgumentException();
            }
        });
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        return valueList.contains(value);
    }

}
