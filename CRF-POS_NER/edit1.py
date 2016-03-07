import sys
from itertools import izip
w=open(sys.argv[3],'w')
with open(sys.argv[1]) as r,open (sys.argv[2]) as o:
	for line,line1 in izip(r,o):
		if line.strip():
			w.write(line.split("\n")[0]+" "+line1.split("\n")[0]+"\n")
		else:
			w.write("\n")
w.close();

