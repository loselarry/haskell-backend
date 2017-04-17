package org.kframework.minikore.main.interfaces

object kore {

  sealed trait Pattern

  trait Data

  trait Name {
    val name: Data
  }

  object Name {
    def unapply(arg: Name): Option[Data] = Some(arg.name)
  }

  trait Sort {
    val sort: Data
  }

  object Sort {
    def unapply(arg: Sort): Option[Data] = Some(arg.sort)
  }

  trait Symbol {
    val symbol: Data
  }

  object Symbol {
    def unapply(arg: Symbol): Option[Data] = Some(arg.symbol)
  }

  trait Value {
    val value: Data
  }

  object Value {
    def unapply(arg: Value): Option[Data] = Some(arg.value)
  }

  trait DomainValue extends Pattern {
    val symbol: Symbol
    val value: Value
  }

  object DomainValue {
    def unapply(arg: DomainValue): Option[(Symbol, Value)] = Some(arg.symbol, arg.value)
  }

  trait Variable extends Pattern {
    val name: Name
    val sort: Sort
  }

  object Variable {
    def unapply(arg: Variable): Option[(Name, Sort)] = Some(arg.name, arg.sort)
  }

  trait Top extends Pattern

  object Top {
    def unapply(arg: Top): Boolean = true
  }

  trait Bottom extends Pattern

  object Bottom {
    def unapply(arg: Bottom): Boolean = true
  }

  trait Not extends Pattern {
    def p: Pattern
  }

  object Not {
    def unapply(arg: Not): Option[Pattern] = Some(arg.p)
  }

  trait Next extends Pattern {
    def p: Pattern
  }

  object Next {
    def unapply(arg: Next): Option[Pattern] = Some(arg.p)
  }

  trait And extends Pattern {
    val p: Pattern
    val q: Pattern
  }

  object And {
    def unapply(arg: And): Option[(Pattern, Pattern)] = Some(arg.p, arg.q)
  }

  trait Or extends Pattern {
    val p: Pattern
    val q: Pattern
  }

  object Or {
    def unapply(arg: Or): Option[(Pattern, Pattern)] = Some(arg.p, arg.q)
  }

  trait Implies extends Pattern {
    val p: Pattern
    val q: Pattern
  }

  object Implies {
    def unapply(arg: Implies): Option[(Pattern, Pattern)] = Some(arg.p, arg.q)
  }

  trait Rewrite extends Pattern {
    val p: Pattern
    val q: Pattern
  }

  object Rewrite {
    def unapply(arg: Rewrite): Option[(Pattern, Pattern)] = Some(arg.p, arg.q)
  }

  trait Equals extends Pattern {
    val p: Pattern
    val q: Pattern
  }

  object Equals {
    def unapply(arg: Equals): Option[(Pattern, Pattern)] = Some(arg.p, arg.q)
  }

  trait Exists extends Pattern {
    val v: Variable
    val q: Pattern
  }

  object Exists {
    def unapply(arg: Exists): Option[(Variable, Pattern)] = Some(arg.v, arg.q)
  }

  trait ForAll extends Pattern {
    val v: Variable
    val q: Pattern
  }

  object ForAll {
    def unapply(arg: ForAll): Option[(Variable, Pattern)] = Some(arg.v, arg.q)
  }

  trait Application extends Pattern {
    val symbol: Symbol
    val args: Seq[Pattern]
  }

  object Application {
    def unapply(arg: Application): Option[(Symbol, Seq[Pattern])] = Some(arg.symbol, arg.args)
  }

  type Attributes = Seq[Pattern]

  trait HasAttributes {
    val att: Attributes
  }

  object HasAttributes {
    def unapply(arg: HasAttributes): Option[Attributes] = Some(arg.att)
  }

  trait Sentence extends HasAttributes

  trait Import extends Sentence {
    val name: Name
  }

  object Import {
    def unapply(arg: Import): Option[(Name, Attributes)] = Some(arg.name, arg.att)
  }

  trait Rule extends Sentence {
    val p: Pattern
  }

  object Rule {
    def unapply(arg: Rule): Option[(Pattern, Attributes)] = Some(arg.p, arg.att)
  }

  trait Axiom extends Sentence {
    val p: Pattern
  }

  object Axiom {
    def unapply(arg: Axiom): Option[(Pattern, Attributes)] = Some(arg.p, arg.att)
  }

  trait SortDeclaration extends Sentence {
    val sort: Sort
  }

  object SortDeclaration {
    def unapply(arg: SortDeclaration): Option[(Sort, Attributes)] = Some(arg.sort, arg.att)
  }

  trait SymbolDeclaration extends SortDeclaration {
    val symbol: Symbol
    val args: Seq[Sort]
  }

  object SymbolDeclaration {
    def unapply(arg: SymbolDeclaration): Option[(Sort, Symbol, Seq[Sort], Attributes)] = Some(arg.sort, arg.symbol, arg.args, arg.att)
  }

  trait Module extends HasAttributes {
    val sentences: Seq[Sentence]
  }

  object Module {
    def unapply(arg: Module): Option[(Seq[Sentence], Attributes)] = Some(arg.sentences, arg.att)
  }

  trait Definition extends HasAttributes {
    val modules: Seq[Module]
  }

  object Definition {
    def unapply(arg: Definition): Option[(Seq[Module], Attributes)] = Some(arg.modules, arg.att)
  }

}

object builders {

  import kore._

  trait Builders {

    def Name(name: Data): Name

    def Sort(sort: Data): Sort

    def Symbol(symbol: Data): Symbol

    def Value(value: Data): Value

    def Variable(name: Name, sort: Sort): Variable

    def DomainValue(symbol: Symbol, value: Value): DomainValue

    def Top(): Top

    def Bottom(): Bottom

    def Not(p: Pattern): Not

    def Next(p: Pattern): Next

    def And(p: Pattern, q: Pattern): And

    def Or(p: Pattern, q: Pattern): Or

    def Implies(p: Pattern, q: Pattern): Implies

    def Equals(p: Pattern, q: Pattern): Equals

    def Exists(v: Variable, q: Pattern): Exists

    def ForAll(v: Variable, q: Pattern): ForAll

    def Rewrite(p: Pattern, q: Pattern): Rewrite

    def Application(p: Symbol, args: Seq[Pattern]): Application

    def Import(name: Name, att: Attributes = Seq.empty): Import

    def SortDeclaration(sort: Sort, att: Attributes = Seq.empty): SortDeclaration

    def SymbolDeclaration(sort: Sort, symbol: Symbol, args: Seq[Sort], att: Attributes = Seq.empty): SymbolDeclaration

    def Rule(p: Pattern, att: Attributes = Seq.empty): Rule

    def Axiom(p: Pattern, att: Attributes = Seq.empty): Axiom

    def Module(sentences: Seq[Sentence], att: Attributes = Seq.empty): Module

    def Definition(modules: Seq[Module], att: Attributes = Seq.empty): Definition
  }

}
