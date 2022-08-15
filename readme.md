This project implements a tool to parse Windows .rc files and converts them to xml files. Parsing .rc files is very much like parsing C/C++ header files. As such this tool might be interesting for people who have the need to parse such header files. The tool comes with:

- Handling for unlimited nesting of include files
- Trigraph handling and line splicing in the input reader
- Complete macro handling, including charizing  and stringizing
- An evaluator for `#if`, `#ifdef` and `#ifndef` conditional expressions
- Support for some specialities used by (former) Borland Compilers


Compiling the Grammars
===

- Order is important: first expression parser then parser then lexer
- Copy token vocabulary from expression package (folder) to main package (folder)
- Adjust fixed token type constants (e.g. RCParserTokenTypes.LITERAL_auto3state, which is actually used in pure numerical form).

PreprocessorInputState:

- \n must be an ignored character or another ignored character must be used on return when starting to read a new include file and
  pushing the old state to create a new one.

Command line:

`-include="<your include path>" -symbol="RC_INVOKED" -symbol="_WIN32" -symbol="UNICODE" -symbol="APSTUDIO_INVOKED" -symbol="_WIN32_WINNT 0x0400" -symbol="_WIN32_IE 0x0600" -symbol="_MSC_VER 0x1300" -symbol="_INTEGRAL_MAX_BITS 32"`

VM arguments:

`-Dinclude-paths="${env_var:include}"`

Running
1. Open (Import into workspace), and compile with eclipse helios (Java IDE) - https://www.eclipse.org/downloads/packages/release/helios/r
2. Download swt - http://www.eclipse.org/downloads/download.php?file=/eclipse/downloads/drops/R-3.1.1-200509290840/swt-3.1.1-win32-win32-x86.zip
3. Add swt's Jar as referenced library
4. Add swt's DLL as native references to the swt jar you added - https://stackoverflow.com/a/958074/981766
5. Install JDK 7 JRE - https://www.oracle.com/java/technologies/javase/javase7-archive-downloads.html
6. Set project to use JRE 7
7. Download antlr 2.7.3 and add its Jar to reference while removing antlr 2.7.2 - https://github.com/antlr/website-antlr2/blob/gh-pages/download/antlr-2.7.3.jar

References
1. https://www.eclipse.org/forums/index.php/t/166444/
2. https://www.eclipse.org/forums/index.php/t/37549/
3. https://stackoverflow.com/questions/957700/how-to-set-the-java-library-path-from-eclipse