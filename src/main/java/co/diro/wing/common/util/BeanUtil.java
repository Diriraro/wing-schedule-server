
package co.diro.wing.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;



public class BeanUtil {
	public static <C> C convertMapToBean(Map<String, Object> source, Class<C> targetClass) {
	    C bean = null;
	    try {
	        bean = targetClass.newInstance();
	        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(targetClass);

	        for (PropertyDescriptor desc : targetPds) {
	            Object value = source.get(desc.getName());
	            if (value != null) {
	                Method writeMethod = desc.getWriteMethod();
	                if (writeMethod != null) {
	                    String classSimpleName = writeMethod.getParameterTypes()[0].getSimpleName();
	                    //System.out.println("classSimpleName : " + classSimpleName);
	                    if (classSimpleName.equals("int") && value instanceof Double) {
	                    	value = ((Double)value).intValue();
	                    }
	                    else if (classSimpleName.equals("long") && value instanceof Double) {
	                    	value = ((Double)value).longValue();
	                    }
	                    writeMethod.invoke(bean, new Object[] { value });
	                }
	            }
	        }
	    } catch (InstantiationException e) {
	        new IllegalArgumentException("Cannot initiate class",e);
	    } catch (IllegalAccessException e) {
	        new IllegalStateException("Cannot access the property",e);
	    } catch (InvocationTargetException e) {
	        new IllegalArgumentException(e);
	    }
	    return bean;
	}
}