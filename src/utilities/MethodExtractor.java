package utilities;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodExtractor {
    public static Method getPublicMethodByName(Class target, String name) {
        Method[] methods = target.getMethods();
        Method result = null;
        for (Method method: methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                if (method.getName().equals(name)) {
                    result = method;
                    break;
                }
            }
        }
        if (result == null) {
            System.out.println("cannot find '"+name+"' in "+target.getName());
        }
        return result;
    }
}
