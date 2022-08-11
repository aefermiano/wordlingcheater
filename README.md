# Wordling Cheater

Cheat for the Wordling! mobile game.


# Usage

You need a text file with all the words of the language you're playing with, easily found on the internet. Don't worry about case or accents, the tool will handle them automatically.

If you are playing the version with 4, 5 and 6 letters, you can start the application with something like this:

```shell
java -jar target/wordling_cheater-1.0-jar-with-dependencies.jar --dictionaryFile=br-utf8.txt 4 5 6
```

You will see a "cliche" shell:

```
hello>
```

You can check the commands available:

```
hello> ?list
abbrev  name    params
r       reset   ()
r       reset   (p1)
ap      apply-pattern   (p1, p2)
gnol    get-number-of-letters   ()
g       guess   (p1)
g       guess   ()
```

The summary is:

* Reset will start a new game, and switch the number of the letters if provided.
* Apply pattern will feed the tool with the information provided by the game. The first parameter is the last word tried, and the second is the colors of each word (G for green, Y for yellow, B for black).
* Guess will tell you what the tool thinks it's the best guess. If you want a list of possibilities, inform it with the parameter, and the tool will give you a list ordered by the most likely guess.

The tool will try to make the best guess by checking the most common letters in your dictionary, and prioritizing words with different letters, so you get more chances of getting matches and the game should converge fast.

# Example

Playing in english with 6 letters (you may replace the commands by the abbreviations, if you want):

```
hello> reset 6                       
true
hello> guess 5
ARIOSE
SERIAN
ARISEN
ARSINE
RESINA
hello> apply-pattern arisen ybbbyg
hello> guess 5
ETALON
PALEON
TEOPAN
DEACON
DAEMON
hello> apply-pattern daemon gyybgg
true
hello> guess 5
DEACON
```

The word is DEACON.
