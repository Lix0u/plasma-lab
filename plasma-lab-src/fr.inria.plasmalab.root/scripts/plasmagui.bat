@REM
@REM This file is part of fr.inria.plasmalab.root.
@REM
@REM fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
@REM it under the terms of the GNU Lesser General Public License as published by
@REM the Free Software Foundation, either version 3 of the License, or
@REM (at your option) any later version.
@REM
@REM fr.inria.plasmalab.root is distributed in the hope that it will be useful,
@REM but WITHOUT ANY WARRANTY; without even the implied warranty of
@REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
@REM GNU Lesser General Public License for more details.
@REM
@REM You should have received a copy of the GNU Lesser General Public License
@REM along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
@REM

rem Launch plasmagui on windows

set FWDIR=%~dp0
set PLASMA_HOME=%FWDIR%

java -jar libs\fr.inria.plasmalab.uiterminal-1.4.5-SNAPSHOT.jar launch %*
