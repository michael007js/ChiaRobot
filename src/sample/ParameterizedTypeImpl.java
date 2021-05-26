package sample;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public   class ParameterizedTypeImpl implements ParameterizedType {
    Class clazz;

    public ParameterizedTypeImpl(Class clz) {
        clazz = clz;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}