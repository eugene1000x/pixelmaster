
set projectDir=.

javadoc.exe ^
	-d ./output ^
	-encoding utf8 ^
	-sourcepath "%projectDir%/src" ^
	-overview "%projectDir%/src/overview.html" ^
	-subpackages pixelmaster.core.api ^
	-tag author ^
	-link http://java.sun.com/javase/6/docs/api ^
	pixelmaster.modules pixelmaster pixelmaster.core

pause
