import re

def wrap(cells, rule):
    lencells = len(cells)

    #formats integer to binary
    rulebits = '{0:08b}'.format(rule) 

    #generates neighbour sets
    neighbours = {tuple('{0:03b}'.format(n)):rulebits[::-1][n] for n in range(8)} 
    c = cells
    while True:
        yield c
        c = ''.join(neighbours[(c[i-1], c[i], c[(i+1) % lencells])] for i in range(lencells))
 
if __name__ == '__main__':
	#represents the number of required iterations and the rule
    lines,  rule = 100, 30

    #validates input
    invalid = True
    while invalid:
    	start =  raw_input("Enter valid initial state:")
    	matches = re.match('[01]+$', start)
    	if matches:
    		invalid = False

   	#clusters the generated ouput lists/wrapped data depending on the assigned number
   	#lines/iterations and displays them 
    zipped = [range(lines)] + [wrap(start, rule)]
    print('\n   Rule: %r' % (rule))
    for data in zip(*zipped):
        i = data[0]
        cells = data[1:]
        print('%2i: %s' % (i, '    '.join(cells).replace('0', ' ')))
