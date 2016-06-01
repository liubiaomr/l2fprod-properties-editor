/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.propertysheet;

import com.l2fprod.common.beans.ExtendedPropertyDescriptor;

import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PropertyDescriptorAdapter.<br>
 *
 */
class PropertyDescriptorAdapter extends AbstractProperty {

    private PropertyDescriptor descriptor;

    public PropertyDescriptorAdapter() {
        super();
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PropertyDescriptorAdapter(PropertyDescriptor descriptor) {
        this();
        setDescriptor(descriptor);
    }

    public void setDescriptor(PropertyDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public PropertyDescriptor getDescriptor() {
        return descriptor;
    }

    @Override
    public String getName() {
        return descriptor.getName();
    }

    @Override
    public String getDisplayName() {
        return descriptor.getDisplayName();
    }

    @Override
    public String getShortDescription() {
        return descriptor.getShortDescription();
    }

    @Override
    public Class<?> getType() {
        return descriptor.getPropertyType();
    }

    @Override
    public Object clone() {
        PropertyDescriptorAdapter clone = new PropertyDescriptorAdapter(descriptor);
        clone.setValue(getValue());
        return clone;
    }

    @Override
    public void readFromObject(Object object) {
        try {
            Method method = descriptor.getReadMethod();
            if (method != null) {
                setValue(method.invoke(object));
            }
        } catch (IllegalAccessException e) {
            String message = "Got exception when reading property " + getName();
            if (object == null) {
                message += ", object was 'null'";
            } else {
                message += ", object was " + String.valueOf(object);
            }
            throw new RuntimeException(message, e);
        } catch (IllegalArgumentException e) {
            String message = "Got exception when reading property " + getName();
            if (object == null) {
                message += ", object was 'null'";
            } else {
                message += ", object was " + String.valueOf(object);
            }
            throw new RuntimeException(message, e);
        } catch (InvocationTargetException e) {
            String message = "Got exception when reading property " + getName();
            if (object == null) {
                message += ", object was 'null'";
            } else {
                message += ", object was " + String.valueOf(object);
            }
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public void writeToObject(Object object) {
        Method method = descriptor.getWriteMethod();
        if (method != null) {
            try {
                method.invoke(object, new Object[]{getValue()});
            } catch (IllegalAccessException e) {
                String message = "Got exception when writing property " + getName();
                if (object == null) {
                    message += ", object was 'null'";
                } else {
                    message += ", object was " + String.valueOf(object);
                }
                throw new RuntimeException(message, e);
            } catch (IllegalArgumentException e) {
                String message = "Got exception when writing property " + getName();
                if (object == null) {
                    message += ", object was 'null'";
                } else {
                    message += ", object was " + String.valueOf(object);
                }
                throw new RuntimeException(message, e);
            } catch (InvocationTargetException e) {
                // let PropertyVetoException go to the upper level without logging
                if (e instanceof InvocationTargetException
                        && ((InvocationTargetException) e).getTargetException() instanceof PropertyVetoException) {
                    throw new RuntimeException(((InvocationTargetException) e).getTargetException());
                }

                String message = "Got exception when writing property " + getName();
                if (object == null) {
                    message += ", object was 'null'";
                } else {
                    message += ", object was " + String.valueOf(object);
                }
                throw new RuntimeException(message, e);
            }
        }
    }

    @Override
    public boolean isEditable() {
        return descriptor.getWriteMethod() != null;
    }

    @Override
    public String getCategory() {
        if (descriptor instanceof ExtendedPropertyDescriptor) {
            return ((ExtendedPropertyDescriptor) descriptor).getCategory();
        } else {
            return null;
        }
    }

}
