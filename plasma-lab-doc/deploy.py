import conf


TARGET = "scm.gforge.inria.fr"
DIRECTORY = "/home/groups/plasma-lab/htdocs/plasma_lab_doc/"

print("Deploying documentation for {} to {}:{}".format(conf.release, TARGET, DIRECTORY))

print("ssh {} -C 'mkdir -p {}'".format(TARGET, DIRECTORY + conf.release))
print("rsync -avz _build/* {}:{}".format(TARGET, DIRECTORY + conf.release))

print("scp index.php scm.gforge.inria.fr:/home/groups/plasma-lab/htdocs/plasma_lab_doc/")
print("scp manuel.php scm.gforge.inria.fr:/home/groups/plasma-lab/htdocs/plasma_lab_doc/")
