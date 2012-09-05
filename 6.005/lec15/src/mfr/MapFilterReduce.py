
# The definitions below show how map/filter/reduce can be implemented
# in Python.  DON'T USE THESE IN PRACTICE.  Use Python's builtin 
# map/filter/reduce functions instead.
   
def map(f, seq):
    result = []
    for x in seq:
        result.append(f(x))
    return result

def filter(f, seq):
    result = []
    for x in seq:
        if f(x):
            result.append(x)
    return result

def reduce(op, seq, init):
    result = init
    for x in seq:
        result = op(result, x)
    return result

######## map examples

from math import sqrt
map(sqrt, [1,4,9,16]) # ==> [1.0, 2.0, 3.0, 4.0]
map(str.lower, ["A", "b", "C"]) # ==> ['a', 'b', 'c']

# functions are first-class: you can assign them, pass them, return them, etc.
mysqrt = sqrt
mysqrt(25) # ==> 5.0

# defining your own functions: either by name (def) or anonymously (lambda)
def powerOfTwo(k):
    return 2**k

map(powerOfTwo, [1,2,3,4]) # ==> [2, 4, 8, 16]

(lambda k: 2**k) (5) # ==> 32
map(lambda k: 2**k, [1,2,3,4]) # ==> [0,1,2,3]

import io.IOBase
map(IOBase.close, streams) # closes each stream on the list

import threading.Thread
map(Thread.join, threads) # waits for each thread to finish

import operator
map(operator.add, [1,2,3], [4,5,6]) # ==> [5, 7, 9]

######## filter examples

filter(str.isalpha, ['x', 'y', '2', '3', 'a']) # ==> ['x', 'y', 'a']

def isOdd(x): return x % 2 == 1
filter(isOdd, [1,2,3,4]) # ==> [1,3]

filter(lambda s: len(s)>0, ['abc', '', 'd']) # ==> ['abc', 'd']



######## reduce examples

reduce(operator.add, [1,2,3], 0) # ==> 6

reduce(max, [5,8,3,1]) # ==> 8

reduce(lambda s,x: s+str(x), [1,2,3,4], '') # ==> '1234'

reduce(operator.concat, [[1,2],[3,4],[],[5]], []) # ==> [1,2,3,4,5]

def flatten(list):
    return reduce(operator.concat, list, [])

######## bigger examples

def evaluate(a, x):
    xi = map(lambda i: x**i, range(0, len(a))) # ==> [x^0, x^1, x^2, ..., x^n-1]
    axi = map(operator.mul, a, xi) # ==> [a[0]*x^0, a[1]*x^1, a[2]*x^2, ..., a[n-1]*x^n-1]
    return reduce(operator.add, axi, 0) # ==> sum of axi

# What's the highest resolution Nikon sells? 
reduce(max, map(Camera.pixels, filter(lambda c: c.brand() == "Nikon", cameras)))

def fileEndsWith(suffix):
    return lambda file: file.getName().endsWith(suffix)

filter(fileEndsWith(".java"), files)
    
