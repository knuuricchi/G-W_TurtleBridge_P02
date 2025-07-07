# G&W Turtle Bridge

Gra **G&W Turtle Bridge** to komputerowa implementacja klasycznej gry handheld z serii Game & Watch. Gracz wciela się w postać przenoszącą paczki przez most zbudowany z żółwi. Żółwie mogą zanurzać się w wodzie, dlatego konieczna jest precyzja i dobre wyczucie czasu.

## Opis rozgrywki

Gracz porusza się po żółwiach, które tworzą tymczasowy most nad wodą. Celem jest transport paczek z jednej strony na drugą i powrót. W grze pojawiają się także ryby, które mogą być łapane przez żółwie. Maksymalna liczba punktów to 999 – po jej osiągnięciu gra się kończy.

## Funkcjonalności

**Sterowanie gracza:**
- `A` – ruch w lewo
- `D` – ruch w prawo
- `S` – rozpoczęcie gry / pauza

**Mechanika gry:**
- Poruszanie się po żółwiach, które mogą się zanurzyć
- Przenoszenie paczek i ich dostarczanie
- Pojawiające się ryby, które mogą być łapane przez żółwie

**Wyświetlanie wyniku:**
- Punktacja prezentowana w stylu siedmiosegmentowym (klasa `SevenSegmentDigit`)

**Obsługa zdarzeń:**
- Mechanizm listenerów do obsługi zmian stanu gry i wyniku

## Struktura projektu

src/
├── Main.java // Główny punkt wejścia do aplikacji
└── p02/
├── game/
│ └── Board.java // Logika gry: plansza, gracz, zdarzenia
└── pres/
├── GameFrame.java // Okno aplikacji z interfejsem graficznym
├── JBoard.java // Panel odpowiedzialny za rysowanie gry
└── SevenSegmentDigit.java // Klasa rysująca cyfry w stylu seven-segment


## Wymagania

- Java 11 lub nowsza
- Wbudowane biblioteki Java (`javax.swing`, `java.awt`)

## Uruchomienie

1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/knuuricchi/G-W_TurtleBridge_P02.git

    Otwórz projekt w IntelliJ IDEA lub innym IDE wspierającym Javę.

    Uruchom plik Main.java.

Zasady gry

    Gracz startuje po lewej stronie planszy.

    Po dotarciu na prawą stronę odbiera paczkę.

    Po powrocie na lewą stronę paczka zostaje dostarczona.

    Trzeba uważać na zanurzające się żółwie – wpadnięcie do wody powoduje stratę.

    Gra kończy się po zdobyciu 999 punktów.
