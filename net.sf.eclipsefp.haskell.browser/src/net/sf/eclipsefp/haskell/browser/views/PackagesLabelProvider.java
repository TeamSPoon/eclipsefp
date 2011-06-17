package net.sf.eclipsefp.haskell.browser.views;

import net.sf.eclipsefp.haskell.browser.DatabaseType;
import net.sf.eclipsefp.haskell.browser.util.ImageCache;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class PackagesLabelProvider implements ILabelProvider {

	public Image getImage(Object element) {
		return element instanceof DatabaseType ? ImageCache.DATABASE : ImageCache.PACKAGE;
	}

	public String getText(Object element) {
		if (element instanceof DatabaseType) {
			switch ((DatabaseType) element) {
			case ALL:
				return "All";
			case HACKAGE:
				return "Hackage";
			case LOCAL:
				return "Local";
			case PACKAGE:
				return "Package";
			}
			return "";
		} else {
			PackagesItem pkg = (PackagesItem)element;
			return pkg.getPackage().getIdentifier().toString();
		}
	}

	// Listeners: not used
	public void addListener(ILabelProviderListener listener) {
		// Do nothing
	}

	public void dispose() {
		// Do nothing
	}

	public boolean isLabelProperty(Object element, String property) {
		// Do nothing
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// Do nothing
	}
}
