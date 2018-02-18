# TMDLib-java [![Build Status](https://travis-ci.org/c-schuhmann/TMDLib-java.svg?branch=master)](https://travis-ci.org/c-schuhmann/TMDLib-java) [![](https://jitpack.io/v/c-schuhmann/TMDLib-java.svg)](https://jitpack.io/#c-schuhmann/TMDLib-java)
## Java library for parsing Nintendo TMD (Title Metadata) files

### Usage & Requirements
1. Include the library either:
  * per compiled jar file - No other dependencies needed!
  * per [JitPack.io](https://jitpack.io/#c-schuhmann/TMDLib-java/) (for usage within Maven, Gradle...)
2. Get a TMD file
3. Pass the file to the TMD constructor (pro.schuhmann.tmdlib.TMD)
4. ...
5. Profit! You can now use this freshly created TMD object to obtain data from it.

Note: Only 3DS TMD files are officially supported at this time! It MAY be possible to obtain data from other Nintendo 
console's (Wii/U, Switch...) aswell.

You may want to look into this projects 
[Javadoc](https://c-schuhmann.github.io/TMDLib-java/) for more information.

### Building
You need at least JDK 1.7 and Maven.
Build & package with `mvn compile package`.

### Contributing
This is more or less a project for myself, but if you really want to contribute: 
Give me some advice, what I can do better :)

### Roadmap
- Rework some classes and methods (Planned for release 1.0.0)
- Add compatibility for other TMD types (Planned for release 1.0.0 or later)
- Add usage examples (some time)

### Thanks
This project would'nt be possible without [3DBrew](https://3dbrew.org/wiki/Title_metadata)!
The TMD file structure is very well described there.

#### Extra: The reason why I started this project
At the time I'm writing this readme file right now, I'm in the process of learning Java.
I was always a big fan of the Nintendo Homebrew Scene, so I thought: Hey, let's do a Homebrew-themed project!

I really hope that this small "library" will be useful for someone.