from to_send_a_fax import E, f
import unittest

class ToSendAFaxTest(unittest.TestCase):
    def test_E(self):
        self.assertEqual(4,  E(0))   # zero
        self.assertEqual(3,  E(1))   # one
        self.assertEqual(3,  E(2))   # two
        self.assertEqual(5,  E(3))   # three
        self.assertEqual(4,  E(4))   # four
        self.assertEqual(4,  E(5))   # five
        self.assertEqual(3,  E(6))   # six
        self.assertEqual(5,  E(7))   # seven
        self.assertEqual(5,  E(8))   # eight
        self.assertEqual(4,  E(9))   # nine
        self.assertEqual(3,  E(10))  # ten
        self.assertEqual(6,  E(11))  # eleven
        self.assertEqual(6,  E(12))  # twelve
        self.assertEqual(8,  E(13))  # thirteen
        self.assertEqual(8,  E(14))  # fourteen
        self.assertEqual(7,  E(15))  # fifteen
        self.assertEqual(7,  E(16))  # sixteen
        self.assertEqual(9,  E(17))  # seventeen
        self.assertEqual(8,  E(18))  # eighteen
        self.assertEqual(8,  E(19))  # nineteen
        self.assertEqual(6,  E(20))  # twenty
        self.assertEqual(9,  E(21))  # twentyone
        self.assertEqual(6,  E(30))  # thirty
        self.assertEqual(5,  E(40))  # forty
        self.assertEqual(5,  E(50))  # fifty
        self.assertEqual(5,  E(60))  # sixty
        self.assertEqual(7,  E(70))  # seventy
        self.assertEqual(6,  E(80))  # eighty
        self.assertEqual(6,  E(90))  # ninety
        self.assertEqual(10, E(99))  # ninetynine
        self.assertEqual(10, E(100)) # one hundred
        self.assertEqual(13, E(101)) # one hundred one
        self.assertEqual(21, E(123)) # one hundred twenty three
        self.assertEqual(21, E(999)) # nine hundred ninety nine

        # one hundred twenty three thousand four hundred fifty six
        self.assertEqual(48, E(123456))

        # nine hundred ninety nine thousand nine hundred ninety nine
        self.assertEqual(50, E(999999))

        # minus one hundred twenty three thousand four hundred fifty six
        self.assertEqual(53, E(-123456))

    def test_f(self):
        # f(x) = 3[E(x)]^3-x

        # f(0) = 3[E(0)]^3
        #      = 3[4]^3
        #      = 192
        self.assertEqual(192, f(0))

        # f(-123) = 3[E(-123)]^3 + 123
        #         = 3[26]^3 + 123
        #         = 52851
        self.assertEqual(52851, f(-123))

if __name__ == '__main__':
    unittest.main()
