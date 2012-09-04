def adder(arg1, arg2):
    return arg1 + arg2

#print adder(1, 2)
#print adder("one", "two")
#print adder(1.1, 3.4)
#print adder(1, 3.6)
#print adder(range(3), [2,4,77])

import math

nums = [2, 4, 9, 16, 25]

for i in nums:
    print math.sqrt(i)


result = map(math.sqrt, nums)
print "map method"
print result

result2 = [math.sqrt(x) for x in nums]
print "list comprehension"
print result2
