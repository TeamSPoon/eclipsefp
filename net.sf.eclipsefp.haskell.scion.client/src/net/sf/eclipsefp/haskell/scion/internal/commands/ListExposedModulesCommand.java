package net.sf.eclipsefp.haskell.scion.internal.commands;

/**
 * 
 * @author JP Moresmau
 *
 */
public class ListExposedModulesCommand extends DefinedNamesCommand {

	public ListExposedModulesCommand(){
		super();
	}
	
	@Override
	protected String getMethod() {
		return "list-exposed-modules";
	}

}
