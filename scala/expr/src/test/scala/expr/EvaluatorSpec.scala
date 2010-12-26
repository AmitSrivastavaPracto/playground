package expr

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class EvaluatorSpec extends Spec with ShouldMatchers {
  val add = new Lambda(Array("X", "Y"), Name("X") + Name("Y"))
  val twice = new Lambda(Array("X"), Call("add", Name("X"), Name("X")))
  val env = new Env().withVariable("X", 1).withVariable("Y", 2).withFunction("add", add).withFunction("twice", twice)

  val examples = Array(
    "1" -> 1
    , "2 + 3" -> 5
    , "X + Y" -> 3
    , "Y ^ (X * 4)" -> 16
    , "4 - Y" -> 2
    , "9 / 3" -> 3
    , "add(1, 2)" -> 3
    , "twice(2)" -> 4
  )

  for((input, expectation) <- examples) {
    it("evaluates " + input + " to " + expectation) {
      val ast = Parser.parse(input)
      val result = Evaluator.eval(ast, env)
      expect(expectation) { result }
    }
  }
}
