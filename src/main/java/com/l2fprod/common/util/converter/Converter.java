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
package com.l2fprod.common.util.converter;

/**
 * Converter. Roughly Equivalent to the Apache beanutils converter
 */
public interface Converter {

    /**
     * Converts <code>value</code> to an object of <code>type</code>.
     *
     * @param type
     * @param value
     * @return <code>value</code> converted to an object of <code>type</code>.
     */
    Object convert(Class<?> type, Object value);

    /**
     * Register the converter.
     *
     * @param registry
     */
    void register(Registry registry);
}
