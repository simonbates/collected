; traditional recursive definition of factorial

(define (fact n)
  (if (= n 0)
      1
      (* n (fact (- n 1)))))

; tail-recursive version
; an R5RS syntax version of the example in
; Scheme: An interpreter for extended lambda calculus
; by Gerald Jay Sussman and Guy Lewis Steele Jr.
; MIT AI Lab Memo AIM-349. December 1975.
; http://repository.readscheme.org/ftp/papers/ai-lab-pubs/AIM-349.pdf

(define (fact n)
  (letrec
      ((fact1 (lambda (m ans)
               (if (= m 0)
                   ans
                   (fact1 (- m 1) (* m ans))))))
    (fact1 n 1)))
