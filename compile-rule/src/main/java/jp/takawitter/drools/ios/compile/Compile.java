package jp.takawitter.drools.ios.compile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.drools.core.util.DroolsStreamUtils;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieSession;

public class Compile {
	private static void fireAllRules(KieSession session){
		session.insert("world");
		session.fireAllRules();
	}

	public static void main(String[] args) throws Throwable{
		try(InputStream is = Compile.class.getResourceAsStream("/rule.drl")){
			File outDir = new File("out");
			ClassWriter.setOutDir(outDir);

			KieServices kieServices = KieServices.Factory.get();
			KieFileSystem kfs = kieServices.newKieFileSystem().write(
					"src/main/resources/rule.drl",
					kieServices.getResources().newInputStreamResource(is)
					);

			KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
			Results results = kieBuilder.getResults();
			if(results.hasMessages( Message.Level.ERROR)){
				System.err.println( results.getMessages());
				throw new RuntimeException("### errors ###");
			}

			KieBase base = kieServices.newKieContainer(
					kieServices.getRepository().getDefaultReleaseId())
					.getKieBase();
			File f = new File(outDir, "KiePackages.cache");
			f.getParentFile().mkdirs();
			try(OutputStream os = new FileOutputStream(f)){
				DroolsStreamUtils.streamOut(os, base.getKiePackages());
			}

			KieSession session = base.newKieSession();
			fireAllRules(session);
			session.halt();
			System.out.println("Compilation completed. Run following commands:");
			System.out.println("cd out; jar cf rules.jar defaultpkg; "
					+ "cp rules.jar ../../ios-sample/lib/; cp KiePackages.cache ../../ios-sample/resources/");
			System.out.println();
			System.out.println("and add following lines to robovm.xml of ios-sample project.");
			for(String s : ClassWriter.getGeneratedClasses()){
				System.out.println("\t\t<pattern>" + s + "</pattern>");
			}
		}
	}
}
