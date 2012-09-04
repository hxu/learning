class Adder():
    def add(self, x, y):
        print "Not implemented"


class ListAdder(Adder):
    def add(self, x, y):
        return x + y


class DictAdder(Adder):
    def add(self, x, y):
        ret = {}
        for k in x.keys():
            ret[k] = x[k]
        for k in y.keys():
            ret[k] = y[k]

print "hey this works!"


class ListTwo:
    pass
