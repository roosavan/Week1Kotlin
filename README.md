# Viikkotehtävä 1 Kotlin
## Datamalli: Task
Kuvaa yksittäistä tehtävää

- id: Int, yksilöllinen tunniste
- title: String, tehtävän otsikko
- description: String, kuvaus
- priority: Int, prioriteetti
- dueDate: String, deadline
- done: Boolean, onko tehtävä tehty

## Mock-data: MockTasks
Valmis listadata testaamista varten

## Listafunktiot:

### addTask
Lisää uuden taskin listan perään

### toggleDone
Kääntää yhden tehtävän true/false

### filterByDone
Suodattaa listasta vain done true arvoiset

### sortByDueDate
Järjestää tehtävät dueDate -kentän mukaan nousevasti

Demovideo: https://www.youtube.com/watch?v=QC4-GotNdLk
