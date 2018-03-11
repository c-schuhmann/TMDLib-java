# TMDLib-java [![Build Status](https://travis-ci.org/c-schuhmann/TMDLib-java.svg?branch=master)](https://travis-ci.org/c-schuhmann/TMDLib-java) [![](https://jitpack.io/v/c-schuhmann/TMDLib-java.svg)](https://jitpack.io/#c-schuhmann/TMDLib-java)
## Java library for parsing Nintendo TMD (Title Metadata) files

### Table of Contents
1. [Usage](#usage)
2. [Example](#example)
3. [Building](#building)
4. [Contributing](#contributing)
5. [Roadmap](#roadmap)
6. [Thanks](#thanks)
7. [Authors Note](#authors-note)

### Usage
1. Include the library either:
  * per compiled jar file (no other dependencies needed)
  * per [JitPack.io](https://jitpack.io/#c-schuhmann/TMDLib-java/) (for usage within Maven, Gradle...)
2. Get a TMD file
3. Pass the file to the TMD constructor (pro.schuhmann.tmdlib.TMD)
4. ...
5. Profit! You can now use this freshly created TMD object to obtain data from it.

Note: Only 3DS TMD files are officially supported at this time! It MAY be possible to obtain data from other Nintendo 
console's (Wii/U, Switch...) aswell.

You may want to look into this projects 
[Javadoc](https://c-schuhmann.github.io/TMDLib-java/) for more information.

### Example
You have to obtain a TMD file first: You can download one from 
[Nintendo's CDN](http://nus.cdn.c.shop.nintendowifi.net/ccs/download/00040000000EE000/tmd). 
(Super Smash Bros. 4 in this example)

```java
import pro.schuhmann.tmdlib.TMD;

public class TmdExample 
{
  public static void printTitleVersionAndContentCount(java.io.File f) throws java.io.IOException
  {
    TMD tmdFile = new TMD(new File("tmd"));
    System.out.println("Version of the title: " + tmdFile.getHeader().getTitleVersion());
    System.out.println("Number of contents: " + tmdFile.getHeader().getContentCount());
  }
}
```

### Building
You need at least JDK 1.7 and Maven.
Build & package with `mvn compile package`.

### Contributing
This is more or less a project for myself, but if you really want to contribute: 
Give me some advice, what I can do better :)

### Roadmap
- Rework some classes and methods (Planned for release 1.0.0)
- Add compatibility for other TMD types (Planned for release 1.0.0 or later)

### Thanks
This project would'nt be possible without [3DBrew](https://3dbrew.org/wiki/Title_metadata)!
The TMD file structure is very well described there.

#### Authors Note
At the time I'm writing this readme file right now, I'm in the process of learning Java.
I was always a big fan of the Nintendo Homebrew Scene, so I thought: Hey, let's do a Homebrew-themed project!

I really hope that this small "library" will be useful for someone.