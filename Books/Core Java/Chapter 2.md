# Chapter 2 Fundamental Programming Structures in Java

## 2.1 A Simple Java Program

Let’s look more closely at one of the simplest Java programs you can have—one that merely prints a message to console:

``````java
public class FirstSample
{
  public static void main(String[] args)
  {
    System.out.println("We will not use 'Hello, Wold'")
  }
}
``````

First and foremost, ==*Java is case sensitive*== . If you made any mistakes in capitalization (such as typing Main instead of main), the program will not run.



Now let’s look at this source code line by line. The keyword public is called an ==***access modifier***==; these modifiers control the level of access other parts of a program have to this code. 



<u>The keyword class reminds you that everything in a Java program lives inside a class.</u>

<u>For now, think of a class as a container for the program logic that defines the behavior of an application.</u> 

<u>Classes are the building blocks with which all Java applications are built.</u>

<u>*Everything* in a Java program must be inside a class.</u>



Following the keyword class is the name of the class. 

The rules for class names in Java are quite generous. Names must begin with a letter, and after that, they can have any combination of letters and digits. The length is essentially unlimited. You cannot use a Java reserved word (such as public or class) for a class name.

The standard ==naming convention== (used in the name FirstSample) is called “**camel case**”.



<u>You need to make the file name for the source code the same as the name of the public class</u>, with the extension .java appended. Thus, you must store this code in a file called FirstSample.java.



If you have named the file correctly and not made any typos in the source code, then when you compile this source code, you end up with a file containing the bytecodes for this class. The Java compiler automatically names the bytecode file FirstSample.class and stores it in the same directory as the source file. Finally, launch the program by issuing the following command:

``````shell
Java FirstSample
``````

When you use this command to run a compiled program, <u>the Java virtual machine always starts execution with the code in the main method in the class you indicate</u>. (The term “method” is Java-speak for a function.) Thus, you *must* have a main method in the source of your class for your code to execute. You can, of course, add your own methods to a class and call them from the main method. 

Notice the braces { } in the source code. In Java, as in C/C++, braces delineate the parts (usually called *blocks*) in your program. In Java, the code for any method must be started by an opening brace { and ended by a closing brace }.

Brace styles have inspired an inordinate amount of useless controversy. This book follows a style that lines up matching braces. As whitespace is irrelevant to the Java compiler, you can use whatever brace style you like.

Next, turn your attention to this fragment:

![images](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9788119896257/files/images/img_p64-2.png)



<u>Braces mark the beginning and end of the *body* of the method</u>. This method has only one statement in it. As with most programming languages, <u>you can think of Java statements as sentences of the language. In Java, every statement must end with a semicolon</u>. In particular, carriage returns do not mark the end of a statement, so statements can span multiple lines if need be.

Here, we are using the System.out object and calling its println method. Notice the periods used to invoke a method. Java uses the general syntax

![images](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9788119896257/files/images/img_p65-1.png)



as its equivalent of a function call.

In this case, the println method receives a string parameter. The method displays the string parameter on the console. It then terminates the output line, so that each call to println displays its output on a new line. Notice that Java, like C/C++, <u>uses double quotes to delimit strings</u>.

## 2.2 Comments

Java has three ways of marking comments. The most common form is a ==//==. Use this for a comment that runs from the // to the end of the line.

![images](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9788119896257/files/images/img_p65-3.png)



When longer comments are needed, you can mark each line with a //, or you can use the ==/* and */== comment delimiters that let you block off a longer comment.

Finally, a third kind of comment is used to generate documentation automatically. This comment uses a ==/** to start and a */ to end==. 

## 2.3 Data Types

Java is a ==*strongly typed language*==. <u>This means that every variable must have a declared type</u>. There are ==eight *primitive types*== in Java. <u>Four of them are integer types; two are floating-point number types; one is the character type char, used for code units in the Unicode encoding scheme  and one is a boolean type for truth values</u>.

### 2.3.1 Integer Types

The integer types are for numbers without fractional parts. Negative values are allowed. Java provides the four integer types  Shown in **Table 2.1** Java Integer Types

![images](https://learning.oreilly.com/api/v2/epubs/urn:orm:book:9788119896257/files/images/img_p67.png)

Under Java, the ranges of the integer types do not depend on the machine on which you will be running the Java code. This alleviates a major pain for the programmer who wants to move software from one platform to another, or even between operating systems on the same platform. In contrast, C and C++ programs use the most efficient integer type for each processor. As a result, a C program that runs well on a 32-bit processor may exhibit integer overflow on a 16-bit system. Since Java programs must run with the same results on all machines, the ranges for the various types are fixed.

Long integer numbers have a ==suffix L== or l (for example, 4000000000L). Hexadecimal numbers have a ==prefix 0x or 0X== (for example, 0xCAFE). Octal numbers have a ==prefix 0== (for example, 010 is 8).

You can write numbers in binary, with a ==prefix 0b or 0B==. For example, 0b1001 is 9. You can add ==underscores to number literals==, such as 1_000_000 (or 0b1111_0100_0010_0100_0000) to denote one million. The underscores are for human eyes only. The Java compiler simply removes them.

