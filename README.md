# Viikkotehtävä 2 Kotlin (week2)

## Datamalli: Task
Kuvaa yksittäistä tehtävää.

- **id: Int** – yksilöllinen tunniste
- **title: String** – tehtävän otsikko
- **description: String** – kuvaus
- **priority: Int** – prioriteetti
- **dueDate: String** – deadline
- **done: Boolean** – onko tehtävä tehty

## Mock-data: MockTasks
Valmis listadata testaamista varten

## Domain-funktiot (TaskFunctions)
Sijainti: domain/TaskFunctions.kt

### addTask(list, task)
Lisää uuden taskin listan perään.

### toggleDone(list, id)
Kääntää yhden tehtävän true/false id:n perusteella

### filterByDone(list, done)
Suodattaa listasta tehtävät done-arvon mukaan

### sortByDueDate(list)
Järjestää tehtävät dueDate-kentän mukaan nousevasti

## ViewModel: TaskViewModel
ViewModel vastaa sovelluksen tilasta ja listan hallinnasta

Toteutetut toiminnot:
- **tasks: MutableState<List<Task>>** – UI:n näyttämä lista
- **addTask(task: Task)** ja UI-helper **addTask(title: String)**
- **toggleDone(id: Int)**
- **removeTask(id: Int)**
- **filterByDone(done: Boolean)**
- **sortByDueDate()**
- **showAll()** (palauttaa kaikki tehtävät näkyviin)

## UI: HomeScreen
Näyttää tehtävät `LazyColumn`-listana

## Demovideo
**Viikko2 video: https://youtu.be/MCC26KozVKk**

(Viikko1 video: https://www.youtube.com/watch?v=QC4-GotNdLk)
