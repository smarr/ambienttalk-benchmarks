package at.bench;

import java.io.File;

public interface Constants {
	
	// Default stack size for new event loops
	public static final String _ENV_AT_STACKSIZE_ = "524288";
	
	// Mount point of the 'primary media directory'
	public static final File   _ENV_AT_BASE_ = new File("/");
	
	// Folder under which the assets can be found (see AssetInstaller).
	public static final String _ENV_AT_ASSETS_BASE_ = "atlib";
	
	// Relative paths to at-home 
	public static final String _AT_HOME_RELATIVE_PATH_ = "/Users/smarr/Projects/PostDoc/AliasMOP/at2dist060213/atlib";
	public static final String _AT_TEMP_FILES_PATH = "/Users/smarr/Projects/PostDoc/AliasMOP/at2dist060213/atlib/cache/";
	
	// Result code when AT installation fails
	// public static final int _RESULT_FAIL_ = Activity.RESULT_FIRST_USER;

	// Name of shared preferences file
	public static final String IAT_SETTINGS_FILE = "IatSettings";
	
	// AmbientTalk email address for reporting errors
	public static final String _AT_EMAIL_ADDRESS_	= "ambienttalk@gmail.com";

}