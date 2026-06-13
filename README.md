# ExciteHungaryProject - Backend

## Leírás

Ez a Spring Boot alkalmazás REST API endpoint-okat biztosít a React frontend számára. Az alkalmazás a csapattagok szabadságkezelését és ügyeleti beosztásait kezeli.

## Technológia Stack

- **Backend**: Spring Boot
- **Adatbázis**: PostgreSQL
- **Deployment**: Railway + Docker
- **Build Tool**: Maven

## Fejlesztés

### Előfeltételek

- Java 17+
- Maven
- PostgreSQL

### Alkalmazás futtatása

```bash
./mvnw spring-boot:run
```

### Profil beállítások

- `dev`: Fejlesztési profil
- `prod`: Produkciós profil

## Deployment

Az alkalmazás Docker konténerben fut a Railway platformon. A `Dockerfile` tartalmazza a konténer konfigurációját.

```bash
docker build -t leave-calendar .
docker run -p 8080:8080 leave-calendar
```

## API Endpoints

Az alkalmazás REST API endpoint-okat nyújt:

- Szabadság kérelmek kezelése
- Csapattagok kezelése
- Ügyeleti beosztások
