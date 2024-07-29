# Testy UI, sprawdzające zgodność cen na stronie https://g2a.com

Repozytorium zawiera framework testowy oparty o Jave, Playwright, Cucumber oraz Junit5.

## Spis treści
1. [Technologie](#technologies)
2. [Struktura projektu](#test-structure)
3. [Koncepcja i co zostało zrobione dodatkowo](#test-concept)
4. [Co można zrobic więcej w przyszłości](#what-more)
5. [Uruchamianie testów](#running-tests)
6. [Napotkane problemy](#issues)

<a name="technologies"></a>
# 1. Wykorzystane technologie

- Java
- Playwright
- Cucumber
- Junit5
- Log4J


<a name="test-structure"></a>
# 2. Struktura projektu


Struktura projektu (standardowa dla projektów Java) składa się z dwóch głównych folderów:
* **src/main** -  Zawiera wszystkie klasy oraz metody niezbędne do automatyzacji testów (PageObjecty, utilsy, etc.)
* **src/test** - Zawiera pliki z testami oraz pliki konfiguryacyjne dla Cucumber. Dodatkowo w katalogu resources znajdują się feature z przykładowymi scenariuszami.

<a name="test-concept"></a>
# 3. Koncepcja i co zostało zrobione dodatkowo

## 3.1. Testy

Testy zostały napisane z wykorzystaniem frameworka Cucumber, w taki sposób, aby były one niezależne od siebie i mogły być wykonywane równolegle. Na potrzeby sprawdzenia,
czy rzeczywiście testy są wykonywane równolegle, został dodany drugi feature file z lekko zmienionymi nazwami kroków (sam scenariusz i implementacja kroków jest taka sama - chodziło tylko o koncept). 

## 3.2. PageObjecty
Jeśil chodzi o klasy PageObject, została utworzona klasa abstrakcyjna `BasePage`, która zawiera owrapowane metody Playwrightowe, do których zostało dodane logowanie zdarzeń, oraz odpowiednie waity (Jak doświadczenie nauczyło, Playwright bez odpowiedniego czekania na element, lubi czasami rzucić błędem, że danego (dynamicznego) elementu nie ma na stronie). Wszystkie PageObjecty rozszerzają `BasePage`, żeby móc w prosty sposób korzystać z tych customowych metod.

## 3.3. BrowserConfig
Dodatkowo zaimplemntowana została klasa `BrowserConfig`, która zawiera konfigurację Playwrighta, oraz metody do tworzenia przeglądarki. Dzięki temu w prosty sposób, możemy testy odpalać na różnych przeglądarkach (Chrome, Firefox, Webkit), podając po prostu nazwę przeglądarki w Command Line przy uruchamianiu testów, 
np. `mvn test -DBROWSER=firefox`. Gdy parametr jest niepodany lub nieprawidłowy, zostanie odpalona przeglądarka Chrome.

## 3.4 Raport z testów
Gdy testy zostaną zakończone, w folderze `target/cucumber-reports` zostanie wygenerowany raport z testów w potaci pliku HTML, który można podejrzeć w przeglądarce. Do każdego kroku w scenariuszu są również tworzone zrzuty ekranu, które są dodane do końcowego raportu.

## 3.5 Podanie nazwy szukanego produktu do testu
Produkt do testu podawany jest za pomocą zmiennej PRODUCT_NAME, czyli przykładowe wywołanie testów będzie wyglądać tak:
`mvn test -DPRODUCT_NAME="Tom Clancy"`. W przypadku, gdy zmienna nie zostanie podana, zostanie użyta domyślna wartość parametru, zadeklarowana w klasie `ProductNameGetter`. 

<a name="what-more"></a>
# 4. Co można zrobic więcej w przyszłości

- Na ten moment testy odpalane są tylko na produkcyjnym środowisku, więc url jest podany na sztywno w klasie `TestHelpers`. Przy ewentualnym rozszerzaniu funkcjonalności, można stworzyć osobne pliku konfiguracyjne dla danego środowiska, np. `dev.properties`, `staging.properties`, `prod.properties`, itd.
- Możliwość odpalania testów równiez na widoku mobilnym - równiez mogłoby być to obsługiwane w klasie `BrowserConfig` oraz przez zmienną podawaną przy wywołaniu testów,np. `mvn test -DMOBILE=true`.
- Przy większej liczbie testów, można je pogrupować w różne suity testowe
- Zautomatyzować odpalanie testów w pipeline i jako artefakt joba wystawić raport z `target/cucumber-reports` do pobrania lub wysłać na maila, czy wystawić na jakiejś stronie 
- Żeby przyśpieszyć wykonanie testów, powinny być one wykonywane w trybie headless (w tym wypadku prawdopodobnie strona wykrywa, że ktoś wchodzi w trybie headless i blokuje dalsze operacje na niej)
- I oczywista rzecz - napisać więcej testów :) 

<a name="running-tests"></a>
# 5. Uruchamianie testów

## 5.1. Lokalnie
Żeby uruchomić lokalnie wystarczy (pod warunkiem, że ma się zainstalowaną odpowiednią wersję Javy i mavena) zaciągnąc repozytorium i uruchomić testy komendą - uruchomi ona testy w przeglądarce Chrome, z domyślną nazwą szukanego produktu.

```java
mvn test
```

Żeby uruchomić testy w innej przeglądarce, z konkretnym produktym testowym, należy wykonać:

```java
mvn test -DBROWSER=firefox -DPRODUCT_NAME="Tom Clancy"
```

## 5.2. Docker
Jako, że każdy może mieć różne wersje Javy i mavena, do odpalania testów zalecane jest używanie dockera. W głównym katalogu projektu znajduje się plik Dockerfile, z predefiniowanymi wersjami Javy, mavena oraz Playwright.

Na początku trzeba stworzyć sobie obraz z tego pliku za pomocą komendy: 

```
docker build -t g2a-test .
``` 

Gdy obraz zostanie stworzony, należy uruchomić kontener za pomocą komendy:

```
docker run -it g2a-test bin/bash
```
dzięki czemu zostanie otwarta nowa konsola w kontenerze. Należy przejść do folderu `/app`, gdzie został przekopiowany projekt.

```
cd /app
```

Następnie testy można uruchamiać na różne sposoby za pomocą przykładowych komend podanych w punkcie 5.1, z dopiskiem `xvfb-run`, który jest potrzebny do odpalenia testów w trybie graficznym (nie headless) w dockerze.    

```
xvfb-run mvn test -DBROWSER=firefox -DPRODUCT_NAME="Tom Clancy" 
```

<a name="issues"></a>
# 6. Napotkane problemy

- Zabezpieczenia strony g2a - wykrywanie bota, obejście zrobione w klasie `BrowserConfig`, poprzez dodanie: 

    ```java
    browserContext.addInitScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
    ```

- Kolejnym prawdopodobnym zabezpieczeniem strony jest wykrycie, czy przeglądarka jest uruchomiona w trybie headless - uniemożliwiło to uruchomienie testów w tym trybie, przez co testy wykonywane są trochę dłużej. Zakładam jednak, że środowiska testowe, mogą mieć takie zabezpieczenie wyłączone na potrzeby szybszej egzekucji testów.

- Uruchmianie testów Cucumber równolegle - (oczywiście było to dodatkowe zadanie, które postanowiłem zaimplementować z ciekawości jak cucumber radzi sobie z takimi testami) przysporzyło trochę problemów związanych z konfiguracją i DI w cucumber. Wydaje się jednak, że sposób implementacji okazał się poprawny. 

- Nie wszystkie selektory na stronie są unikalne, co utrudniło prostą identyfikację elementów. W przypadku, gdy kilkanaście elemetów miało ten sam selektor, wybieram pierwszy napotkany element za pomocą `page.locator(selector).first()`. Nie jest to jednak rozwiązanie idealne, gdyż w przypadku zmiany struktury strony, elementy mogą się potem pozmieniać miejscami i testy będą failować

