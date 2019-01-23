# Controlling Execution #

[true和false](#true和false)

[if-else](#if-else)

[迭代](#迭代)

[Foreach语法](#foreach语法)

[return](#return)

[break和continue](#break和continue)

[臭名昭著goto](#臭名昭著goto)

[switch](#switch)

## true和false ##

	== !=

## if-else ##

	if(Boolean-expression)
	 statement

---

	if(Boolean-expression)
	 statement
	else
	 statement 

[IfElse](IfElse.java)

## 迭代 ##

	while(Boolean-expression)
	 iteration statement

[WhileTest](WhileTest.java)

### do-while ###

	do
	 statement
	while(Boolean-expression); 

### for ###

	for(initialization; Boolean-expression; step)
	 statement 

[ListCharacters](ListCharacters.java)

### 逗号操作符 ###

I stated that the comma operator (not the comma separator, which is used to separate definitions and method arguments) has only one use in Java: in the control expression of a **for** loop.

In both the initialization and step portions of the control expression, you can have a number of statements separated by commas, and those statements will be evaluated sequentially按顺序评估. 

[CommaOperator](CommaOperator.java)

## Foreach语法 ##

[ForEachFloat](ForEachFloat.java)

[ForEachString](ForEachString.java)

[ForEachInt](ForEachInt.java)

## return ##

[IfElse2](IfElse2.java)

## break和continue ##

[BreakAndContinue](BreakAndContinue.java)

## 臭名昭著goto ##

Java中goto仅是**保留字**

However, it does have something that looks a bit like a jump tied in with the **break** and **continue** keywords. 

	label1:

---

	label1:
	outer-iteration {
	 inner-iteration {
	 //...
	 break; // (1)
	 //...
	 continue; // (2)
	 //...
	 continue label1; // (3)
	 //...
	 break label1; // (4)
	 }
	}

[LabeledFor](LabeledFor.java)

[LabeledWhile](LabeledWhile.java)

**break和continue的规则**

1. A plain continue goes to the top of the innermost loop and continues.
2. A labeled continue goes to the label and reenters the loop right after that label.
3. A break “drops out of the bottom中断” of the loop.
4. A labeled break drops out of the bottom中断 of the end of the loop denoted by the label. 

## switch ##

	switch(integral-selector) {
	 case integral-value1 : statement; break;
	 case integral-value2 : statement; break;
	 case integral-value3 : statement; break;
	 case integral-value4 : statement; break;
	 case integral-value5 : statement; break;
	 // ...
	 default: statement;
	} 

integral-selector 为 int, char,Java5 的enum, Java7的String

[VowelsAndConsonants](VowelsAndConsonants.java)

