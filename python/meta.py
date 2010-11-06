class Meta:
    def one(self):
        return "1"

    def two(self):
        return "2"

    def __getattr__(self, name):
        if name == "foo":
            return self.one
        else:
            return self.two

m = Meta()
print m.foo()
print m.bar()
print m.baz()

class NewStyleMeta(object):
    def foo(self):
        return "foo!"

    def __getattribute__(self, name):
        print "intercepted %s" % name
        return object.__getattribute__(self, name)

n = NewStyleMeta()
print n.foo()

class FirstClassMethods:
    def __init__(self):
        self.val = 1
        self.bar = self.foo

    def foo(self):
        return self.val

f = FirstClassMethods()
print f.foo()
print f.bar()
