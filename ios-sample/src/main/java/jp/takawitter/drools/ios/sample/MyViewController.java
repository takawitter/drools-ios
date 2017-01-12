package jp.takawitter.drools.ios.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.drools.core.util.DroolsStreamUtils;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIColor;
import org.robovm.apple.uikit.UIView;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.webkit.WKUserContentController;
import org.robovm.apple.webkit.WKWebView;
import org.robovm.apple.webkit.WKWebViewConfiguration;

public class MyViewController extends UIViewController {
	public MyViewController() {
		UIView view = getView();
		view.setBackgroundColor(UIColor.white());
		WKUserContentController controller = new WKUserContentController();
		WKWebViewConfiguration config = new WKWebViewConfiguration();
		config.setUserContentController(controller);
		CGRect frame = view.getFrame();
		wv = new WKWebView(
				new CGRect(frame.getMinX(), frame.getMinY() + 16,
						frame.getWidth(), frame.getHeight() - 16),
				config);
		view.addSubview(wv);
		NSURL bu = NSBundle.getMainBundle().getBundleURL();
		wv.loadFileURL(new NSURL(bu.toString() + "index.html"), bu);

		try(InputStream is = new NSURL(bu.toString() + "KiePackages.cache").toURL().openStream()){
			KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
			try {
				kbase.addKnowledgePackages((List<KnowledgePackage>) DroolsStreamUtils.streamIn(is));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			KieSession session = kbase.newKieSession();
			session.insert("world");
			session.fireAllRules();
			session.halt();
		} catch(IOException e){
			e.printStackTrace();
		}
		wv.evaluateJavaScript("document.write('done.');", null);
	}

	private final WKWebView wv;
}
