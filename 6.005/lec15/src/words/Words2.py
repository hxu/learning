from java.io import File
from java.lang import String
from operator import concat

def flatten(l): 
    return reduce(concat, l, [])
    
def allFilesIn(folder):
    children = folder.listFiles()
    descendents = flatten(map(allFilesIn, filter(File.isDirectory, children)))
    return descendents + filter(File.isFile, children)

def endsWith(suffix):
    return lambda f: f.getPath().endswith(suffix)



def compose(f, g):
    """Requires that f and g are functions, f:A->B and g:B->C.
    Returns a function A->C by composing f with g.""" 
    return lambda x: g(f(x))

def chain(funcs):
    """Requires that funcs is a list of functions [A->B, B->C, ..., Y->Z]. 
    Returns a function A->Z that is the left-to-right composition of funcs."""
    return reduce(compose, funcs)

readIn = chain([File.getPath, open, list])



def splitIntoWords(s):
    nonempty = lambda s: len(s) > 0
    return filter(nonempty, list(String.split(s, "\\W+")))
 
 
# the whole program

roots = map(File, ["."])
files = filter(endsWith(".java"), flatten(map(allFilesIn, roots)))
lines = flatten(map(readIn, files))
words = flatten(map(splitIntoWords, lines))
print "\n".join(words)
