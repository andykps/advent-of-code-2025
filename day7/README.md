~Expects JDK 11+ for Single file source-code programs which explains why the filename doesn't match the classname.~
It wasn't single file source code I was looking for but JEP-512 for compact source files that I was thinking of. The latest commit allows me to remove all of my imports because they're in the java.base module and to remove the boilerplate class declaration and just have a lightweight main method.
Just run with `java day7.java input.txt`
