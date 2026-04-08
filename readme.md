# BestLibraryEver

Ett bibliotekshanteringssystem byggt i Java med trelagersarkitektur och JDBC. Projektet är skapat som en del av kursen *Objektorienterad analys och design* och demonstrerar strukturerad backend-utveckling med fokus på OOP-principer.

---

## Navigering

- [Projektstruktur](#projektstruktur)
- [Arkitekturella beslut](#arkitekturella-beslut)
- [Datamodell](#datamodell)
- [Användning av programmet](#användning-av-programmet)
- [Hur projektet byggdes med AI](#hur-projektet-byggdes-med-ai)
- [Tips för att använda AI i läroprojekt](#tips-för-att-använda-ai-i-läroprojekt)

---

## Projektstruktur

Projektet använder **feature-based packagestruktur** — kod organiseras efter domän snarare än arkitekturlager. Varje package innehåller allt som hör till den featuren: entitet, repository, service, controller, DTOs och mapper.

```
BestLibraryEver/
├── book/
│   ├── Book.java
│   ├── BookRepository.java
│   ├── BookService.java
│   ├── BookController.java
│   ├── BookMapper.java
│   ├── BookSummaryDTO.java
│   ├── BookDetailDTO.java
│   ├── BookFormDTO.java
│   ├── BookNotFoundException.java
│   └── BookNotAvailableException.java
├── author/
│   ├── Author.java
│   └── AuthorRepository.java
├── category/
│   ├── Category.java
│   └── CategoryRepository.java
├── member/
│   ├── Member.java
│   ├── MemberRepository.java
│   ├── MemberService.java
│   ├── MemberController.java
│   ├── MemberMapper.java
│   ├── MemberProfileDTO.java
│   ├── MemberNotFoundException.java
│   └── MemberSuspendedException.java
├── loan/
│   ├── Loan.java
│   ├── LoanRepository.java
│   ├── LoanService.java
│   ├── LoanController.java
│   ├── LoanMapper.java
│   ├── LoanSummaryDTO.java
│   └── ActiveLoanDTO.java
├── swing/
│   ├── SwingMain.java
│   ├── MainFrame.java
│   ├── BookFrame.java
│   ├── LoanFrame.java
│   └── MemberFrame.java
└── Main.java
```

**Namnkonventioner:**
- Packages: små bokstäver, singular (`book` inte `books`)
- Klasser: PascalCase
- Metoder och variabler: camelCase

---

## Arkitekturella beslut

### Trelagersarkitektur

Projektet är uppdelat i tre tydliga lager med separata ansvarsområden:

| Lager | Ansvar | Exempel |
|---|---|---|
| **Controller** | Hanterar input/output, menyer | `BookController` |
| **Service** | Affärslogik, mappning, orkestrering | `BookService` |
| **Repository** | Databasåtkomst via JDBC | `BookRepository` |

### Repository-konventioner

- Repository returnerar alltid **entiteter**, aldrig DTOs
- Repository tar aldrig emot DTOs som argument — mappning från DTO till entitet sker i service
- Varje repository skapar sin egen databasanslutning via `DriverManager`

### Hantering av relationer (M:M)

`Book` har en `List<Author>` och `List<Category>`. Dessa listas returneras tomma från `BookRepository` och fylls på i **service-lagret** via separata anrop till `AuthorRepository` och `CategoryRepository`. Detta valdes framför SQL-joins med `GROUP_CONCAT` för att hålla koden tydlig och lättläst, på bekostnad av fler databasanrop.

### DTO-mönstret

DTOs används för att forma data efter specifika användningsfall. Mappning sker via dedikerade mapper-klasser:

```
Book (entitet) → BookMapper → BookSummaryDTO (boklistning)
Book (entitet) → BookMapper → BookDetailDTO (detaljvy)
BookFormDTO    → BookMapper → Book (vid skapande)
```

Mapper-klasser valdes framför mappning direkt i service (läsbarhet) och framför statiska metoder i DTOn (separation of concerns).

### Felhantering

Custom unchecked exceptions (`extends RuntimeException`) kastas i service-lagret och fångas i controllers:

- `BookNotFoundException` — bok hittades inte
- `BookNotAvailableException` — inga lediga exemplar
- `MemberNotFoundException` — medlem hittades inte
- `MemberSuspendedException` — kontot är avstängt

### UI-alternativ

Projektet har två parallella UI-implementationer som båda använder samma service- och DTO-lager:

- **Konsol** — `Main.java` med controller-klasser och Scanner
- **Swing** — `SwingMain.java` med frame-klasser

Detta demonstrerar värdet av trelagersarkitektur: hela UI-lagret kan bytas ut utan att röra affärslogik eller databasåtkomst.

---

## Datamodell

```
BOOKS ──── BOOK_DESCRIPTIONS (1:1)
BOOKS ──── BOOK_AUTHORS ──── AUTHORS (M:M)
BOOKS ──── BOOK_CATEGORIES ── CATEGORIES (M:M)
BOOKS ──── LOANS ──── MEMBERS
LOANS ──── FINES
MEMBERS ── NOTIFICATIONS
BOOKS ──── REVIEWS ──── MEMBERS
```

`BookDescription` är inflattad direkt i `Book`-entiteten trots att den är separerad i databasen — ett medvetet val för att förenkla domänmodellen.

---

## Användning av programmet

### Förutsättningar

- JDK 21 (Temurin)
- MySQL med databasen `librarydb`
- Uppdatera databasuppgifter i respektive repository-klass:

```java
private final String URL  = "jdbc:mysql://localhost:3306/librarydb";
private final String USER = "root";
private final String PASS = "dittlösenord";
```

### Starta konsolversionen

Kör `main()` i `Main.java`. Navigera med siffror i menyerna:

```
------ Huvudmeny ------
1. Böcker
2. Lån
3. Medlemmar
0. Avsluta
```

### Starta Swing-versionen

Kör `main()` i `SwingMain.java`. Ett fönster öppnas med knappar för varje feature.

### Bokmeny

| Val | Funktion |
|---|---|
| 1 | Visa alla böcker |
| 2 | Sök efter bok på titel |
| 3 | Visa detaljinfo för en bok |
| 4 | Lägg till ny bok |

### Lånemeny

| Val | Funktion |
|---|---|
| 1 | Skapa nytt lån |
| 2 | Visa alla aktiva lån |
| 3 | Visa aktiva lån för en specifik medlem |

### Medlemsmeny

| Val | Funktion |
|---|---|
| 1 | Visa medlemsprofil med aktiva lån |
| 2 | Skapa nytt medlemskonto |

---

## Hur projektet byggdes med AI

Projektet byggdes gradvis och systematiskt i dialog med Claude (Anthropic). Istället för att be om ett färdigt projekt på en gång delades arbetet upp i distinkta steg där varje beslut diskuterades innan kod skrevs.

### Arbetsflödet

**1. Diskussion innan kod**
Varje del av projektet inleddes med en diskussion om alternativ, fördelar och nackdelar. Packagestruktur, DTO-strategi, mappningsansvar och relationshantering diskuterades och beslutades innan någon kod genererades.

**2. Exempel före bulk**
För varje ny typ av klass (repository, service, controller) begärdes först ett enskilt exempel att granska och komma överens om konventioner kring — sedan genererades resten.

**3. Inkrementell byggnad**
Projektet byggdes i denna ordning:
- Packagestruktur
- Entiteter
- Repositories (med diskussion om relationshantering)
- Custom exceptions
- DTOs
- Mappers
- Services
- Controllers
- Swing-UI

Varje steg byggde på det föregående och kunde inte påbörjas förrän föregående lager var klart och förstått.

**4. Aktiv granskning**
AI-genererad kod granskades aktivt — när service-metoder anropade repository-metoder som inte existerade identifierades och åtgärdades luckorna innan arbetet fortsatte.

---

## Tips för att använda AI i läroprojekt

### Vad AI är bra på i det här sammanhanget

- Generera boilerplate-kod snabbt (getters, konstruktorer, repetitiva mönster)
- Förklara fördelar och nackdelar med arkitekturella val
- Hålla kodstilen konsekvent genom ett helt projekt
- Identifiera luckor och inkonsekvenser i er kod

### Vad ni själva måste göra

- **Ta arkitekturbesluten.** AI ger er alternativ men ni måste förstå och äga besluten. Annars kan ni inte försvara dem på en tentamen eller i ett kodsamtal.
- **Granska all kod.** AI gör misstag — anropar metoder som inte finns, missar att uppdatera beroende klasser, introducerar inkonsekvenser. Ni är kvalitetsgranskningen.
- **Ifrågasätt aktivt.** "Varför är det skrivet så här?", "finns det ett enklare sätt?", "vad händer om X?" är bättre frågor än "ge mig koden".

### Konkreta promptningstips

**Ge kontext i början** — berätta om projektet, tekniken ni använder och vad ni redan kan. Ju mer relevant kontext, desto bättre anpassade svar.

**Använd negativa constraints** — "utan mapRow-metod", "inga bullets", "inte heltäckande" är minst lika viktiga som positiva instruktioner.

**Be om alternativ** — "ge mig två alternativ med för- och nackdelar" ger er förståelse, inte bara kod.

**Referera till tidigare beslut** — "som vi bestämde tidigare", "använd samma mönster som i BookRepository" håller koden konsekvent.

**Begär ett exempel först** — innan ni ber om all kod, be om ett exempel att granska. Det ger er chansen att komma överens om konventioner och korrigera kursen innan det är för sent.

**Var specifik med vad ni inte vill ha** — om AI ger er för komplex kod, säg det explicit och förklara varför. AI anpassar sig.

### Vad man ska undvika

- Be om hela projektet på en gång utan att förstå delarna
- Copy-pasta kod utan att läsa den
- Låta AI ta arkitekturbeslut utan diskussion
- Använda AI för att lösa problem ni inte förstår — lös förståelsen först
