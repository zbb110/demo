package com.example.demo;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BeanUtil extends BeanUtils {
    public static <E> List<E> listCopyTo(List<?> source, Class<E> destinationClass) {
        try {
            if (source.size() == 0) {
                return Collections.emptyList();
            } else {
                List<E> res = new ArrayList(source.size());
                Iterator var3 = source.iterator();

                while(var3.hasNext()) {
                    Object o = var3.next();
                    E e = destinationClass.newInstance();
                    BeanUtils.copyProperties(o, e);
                    res.add(e);
                }

                return res;
            }
        } catch (IllegalAccessException var6) {
            throw new RuntimeException(var6);
        } catch (InstantiationException var7) {
            throw new RuntimeException(var7);
        }
    }
}
