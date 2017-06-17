pt jar executabil de inventar:
 1. maven clean install
 2. din folderul .m2
 ... inventar/target/inventar-jar-with-dependencies.jar
 C:\Users\Jasmine\.m2\repository\ro\feedershop\inventar\0.0.1-SNAPSHOT\inventar-0.0.1-SNAPSHOT-jar-with-dependencies.jar

 3. pt al face executabil trebuie editat fisierul manifest.mf
  si pus:
  Main-Class: ro.feedershop.inventar.GenerareInventar

 4. asta e tot