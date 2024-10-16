#!/usr/bin/env bash
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

USER=$1
VERSION=$2

# create directory
ssh $USER@scm.gforge.inria.fr -C "mkdir -p /home/groups/plasma-lab/htdocs/plasma_lab_doc/$VERSION"
# copy _build (hmtl and latex)
rsync -avz _build/* "$USER@scm.gforge.inria.fr:/home/groups/plasma-lab/htdocs/plasma_lab_doc/$VERSION"
# change version in php files
sed -i "s#plasma_lab_doc/...../#plasma_lab_doc/$VERSION/#g" index.php
sed -i "s#plasma_lab_doc/...../#plasma_lab_doc/$VERSION/#g" manuel.php
# upload php files
scp index.php "$USER@scm.gforge.inria.fr:/home/groups/plasma-lab/htdocs/plasma_lab_doc/"
scp manuel.php "$USER@scm.gforge.inria.fr:/home/groups/plasma-lab/htdocs/plasma_lab_doc/"
