package jp.takawitter.drools.ios.compile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ClassWriter {
	public static void write(String name, byte[] bytecode){
		File f = new File(outDir, name.replaceAll("\\.", "/") + ".class");
		try {
			f.getParentFile().mkdirs();
			Files.write(f.toPath(), bytecode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		generatedClasses.add(name);
	}

	public static List<String> getGeneratedClasses() {
		return generatedClasses;
	}

	public static void setOutDir(File outDir){
		ClassWriter.outDir = outDir;
	}

	private static File outDir = new File("");
	private static List<String> generatedClasses = new ArrayList<>();
}
