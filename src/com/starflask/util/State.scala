package com.starflask.util

/*
 * allows us to compose multiple state changes into a single state transition.
 * https://github.com/vmarquez/purebomberman
 * 
 */
  case class State[A,B](run: A=>(A,B)) {
        def map[C](fmap: B=>C) = 
            State[A,C](a => {
                val (na,b) = run(a)
                val c = fmap(b)
                (na,c)
            })

        def flatMap[C](fmap: B=>State[A,C]) = 
            State[A,C](a => {
                val (na,b) = run(a)
                fmap(b).run(na)
            })
    }