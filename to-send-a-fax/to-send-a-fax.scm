#! /usr/bin/env scheme-r5rs

; Google Labs advert
; Communications of the ACM, August 2004 Volume 47, Number 8
;
; To send a fax,
;
; Dial the four-digit access code Y
; where 60097 equals f(f(f(Y))).
;
; This machine has extension number Z
; where f(f(Z)) = 1.
;
; (If you forgot your orientation packet,
; E(x) = number of letters
; when x is written out in American English
; f(x) = 3[E(x)]^3-x)

(define E
  (let ((nletters-zero-to-nineteen
         (map string-length
           '("zero"      "one"       "two"       "three"     "four"
             "five"      "six"       "seven"     "eight"     "nine"
             "ten"       "eleven"    "twelve"    "thirteen"  "fourteen"
             "fifteen"   "sixteen"   "seventeen" "eighteen"  "nineteen")))
        (nletters-twenty-to-ninety
         (map string-length
           '(""          ""          "twenty"    "thirty"    "forty"
             "fifty"     "sixty"     "seventy"   "eighty"    "ninety")))
        (multiple? (lambda (m n) (= (remainder m n) 0))))
    (lambda (x)
      (cond ((<= 0 x 19)
              (list-ref nletters-zero-to-nineteen x))
            ((<= 20 x 99)
              (+ (list-ref nletters-twenty-to-ninety (quotient x 10))
                 (if (multiple? x 10)
                   0
                   (E (remainder x 10)))))
            ((<= 100 x 999)
              (+ (E (quotient x 100))
                 (string-length "hundred")
                 (if (multiple? x 100)
                   0
                   (E (remainder x 100)))))
            ((<= 1000 x 999999)
              (+ (E (quotient x 1000))
                 (string-length "thousand")
                 (if (multiple? x 1000)
                   0
                   (E (remainder x 1000)))))
            ((<= -999999 x -1)
              (+ (string-length "minus") (E (abs x))))
            (else
              (begin
                (display "E x out of range ")
                (write x)
                (newline)))))))

(define (f x)
  (- (* 3 (expt (E x) 3)) x))

(define (solve want func i max)
  (if (eq? want (func i))
    (begin (display i) (newline)))
  (if (< i max) (solve want func (+ i 1) max)))

(define (main arguments)
  (solve 60097 (lambda (x) (f (f (f x)))) 0 9999)
  (solve 1 (lambda (x) (f (f x))) 0 9999))
