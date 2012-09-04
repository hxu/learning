
def countLines(name):
    myfile = open(name)
    print len(myfile.readlines())

def countChars(name):
    myfile = open(name)
    print len(myfile.read())

def test(name):
    print 'Number of lines:'
    countLines(name)
    print 'Number of characters:'
    countChars(name)


if __name__ == '__main__':
    test('mymod.py')
