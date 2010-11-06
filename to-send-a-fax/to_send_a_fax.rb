#!/usr/bin/ruby

# Google Labs advert
# Communications of the ACM, August 2004 Volume 47, Number 8
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

@nletters_zero_to_nineteen = [ 'zero', 'one', 'two', 'three', 'four', 'five',
    'six', 'seven', 'eight', 'nine', 'ten', 'eleven', 'twelve', 'thirteen',
    'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen',
    'nineteen' ].collect { |s| s.length }

@nletters_twenty_to_ninety = [ 'twenty', 'thirty', 'forty', 'fifty',
    'sixty', 'seventy', 'eighty', 'ninety' ].collect { |s| s.length }

def is_multiple(m, n)
    if m % n == 0
        return true
    else
        return false
    end
end

def E(x)
    if 0 <= x && x <= 19
        return @nletters_zero_to_nineteen[x]
    elsif 20 <= x && x <= 99
        tens = @nletters_twenty_to_ninety[(x/10) - 2]
        if is_multiple(x, 10)
            return tens
        else
            return tens + E(x % 10)
        end
    elsif 100 <= x && x <= 999
        hundreds = E(x/100) + 'hundred'.length
        if is_multiple(x, 100)
            return hundreds
        else
            return hundreds + E(x % 100)
        end
    elsif 1000 <= x && x <= 999999
        thousands = E(x/1000) + 'thousand'.length
        if is_multiple(x, 1000)
            return thousands
        else
            return thousands + E(x % 1000)
        end
    elsif -999999 <= x && x <= -1
        return 'minus'.length + E(x.abs)
    else
        print 'E x out of range'
    end
end

def f(x)
    return 3 * (E(x)**3) - x
end

def solve(want, max)
    for i in 0..max
        if yield(i) == want
            print(i, "\n")
        end
    end
end

solve(60097, 9999) { |x| f(f(f(x))) }
solve(1, 9999) { |x| f(f(x)) }
