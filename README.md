# Viikkotehtävä 6 Kotlin (week6)

## Room Database

Room on Android Jetpack -kirjasto, joka tarjoaa abstraktiokerroksen SQLite-tietokannan päälle. Se mahdollistaa datan pysyvän tallentamisen sovellukseen.

### Room-arkkitehtuuri

UI (Compose) ↕ ViewModel (StateFlow) ↕ Repository (suspend functions) ↕ DAO (Data Access Object) ↕ RoomDatabase ↕ SQLite

### Komponentit

#### 1. Entity (TaskEntity.kt)
- Edustaa tietokantataulua
- `@Entity` annotaatio määrittää luokan tauluksi
- `@PrimaryKey` määrittää pääavaimen
- Sisältää kaikki taulun sarakkeet (id, title, description, priority, dueDate, done)

#### 2. DAO - Data Access Object (TaskDao.kt)
- Määrittelee tietokantaoperaatiot (CRUD)
- `@Query`, `@Insert`, `@Update`, `@Delete` annotaatiot
- Palauttaa `Flow<List<TaskEntity>>` reaktiiviseen datanhakuun
- Suspend-funktiot asynkronisiin operaatioihin

#### 3. Database (AppDatabase.kt)
- `@Database` annotaatio määrittää tietokannan
- Singleton-pattern varmistaa yhden instanssin
- Tarjoaa pääsyn DAO:ihin
- Versiointi tietokannan päivityksiä varten

#### 4. Repository (TaskRepository.kt)
- Välittää datan DAO:sta ViewModelille
- Kapseloi tietolähteen (voi olla useita lähteitä)
- Tarjoaa puhtaan API:n ViewModelille
- Suspend-funktiot tietokantaoperaatioille

#### 5. ViewModel (TaskViewModel.kt)
- Kerää Flow-datan `collectAsState()` avulla
- Hallitsee UI:n tilaa `StateFlow`:lla
- Kutsuu Repository-funktioita `viewModelScope.launch` sisällä
- Eristää UI:n suorista tietokantakutsuista

#### 6. UI (Compose)
- Kuuntelee ViewModel:n StateFlow:ta
- Näyttää datan käyttäjälle
- Lähettää käyttäjän toimet ViewModelille
- Automaattinen rekomponointi datan muuttuessa

## Datavirta

### Lukeminen (Database → UI)

1. RoomDatabase emittoi muutokset
2. DAO:n Flow päivittyy automaattisesti
3. Repository välittää Flow:n ViewModelille
4. ViewModel kerää Flow:n StateFlow:ksi
5. UI kerää StateFlow:n collectAsState():lla
6. Compose rekomponoituu automaattisesti

#### Youtube demo

https://youtu.be/agZLckHRikc
