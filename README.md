# Viikkotehtävä 4 Kotlin (week4)

## Navigointi Jetpack Composessa
- Navigointi mahdollistaa siirtymisen eri näkymien (screenien) välillä sovelluksessa. Jetpack Compose käyttää Navigation Compose -kirjastoa, joka integroi navigaation deklaratiiviseen UI-malliin.

### NavController
- Hallitsee navigaatiota sovelluksessa
- Muistaa navigaatiohistorian
- Tarjoaa metodit: `navigate()`, `popBackStack()`, `navigateUp()`

### NavHost
- Määrittelee navigaatiorakenteen
- Sisältää kaikki reitit ja niitä vastaavat Composablet
- Yhdistää reitin tiettyyn ruutuun

### Sovelluksen navigaatiorakenne
- **HomeScreen → CalendarScreen**: `navController.navigate(ROUTE_CALENDAR)`
- **CalendarScreen → HomeScreen**: `navController.popBackStack()`
- TopAppBar sisältää kalenterikonin (HomeScreen) ja takaisin-nuolen (CalendarScreen)

## MVVM + Navigointi

### Yksi ViewModel kahdelle screenille

- ViewModel luodaan NavHostin tasolla MainActivity:ssä ja välitetään parametrina molemmille ruuduille
- Sama tila jaetaan molempien ruutujen välillä
- Muutokset näkyvät heti kummassakin näkymässä
- Ei tarvetta tilan synkronointiin

### Jaettu StateFlow-tila

Molemmat ruudut kuuntelevat samaa StateFlowta: `val tasks by viewModel.tasks.collectAsState()`

**Kun käyttäjä muokkaa tehtävää:**
1. UI kutsuu ViewModelin funktiota (esim. `toggleDone()`)
2. ViewModel päivittää `_tasks.value`
3. StateFlow emittoi uuden arvon
4. Molemmat ruudut saavat päivityksen automaattisesti
5. UI rekomponoituu molemmissa näkymissä

## CalendarScreen

**Toteutus:**
- Tehtävät ryhmitellään `dueDate`-kentän mukaan: `tasks.groupBy { it.dueDate }.toSortedMap()`
- Näytetään päivämäärä otsikkona ja sen alla kyseisen päivän tehtävät

**Ominaisuudet:**
- Sama `toggleDone()` toiminnallisuus kuin HomeScreenissä
- Tehtävän klikkaus avaa `DetailDialog`:n muokkausta varten
- Kaikki muutokset synkronoituvat automaattisesti HomeScreenin kanssa

## AlertDialog: Add & Edit

### AddTask
- Toteutettu HomeScreenissä yksinkertaisella TextField + Button -yhdistelmällä
- Käyttäjä kirjoittaa otsikon ja painaa "Add"
- Kutsuu `viewModel.addTask(title)`

### EditTask (DetailDialog)
- Avataan kun käyttäjä klikkaa tehtävää (HomeScreen tai CalendarScreen)
- Paikallinen tila: `var selectedTask by remember { mutableStateOf<Task?>(null) }`

**DetailDialog sisältää:**
- Esitäytetyt tekstikentät (title, description)
- **"Save"** → `viewModel.updateTask(task.id, title, description)`
- **"Delete"** → `viewModel.removeTask(task.id)` + vahvistus
- **"Cancel"** → sulje dialogi ilman muutoksia

## Kerrosrakenne

### Model (model-paketti)
- **Task.kt** – datamalli (id, title, description, priority, dueDate, done)
- **MockTasks** – testausdata

### ViewModel (viewmodel-paketti)
- **TaskViewModel.kt** – tilan hallinta StateFlow:lla
- Toiminnot: `addTask()`, `toggleDone()`, `updateTask()`, `removeTask()`, `filterByDone()`, `sortByDueDate()`, `showAll()`

### View (view-paketti)
- **HomeScreen.kt** – tehtävälista
- **CalendarScreen.kt** – kalenterinäkymä
- **DetailDialog.kt** – muokkaus ja poisto
- **AddTaskDialog.kt** - Uuden tehtävän lisääminen
- UI kuuntelee ViewModelin tilaa `collectAsState()`:lla
- Päivitykset näkyvät automaattisesti

### Navigation (navigation-paketti)
- **Routes.kt** – reitit (`ROUTE_HOME`, `ROUTE_CALENDAR`)

### MainActivity
- Luo NavController ja ViewModel
- Määrittelee NavHost:n navigaatiorakenteen

## Demovideo

**Viikko4 video: https://youtu.be/hlWMvchsL2I**
