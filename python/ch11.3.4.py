
L = [1,2,4,8,16,32,64]
X = 5

i = 0
while i < len(L):
    if 2 ** X == L[i]:
        print 'at index', i
        break
    else:
        i = i+1
else:
    print 'not found'


print 'second method \n'

for l in L:
    if 2 ** X == l:
        print 'at index', L.index(l)

print '\n exercise d \n'

L = []
for i in range(5):
    L.append(2**i)

print L
