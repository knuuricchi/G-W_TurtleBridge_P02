G&W Turtle Bridge
Opis projektu
Gra "G&W Turtle Bridge" to implementacja klasycznej gry, w której gracz steruje postacią przenoszącą paczki przez most złożony z żółwi. Żółwie mogą zanurzać się w wodzie, co stanowi wyzwanie dla gracza. Gra wykorzystuje interfejs graficzny oparty na bibliotece Swing oraz mechanizm zdarzeń do obsługi logiki gry.  
Funkcjonalności
Sterowanie gracza:
Poruszanie w lewo i prawo za pomocą klawiszy A i D.
Rozpoczęcie gry i pauza za pomocą klawisza S.
Mechanika gry:
Gracz może podnosić paczki i dostarczać je na drugi koniec mostu.
Żółwie mogą zanurzać się w wodzie, co wymaga ostrożności podczas poruszania się.
Ryby pojawiają się na planszy i mogą być łapane przez żółwie.
Wyświetlanie wyniku:
Wynik jest rysowany w stylu "seven-segment" za pomocą klasy SevenSegmentDigit.
Obsługa zdarzeń:
Mechanizm listenerów do obsługi zmian wyniku i zdarzeń gry.
Struktura projektu
src/p02/game/Board.java Logika gry, obsługa planszy, sterowanie graczem i mechanika zdarzeń.
src/p02/pres/GameFrame.java Główne okno aplikacji, które zarządza interfejsem graficznym.
src/p02/pres/JBoard.java Panel graficzny odpowiedzialny za rysowanie planszy i wyniku.
src/p02/pres/SevenSegmentDigit.java Klasa do rysowania cyfr w stylu "seven-segment".
src/Main.java Punkt wejścia do aplikacji.
Wymagania
Java: Wersja 11 lub nowsza.
Biblioteki: Wbudowane biblioteki Java (javax.swing, java.awt).
Uruchomienie
Sklonuj repozytorium:
git clone https://github.com/knuuricchi/turtle-bridge.git
Otwórz projekt w IntelliJ IDEA.
Uruchom plik Main.java.
Sterowanie
A: Ruch w lewo.
D: Ruch w prawo.
S: Rozpoczęcie gry lub pauza.
Zasady gry
Gracz zaczyna po lewej stronie mostu.
Aby podnieść paczkę, należy dotrzeć do prawej strony mostu.
Po podniesieniu paczki należy wrócić na lewą stronę, aby ją dostarczyć.
Uważaj na żółwie, które mogą zanurzać się w wodzie.
Gra kończy się po osiągnięciu 999 punktów.
Autor
Projekt został stworzony przez użytkownika knuuricchi.
