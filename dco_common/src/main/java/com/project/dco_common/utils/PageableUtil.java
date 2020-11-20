package com.project.dco_common.utils;

import com.project.dco_common.constants.DateTimeConstants;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PageableUtil {

    public static <T> boolean searchObjectByField(T object, String searchField, String searchText) {
        try {
            Field field = object.getClass().getDeclaredField(searchField);
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue = (String) field.get(object);
            if (searchField.equals(fieldName) && fieldValue.contains(searchText)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static <T> boolean filterByDate(T object, String dateField, String timeForm, String timeTo) {
        try {
            Field field = object.getClass().getDeclaredField(dateField);
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldValue = (String) field.get(object);
            Date dateValue = DateTimeUtils.toDateFromString(fieldValue, DateTimeConstants.YYYY_MM_DD_HYPHEN);
            Date timeFromDate = DateTimeUtils.toDateFromString(timeForm, DateTimeConstants.YYYY_MM_DD_HYPHEN);
            Date timeToDate = DateTimeUtils.toDateFromString(timeTo, DateTimeConstants.YYYY_MM_DD_HYPHEN);
            if (!ObjectUtils.isEmpty(dateValue) && dateField.equals(fieldName) &&
                    !(DateTimeUtils.compareDate(timeFromDate, dateValue) == 1) &&
                    !(DateTimeUtils.compareDate(dateValue, timeToDate) == 1)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static <T> List<T> getSort(List<T> arr, String sortField, String sortType, Class<T> clz) throws NoSuchFieldException {
        Field field = clz.getDeclaredField(sortField);
        field.setAccessible(true);
        return arr.stream().sorted((e1, e2) -> {
            try {
                String a = (String) field.get(e1);
                String b = (String) field.get(e2);
                if (sortType.equals("ASC")) {
                    return a.compareTo(b);
                } else if (sortType.equals("DESC")) {
                    return b.compareTo(a);
                }
                return 0;
            } catch (IllegalAccessException e) {
                return 0;
            }
        }).collect(Collectors.toList());
    }

    public static List<?> getPageable(int page, int perPage, List<?> accounts) {
        int sizeList = accounts.size();
        int startPage = (page - 1) * perPage;
        int endPage = startPage + perPage;
        if (startPage >= sizeList) {
            return new ArrayList<>();
        }
        if (endPage <= sizeList) {
            return accounts.subList(startPage, endPage);
        }
        return accounts.subList(startPage, sizeList);
    }
}
