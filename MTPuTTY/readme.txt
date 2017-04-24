Overview
========
MTPuTTY (Multi-Tabbed PuTTY) is a small utility enabling you 
to wrap unlimited number of PuTTY applications in one tabbed 
GUI interface.

History
=======

Ver 1.6.0.176
-------------
Now you can remap hotkeys.
Copy/paste servers added.
Added serial connections.
Added command to duplicate session.
Several minor improvements.
Several minor bugs fixed.

Ver 1.5.0.150
-------------
Added option to hide start page.
You can now duplicate servers/sessions (mouse + Ctrl key).
Added commands to import and export tree.
Option to hide main menu added.
You can now rename tabs.
Added option to not show close buttons on tabs (use middle button to close).
Several minor bugs fixed.


Ver 1.4.1.129 
-------------
Attach command to attach "orphan" PuTTY sessions.
Connect to command to connect to any server (not listed in the tree).
Fixed compatibility with Putty 0.60 and below. However version 0.61+ is recommended.
Minor bugs fixed.


Ver 1.4.0.110
-------------
Docking and tiling of tabs is totally redesigned. Now it looks and behaves much better.
Portable mode. You may copy mtptutty.exe and mtputty.xml to a flash drive.
MTPuTTY now remembers logins, passwords and scripts in Putty Sessions folder.
Save/load script command added.
Setial connections are listed under Putty sessions.
WinMenu key popups Putty System menu.
Added Start page with helpful controls.
Minor bugs fixed.



Ver 1.3.0.75
------------
Tiling. Putty tabs can be dragged and docked as tiles.
Full KiTTY support.
Putty System menu popups on right click on a tab.
MTPuTTY now remembers folder expand/collapse state.
MTPuTTY now remembers location of Server tree.
Optional confirmation on quit.
Some bugs fixed.


Ver 1.2.3.69
------------
PuTTY 0.61 compatibility.



Ver 1.2.2.52
------------
Sessions containing spaces in name didn't work under PuTTY Sessions folder. Fixed.
Fixed a small bug linked with new folder creation in the tree.
Added "Reload PuTTY sessions" command to refresh sessions.


Ver 1.2
-------
You can now create folders in the tree.
You can now sort items in the tree.
Hide toolbar command added.
Added hotkey (Ctrl + ~) to switch focus between the application and PuTTY.
MTPuTTY now saves and restores its position.
Fixed some minor bugs.



Ver 1.1.0.39:
------------
Bugfixes:
  Passwords were not typed correctly in PuTTY window. Fixed.
  Password via command line (-pw) did not work. Fixed.
  


Ver 1.1:
-------

Ctrl+Tab and Ctrl+Shift+Tab switch between tabs.
Automatically detects installed PuTTY.
Automatically imports all PuTTY sessions into the tree.
Added option to replicate PuTTY titles as tab names.
Extended toolbar to connect any server without navigating in the tree.
The program strictly respects all CRLFs in the scripts. If you forget to add a closing CRLF in the script, Enter key will not be sent to PuTTY.
Basic KiTTY support added - you can specify kitty.exe instead of putty.exe, but it doesn't import KiTTY sessions.
Fixed lost focus issue when you switch back into MTPuTTY using Alt+Tab.
Added option to close tab if the session terminated normally.
Added option to leave PuTTY window if the session terminated unexpectedly.
Added hotkey to show/hide tree.
Send Script dialog now saves state of the checkboxes.


Ver 1.0:
------- 
First public beta