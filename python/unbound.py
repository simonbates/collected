class Foo:
    def __init__(self, n):
        self.n = n

    def bar(self):
        return 2 * self.n

f = Foo(4)
print Foo.bar(f)
