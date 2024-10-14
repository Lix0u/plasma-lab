#!/usr/bin/env sh
#
# This file is part of fr.inria.plasmalab.root.
#
# fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# fr.inria.plasmalab.root is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
#


export PLASMA_HOME=$(cd $(dirname "$0") && pwd)

pattern="fr.inria.plasmalab.uiterminal"
bin=$(ls $PLASMA_HOME/libs/$pattern*)

java -jar $bin "$@"
