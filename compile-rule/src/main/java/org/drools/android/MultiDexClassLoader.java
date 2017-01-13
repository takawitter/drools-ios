/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.android;

import java.security.ProtectionDomain;

import org.drools.core.util.ByteArrayClassLoader;
import org.mvel2.util.MVELClassLoader;

import jp.takawitter.drools.ios.compile.ClassWriter;

/**
 * Maintains separate dex files for each defined class.
 */
public class MultiDexClassLoader extends ClassLoader implements ByteArrayClassLoader, MVELClassLoader {
    public MultiDexClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> defineClass(String name, byte[] bytes) {
        ClassWriter.write(name, bytes);
        return super.defineClass(name, bytes, 0, bytes.length);
    }

    @Override
    public Class< ? > defineClass(final String name,
                                  final byte[] bytes,
                                  final ProtectionDomain domain) {
        return defineClass(name, bytes);
    }

    @Override
    public Class<?> defineClassX(String className, byte[] b, int start, int len) {
        byte[] newBytes = new byte[len];
        System.arraycopy(b, start, newBytes, 0, len);
        return defineClass(className, newBytes);
    }
}
