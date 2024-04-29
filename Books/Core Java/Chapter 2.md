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



