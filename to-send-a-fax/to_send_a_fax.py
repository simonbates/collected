# Google Labs advert
# Communications of the ACM, August 2004, Volume 47, Number 8
#
# To send a fax,
#
# Dial the four-digit access code Y
# where 60097 equals f(f(f(Y))).
#
# This machine has extension number Z
# where f(f(Z)) = 1.
#
# (If you forgot your orientation packet,
# E(x) = number of letters
# when x is written out in American English
# f(x) = 3[E(x)]^3-x)

_number_of_letters_zero_to_nineteen = [len(s) for s in [
    'zero',      'one',       'two',       'three',     'four',
    'five',      'six',       'seven',     'eight',     'nine',
    'ten',       'eleven',    'twelve',    'thirteen',  'fourteen',
    'fifteen',   'sixteen',   'seventeen', 'eighteen',  'nineteen']]

_number_of_letters_twenty_to_ninety = [len(s) for s in [
    'twenty',    'thirty',    'forty',     'fifty',     'sixty',
    'seventy',   'eighty',    'ninety']]

def E(x):
    """The number of letters when x is written out in American English"""
    if 0 <= x and x <= 19:
        return _number_of_letters_zero_to_nineteen[x]
    elif 20 <= x and x <= 99:
        tens = _number_of_letters_twenty_to_ninety[int(x/10) - 2]
        if _is_multiple(x, 10):
            return tens
        else:
            return tens + E(x % 10)
    elif 100 <= x and x <= 999:
        hundreds = E(int(x/100)) + len('hundred')
        if _is_multiple(x, 100):
            return hundreds
        else:
            return hundreds + E(x % 100)
    elif 1000 <= x and x <= 999999:
        thousands = E(int(x/1000)) + len('thousand')
        if _is_multiple(x, 1000):
            return thousands
        else:
            return thousands + E(x % 1000)
    elif -999999 <= x and x <= -1:
        return len('minus') + E(abs(x))
    else:
        raise Exception('E x out of range')

def _is_multiple(m, n):
    if m % n == 0:
        return True
    else:
        return False

def f(x):
    return 3 * (E(x)**3) - x

def solve(want, func, stop):
    """Returns values of x for which func(x) == want
    for x in the range 0..(stop-1)"""
    solutions = list()
    for x in range(stop):
        if func(x) == want:
            solutions.append(x)
    return solutions

if __name__ == "__main__":
    print 'Solutions for Y in the range 0..9999:',
    print solve(60097, lambda x: f(f(f(x))), 10000)
    print 'Solutions for Z in the range 0..9999:',
    print solve(1, lambda x: f(f(x)), 10000)
