package org.drools.android;

import java.security.ProtectionDomain;

import org.drools.core.util.ByteArrayClassLoader;

public class MultiDexClassLoader extends ClassLoader implements ByteArrayClassLoader {
	public MultiDexClassLoader(ClassLoader parent) {
		super(parent);
	}

	/**
	 * Just load a class instead of defining class.
	 * Classes generated from rules should be compiled beforehand
	 *  and packed into rules.jar.
	 */
	@Override
	public Class< ? > defineClass(
			String name, byte[] bytes, ProtectionDomain domain) {
		try {
			return loadClass(name);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
