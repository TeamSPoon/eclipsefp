// Copyright (c) 2003-2005 by Leif Frenzel - see http://leiffrenzel.de
package net.sf.eclipsefp.haskell.core.project;

import net.sf.eclipsefp.common.core.util.Assert;
import net.sf.eclipsefp.haskell.core.compiler.CompilerManager;
import net.sf.eclipsefp.haskell.core.compiler.ICompilerOutput;
import net.sf.eclipsefp.haskell.core.compiler.IHaskellCompiler;
import net.sf.eclipsefp.haskell.core.compiler.defaultcompiler.DefaultHaskellCompiler;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Implementation for a Haskell project info. Contains information about source
 * paths etc. This is essentially what is stored in the .hsproject file in the
 * project directory.
 * </p>
 * 
 * @author Leif Frenzel
 */
final class HaskellProject implements IHaskellProject {

	private IProject fProject;

	private String sourcePath = "";

	private String outputPath = "";

	private String binPath = "";

	private String targetName = "";

	private IHaskellCompiler fCompiler;

	HaskellProject(final IProject project) {
		fCompiler = CompilerManager.getInstance().getCompiler();
		fProject = project;
	}
	
	// interface methods of IHaskellProject
	// /////////////////////////////////////

	public IProject getResource() {
		return fProject;
	}

	public IPath getSourcePath() {
		return getProjectRelativePath(sourcePath);
	}

	public IPath getOutputPath() {
		return getProjectRelativePath(outputPath);
	}

	public IPath getBinPath() {
		return getProjectRelativePath(binPath);
	}

	public String getTargetName() {
		return targetName;
	}

	// TODO add libraries to properties which listeners are notifed about
	public IImportLibrary[] getImportLibraries() {
		ImportLibrariesList list = new ImportLibrariesList(getResource());
		return list.getAll();
	}

	// interface methods of IAdaptable
	// ////////////////////////////////

	public Object getAdapter(final Class required) {
		Object result = null;
		if (required == IResource.class) {
			result = getResource();
		}
		return result;
	}

	// internal setters
	// /////////////////

	void setSourcePath(final String sourcePath) {
		Assert.isNotNull(sourcePath, "Attempt to set source path to null.");

		String name = IHaskellProject.PROPERTY_SOURCE_PATH;
		ProjectPropertiesEvent event = new ProjectPropertiesEvent(this, name);
		event.setOldValue(getSourcePath());

		this.sourcePath = sourcePath;

		event.setNewValue(getSourcePath());
		HaskellProjectManager.getInstance().broadcast(event);
	}

	void setOutputPath(final String outputPath) {
		Assert.isNotNull(sourcePath, "Attempt to set output path to null.");

		String name = IHaskellProject.PROPERTY_OUTPUT_PATH;
		ProjectPropertiesEvent event = new ProjectPropertiesEvent(this, name);
		event.setOldValue(getOutputPath());

		this.outputPath = outputPath;

		event.setNewValue(getOutputPath());
		HaskellProjectManager.getInstance().broadcast(event);
	}

	void setBinPath(final String binPath) {
		Assert.isNotNull(sourcePath, "Attempt to set bin path to null.");

		String name = IHaskellProject.PROPERTY_BIN_PATH;
		ProjectPropertiesEvent event = new ProjectPropertiesEvent(this, name);
		event.setOldValue(getBinPath());

		this.binPath = binPath;

		event.setNewValue(getBinPath());
		HaskellProjectManager.getInstance().broadcast(event);
	}

	void setTargetName(final String targetName) {
		Assert.isNotNull(targetName, "Attempt to set target name to null.");

		String name = IHaskellProject.PROPERTY_TARGET_NAME;
		ProjectPropertiesEvent event = new ProjectPropertiesEvent(this, name);
		event.setOldValue(getTargetName());

		this.targetName = targetName;

		event.setNewValue(getTargetName());
		HaskellProjectManager.getInstance().broadcast(event);
	}
	
	// helping methods
	// ////////////////

	private IPath getProjectRelativePath(final String whichPath) {
		IPath result;
		if (whichPath.equals("")) {
			result = fProject.getProjectRelativePath();
		} else {
			result = fProject.getFolder(whichPath).getProjectRelativePath();
		}
		return result;
	}

	public IContainer getSourceFolder() {
		IPath sourcePath = getSourcePath();
		if (sourcePath.equals(fProject.getProjectRelativePath())) {
			return fProject;
		}

		return fProject.getFolder(sourcePath);
	}

	public IHaskellCompiler getCompiler() {
		return fCompiler;
	}

	public void setCompiler(IHaskellCompiler compiler) {
		if (null == compiler) {
			compiler = new DefaultHaskellCompiler();
		}
		fCompiler = compiler; 
	}

	public ICompilerOutput compile(IFile file) {
		return getCompiler().compile(file);
	}
}