package org.drools.android;

import org.drools.core.rule.JavaDialectRuntimeData;

public class DexPackageClassLoader extends ClassLoader {
	public DexPackageClassLoader(
			JavaDialectRuntimeData store,
			ClassLoader parent) {
		super(parent);
	}
}
