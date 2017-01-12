package jp.takawitter.drools.ios.sample;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIWindow;

public class Sample extends UIApplicationDelegateAdapter {
	@Override
	public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
		rootViewController = new MyViewController();
		window = new UIWindow(UIScreen.getMainScreen().getBounds());
		window.setRootViewController(rootViewController);
		window.makeKeyAndVisible();
		return true;
	}

	public static void main(String[] args) {
		try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
			UIApplication.main(args, null, Sample.class);
		}
	}

	private UIWindow window;
	private MyViewController rootViewController;
}
