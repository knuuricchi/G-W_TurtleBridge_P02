# 🐢 G&W Turtle Bridge

**G&W Turtle Bridge** to gra inspirowana klasycznym handheldem Game & Watch. Gracz steruje postacią, której zadaniem jest przenoszenie paczek przez niebezpieczny most złożony z żółwi. Żółwie mogą zanurzać się w wodzie, co czyni przeprawę trudniejszą i bardziej dynamiczną.

## 🎮 Funkcjonalności

### Sterowanie
- `A` – ruch w lewo  
- `D` – ruch w prawo  
- `S` – rozpoczęcie gry / pauza  

### Mechanika gry
- Gracz przenosi paczki z jednej strony mostu na drugą.
- Żółwie, po których gracz się porusza, mogą się zanurzać.
- Ryby pojawiają się na planszy i mogą być łapane przez żółwie.
- Po zdobyciu 999 punktów gra się kończy.

### Wyświetlanie wyniku
- Wynik przedstawiany w stylu **seven-segment display** dzięki klasie `SevenSegmentDigit`.

### Obsługa zdarzeń
- Wykorzystanie mechanizmu listenerów do reagowania na zmiany wyniku i wydarzenia w grze.

---

## 🗂️ Struktura projektu

