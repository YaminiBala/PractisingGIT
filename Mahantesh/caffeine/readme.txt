caffeine
===============================================================================


Run this to prevent your PC screen from locking.  It does this by simulating
that you've used the F15 key once every 59 seconds.

Double-click the icon to temporarily disable Caffeine, and permit your screen
to lock.

Under Win98:      a key down and then a key up event is simulated
Under Win2000/XP: a key up event is simulated

The additional event is required under Windows 98, otherwise the screen will
still lock.


Command-line options:

caffeine.exe -startoff		Application starts inactive
caffeine.exe -exitafter:xxx	Application will terminate after xxx minutes
caffeine.exe -activefor:xxx	Application will become inactive after
				xxx minutes
caffeine.exe -inactivefor:xxx	Application will become active after
				xxx minutes
caffeine.exe -appexit		Terminates current running instance of
				application
caffeine.exe -appon		Makes the current running instance of the
				application active
caffeine.exe -appoff		Makes the current running instance of the
				application inactive
caffeine.exe -apptoggle		Toggles the state of the current running instance of the
				application
caffeine.exe xx                 xx is a number which sets the number of seconds
                                between simulated keypresses. This must be the
                                first text on the commandline
caffeine.exe -noicon		Do not show the tray icon.  Use either the above
				-appexit switch to terminate the app, or just
				kill the process in Task Manager
caffeine.exe -replace		Terminate the current running instance of Caffeine
				and then take its place
caffeine.exe -useshift		Simulate the shift key rather than the right
				context-menu key
caffeine.exe -showdlg		Display a dialog showing the current active state of
				Caffeine

-------------------------------------------------------------------------------
Copyright Tom Revell, tom.revell@zhornsoftware.co.uk
Zhorn Software, http://www.zhornsoftware.co.uk/